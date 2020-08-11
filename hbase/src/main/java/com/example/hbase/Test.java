package com.example.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-30 17:06
 **/
public class Test {

    public static void main(String[] args) throws Exception{
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "localhost");
        Connection connection = ConnectionFactory.createConnection(conf);
        Table test = connection.getTable(TableName.valueOf("test"));

        Put put = new Put(Bytes.toBytes("row001"));
        put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("name"), Bytes.toBytes("xiaoming"));
        test.put(put);
    }
}
