package com.example.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellScanner;
import org.apache.hadoop.hbase.Coprocessor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.coprocessor.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-12-09 17:08
 **/
public class HBaseUtil {

    /**
     * get a hbase connection
     * @return
     * @throws Exception
     */
    public static Connection getHBaseConnection() throws Exception{
        Configuration conf = new Configuration();
        //conf.set("hbase.zookeeper.quorum", "localhost");
        Connection connection = ConnectionFactory.createConnection(conf);
        return connection;
    }

    public static void putData(Table table, String rowId, String cf, String cfName, String value) throws Exception{
        Put put = new Put(Bytes.toBytes(rowId));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cfName), Bytes.toBytes(value));
        table.put(put);
    }

    public static void loadCoprocessor(Connection connection, TableName tableName) throws Exception{
        Table table = connection.getTable(tableName);

        List<CoprocessorDescriptor> coprocessorList = new LinkedList<>(table.getDescriptor().getCoprocessorDescriptors());
        CoprocessorDescriptor coprocessorDescriptor = CoprocessorDescriptorBuilder.newBuilder(LogDeleteOperationRegionObserver.class.getName())
                .setJarPath("hdfs://127.0.0.1:8020/hbase-jar/coprocessor.jar")
                .build();
        coprocessorList.add(coprocessorDescriptor);
        Admin admin = connection.getAdmin();

        if (admin.isTableEnabled(tableName)){
            admin.disableTable(tableName);
        }

        TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(table.getDescriptor())
                .setCoprocessors(coprocessorList)
                .build();
        admin.modifyTable(tableDescriptor);

        if (admin.isTableDisabled(tableName)){
            admin.enableTable(tableName);
        }
    }

    public static void unloadCoprocessor(Connection connection, TableName tableName) throws Exception {
        Table table = connection.getTable(tableName);
        Admin admin = connection.getAdmin();
        if (admin.isTableEnabled(tableName)){
            admin.disableTable(tableName);
        }
        TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(table.getDescriptor())
                .removeCoprocessor(LogDeleteOperationRegionObserver.class.getName())
                .build();
        admin.modifyTable(tableDescriptor);
        if (admin.isTableDisabled(tableName)){
            admin.enableTable(tableName);
        }
    }

    public static void scan(Table table, String prefix) throws Exception{

        Scan scan = new Scan();
        Filter filter = new PrefixFilter(Bytes.toBytes(prefix));
        scan.setFilter(filter);
        ResultScanner rs = table.getScanner(scan);
        Iterator<Result> iterator = rs.iterator();

        ColumnFamilyDescriptor[] familyDescriptors = table.getDescriptor().getColumnFamilies();

        for (ColumnFamilyDescriptor familyDescriptor : familyDescriptors){
            String fName = familyDescriptor.getNameAsString();
            System.out.println(fName);
        }


        while (iterator.hasNext()){
            Result result = iterator.next();
            byte[] valueBytes = result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("name"));
            System.out.println(Bytes.toString(valueBytes));
        }
    }

    public static void main(String[] args) throws Exception{
        Connection connection = getHBaseConnection();
        TableName tableName = TableName.valueOf("test");
        //Table table = connection.getTable(tableName);
        //putData(table, "row2", "cf", "name", "xiaoming");
        //scan(table, "row");
        unloadCoprocessor(connection, tableName);
        loadCoprocessor(connection, tableName);
    }
}
