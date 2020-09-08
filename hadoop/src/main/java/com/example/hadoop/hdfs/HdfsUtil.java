package com.example.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HdfsUtil {

    public static void main(String[] args) throws Exception{
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(configuration);

        Path path = new Path("/user");
        fs.mkdirs(path);
    }
}
