package com.example.hbase;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessor;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.coprocessor.RegionObserver;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.wal.WALEdit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-12-17 13:25
 **/
public class LogDeleteOperationRegionObserver implements RegionObserver, RegionCoprocessor {

    private static Logger logger = LoggerFactory.getLogger(LogDeleteOperationRegionObserver.class);

    @Override
    public Optional<RegionObserver> getRegionObserver() {
        return Optional.of(this);
    }

    @Override
    public void prePut(ObserverContext<RegionCoprocessorEnvironment> c, Put put, WALEdit edit, Durability durability) throws IOException {
        logger.info("======================pre put===========================");
        Table table = c.getEnvironment().getConnection().getTable(TableName.valueOf("put_log"));
        TableDescriptor tableDescriptor = c.getEnvironment().getRegion().getTableDescriptor();
        byte[] tableName = tableDescriptor.getTableName().getName();
        for (Map.Entry<byte[], List<Cell>> entry:  put.getFamilyCellMap().entrySet()){
            String cf = Bytes.toString(entry.getKey());
            List<Cell> cellList = entry.getValue();

            String val = cellList.stream().map(cell -> Bytes.toString(cell.getValueArray())).collect(Collectors.joining("|"));
            Put newPut = new Put(Bytes.toBytes(System.currentTimeMillis() +""));
            newPut.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("cf"), Bytes.toBytes(cf));
            newPut.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("table"), tableName);
            newPut.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("val"), Bytes.toBytes(val));
            newPut.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("row"), put.getRow());
            newPut.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("operation_time"), Bytes.toBytes(put.getTimestamp() + ""));
            table.put(newPut);
        }
        table.close();
    }

    @Override
    public void postDelete(ObserverContext<RegionCoprocessorEnvironment> c, Delete delete, WALEdit edit, Durability durability) throws IOException {
        String user = c.getCaller().isPresent() ? c.getCaller().get().getName() : "unknown";
        String deleteRow = Bytes.toString(delete.getRow());
        logger.info(String.format("user %s , delete row %s", user, deleteRow));
        Table table = c.getEnvironment().getConnection().getTable(TableName.valueOf("delete_log"));

        TableDescriptor tableDescriptor = c.getEnvironment().getRegion().getTableDescriptor();
        byte[] tableName = tableDescriptor.getTableName().getName();
        Put put = new Put(Bytes.toBytes(System.currentTimeMillis() + ""));
        put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("user"), Bytes.toBytes(user));
        put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("table"), tableName);
        put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("row"), delete.getRow());
        put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("operation_time"), Bytes.toBytes(delete.getTimestamp() + ""));
        table.put(put);
        table.close();
    }
}
