package com.test;

import org.apache.commons.net.ftp.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-04-17 08:48
 **/
public class TestFtpUpload {

    private static final String server = "10.92.35.24";
    private static final Integer port = 21;
    private static final String username = "usersupport";
    private static final String password = "Aa123456";
    private static final String workDir = "usersupport";

    public static void main(String[] args) throws Exception{
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(server, port);
        int replyCode = ftpClient.getReplyCode();
        if (FTPReply.isPositiveCompletion(replyCode)){
            ftpClient.login(username, password);
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.configure(new FTPClientConfig());
            ftpClient.setBufferSize(1024);
            ftpClient.enterLocalPassiveMode();
            ftpClient.makeDirectory(workDir);
            try{
                ftpClient.changeWorkingDirectory(workDir);
                final String fileName = "s3_1060_1587028609876__debuglogger-20200416163155.zip";
                File attachment = new File("/home/lmh/Downloads/s3_1060_1587028609876__debuglogger-20200416163155.zip");
                FTPFile[] files = ftpClient.listFiles("", new FTPFileFilter() {
                    public boolean accept(FTPFile file) {
                        return file.getName().equals(fileName);
                    }
                });
                if (files.length > 0){
                    ftpClient.deleteFile(fileName);
                }
                FileInputStream fi = new FileInputStream(attachment);
                System.out.println(ftpClient.storeFile(fileName, fi));
                fi.close();
            }catch (IOException e){
                throw e;
            }finally {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        }else{
            ftpClient.disconnect();
        }

    }
}
