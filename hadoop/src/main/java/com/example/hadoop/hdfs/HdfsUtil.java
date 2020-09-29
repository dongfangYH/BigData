package com.example.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class HdfsUtil {

    public static void copyFileFromLocal(FileSystem fs, String localPath, String destPath) {
        try{
            FileInputStream fi = new FileInputStream(localPath);
            Path dest = new Path(destPath);
            FSDataOutputStream fo = fs.create(dest, true);
            IOUtils.copyBytes(fi, fo, 1024, true);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    public static void appendFile(FileSystem fs, String fromPath, String destPath){
        try{
            FileInputStream fi = new FileInputStream(fromPath);
            Path dest = new Path(destPath);
            FSDataOutputStream fo = fs.append(dest, 1024);
            IOUtils.copyBytes(fi, fo, 1024, true);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static FSDataInputStream getFile(FileSystem fs, String fromPath){
        try{
            Path path = new Path(fromPath);
            return fs.open(path);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception{
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(configuration);

        String destPath = "/data/eureka.jar";

        long start = System.currentTimeMillis();
        FSDataInputStream fi = getFile(fs, destPath);
        FileOutputStream fo = new FileOutputStream("/home/lmh/eureka.jar");
        IOUtils.copyBytes(fi, fo, 1024, true);
        System.out.println((System.currentTimeMillis() - start));
    }
}
