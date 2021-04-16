package am.ps.filemerger;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.CollectionUtils;
import com.amazonaws.util.IOUtils;
import lombok.Data;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.GroupReadSupport;
import org.apache.parquet.hadoop.example.GroupWriteSupport;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * FileMerger
 * @author yuanhang.liu
 * @date 2020-01-07
 */
public class FileMerger {

    private static String BUCKET_NAME = "teye-mid-table";
    private static String KEY_PREFIX = "mid_event/info";

    private static String FOLDER = "/tmp/data/s3";
    private static final Integer PARTS_LENGTH = 2;
    private static final String FILE_SEP = "/";
    private static final String UNDERLINE = "_";

    /**
     * parquet file schema
     */
    private static final String schemaStr = "message hive_schema {\n" +
            "  optional binary packagename (UTF8);\n" +
            "  optional binary teyeid (UTF8);\n" +
            "  optional binary appversion (UTF8);\n" +
            "  optional binary basic_devicename (UTF8);\n" +
            "}";

    /**
     * 最大文件大小 1G
     */
    private static Long MAX_COMBINE_FILE_SIZE = 1024L * 1024 * 1024;

    /**
     * 获取aws凭证
     * @return
     */
    public static AWSCredentialsProvider getCredentialsProvider(){
        return new ProfileCredentialsProvider();
    }

    /**
     * 获取s3客户端
     * @param regions
     * @return
     */
    public static AmazonS3 getS3Client(Regions regions){
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(getCredentialsProvider())
                .withRegion(regions)
                .build();
        return s3Client;
    }

    /**
     * 合并parquet文件
     * @param fileInfos
     * @param info
     * @return 结果路径
     * @throws Exception
     */
    public static String mergeParquetFiles(List<FileInfo> fileInfos, String info) throws Exception{

        Configuration conf = new Configuration();
        MessageType schema = MessageTypeParser.parseMessageType(schemaStr);

        //合并后parquet file的文件路径
        String outPath = FOLDER + FILE_SEP + info + FILE_SEP + UUID.randomUUID().toString();

        Path out = new Path(outPath);
        GroupWriteSupport writeSupport = new GroupWriteSupport();
        writeSupport.setSchema(schema, conf);
        ParquetWriter<Group> writer = new ParquetWriter<>(out, conf, writeSupport);

        for (FileInfo fileInfo: fileInfos){
            Path f = new Path(FOLDER + FILE_SEP + info + FILE_SEP + fileInfo.getFileName());
            GroupReadSupport readSupport = new GroupReadSupport();
            ParquetReader<Group> reader = ParquetReader.builder(readSupport, f).build();
            Group line = null;
            while ((line = reader.read()) != null){
                writer.write(line);
            }
        }

        writer.close();
        return outPath;
    }

    public static void main(String args[]) throws Exception {

        //set bucket name
        if (args.length > 0){
            BUCKET_NAME = args[0];
        }

        //key prefix
        if (args.length > 1){
            KEY_PREFIX = args[1];
        }

        // max file size
        if (args.length > 2){
            MAX_COMBINE_FILE_SIZE = Long.valueOf(args[2]);
        }

        String homeDir = System.getProperty("user.home");
        FOLDER = homeDir + FILE_SEP + "file_merger/s3";

        File workDir = new File(FOLDER);
        if (!workDir.exists()){
            workDir.mkdirs();
        }

        AmazonS3 s3Client = getS3Client(Regions.EU_WEST_1);
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(BUCKET_NAME)
                .withPrefix(KEY_PREFIX);

        ObjectListing objectListing = s3Client.listObjects(listObjectsRequest);
        Map<String, List<FileInfo>> fileMapping = new TreeMap<>((o1, o2) -> o1.compareTo(o2));

        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()){
            String key = objectSummary.getKey();
            Long fileSize = objectSummary.getSize();
            String fileKey = key.substring(KEY_PREFIX.length() + 1);
            String[] parts = fileKey.split(FILE_SEP);
            if (parts.length == PARTS_LENGTH){
                String info = parts[0];
                String fileName = parts[1];
                FileInfo fileInfo = new FileInfo(key, fileName, fileSize);
                if (fileMapping.containsKey(info)){
                    fileMapping.get(info).add(fileInfo);
                }else {
                    List<FileInfo> fileInfoList = new ArrayList<>();
                    fileInfoList.add(fileInfo);
                    fileMapping.put(info, fileInfoList);
                }
            }
        }

        combineFiles(fileMapping);
    }

    /**
     * 执行文件合并操作
     * @param fileMapping
     * @throws Exception
     */
    private static void combineFiles(Map<String, List<FileInfo>> fileMapping) throws Exception {
        Set<Map.Entry<String, List<FileInfo>>> entrys = fileMapping.entrySet();

        Iterator<Map.Entry<String, List<FileInfo>>> iterator = entrys.iterator();

        while (iterator.hasNext()){
            Map.Entry<String, List<FileInfo>> entry = iterator.next();
            String info = entry.getKey();
            List<FileInfo> fileInfos = entry.getValue();
            Long freeSpace = getAvailableSpace(FOLDER);

            List<FileInfo> toBeCombineFileList = new ArrayList<>();

            Long addUpSpace = 0L;
            for (FileInfo fileInfo : fileInfos){
                addUpSpace += fileInfo.size;
                freeSpace -= (2 * fileInfo.size);  //包含下载文件的空间和
                //如果
                if (addUpSpace <= MAX_COMBINE_FILE_SIZE && freeSpace > 0){
                    toBeCombineFileList.add(fileInfo);
                }else {
                    if (toBeCombineFileList.size() > 1){
                        doCombineFiles(info, toBeCombineFileList);
                        toBeCombineFileList.clear();
                    }
                    addUpSpace = 0L;
                    freeSpace = getAvailableSpace(FOLDER);
                }
            }

            if (toBeCombineFileList.size() > 1){
                doCombineFiles(info, toBeCombineFileList);
            }
        }
    }

    /**
     * 执行文件合并操作
     * @param info
     * @param fileInfos
     * @throws Exception
     */
    private static void doCombineFiles(String info, List<FileInfo> fileInfos) throws Exception {

        if (CollectionUtils.isNullOrEmpty(fileInfos)){
            throw new Exception("No enough space!");
        }

        String path = FOLDER + FILE_SEP + info;
        File file = new File(path);
        if (!file.exists()){
            file.mkdir();
        }

        //download all files to dir
        for (FileInfo fileInfo : fileInfos){
            String key = fileInfo.getKey();
            downLoadS3File(path, BUCKET_NAME, key, fileInfo.getFileName());
        }

        String outPath = mergeParquetFiles(fileInfos, info);

        File out = new File(outPath);

        String newFileName = System.currentTimeMillis() + UNDERLINE + UUID.randomUUID().toString();
        String key = KEY_PREFIX + FILE_SEP + info + FILE_SEP + newFileName;
        uploadToS3(key, out);

        //删除生成合并后parquet file
        delete(out, true);

        cleanS3File(fileInfos);

        //清除下载到本地的文件
        cleanLocalFile(path, fileInfos);
    }

    /**
     * 删除文件
     * @param file
     * @param recursive
     */
    private static void delete(File file, Boolean recursive) throws IOException {
        if (file.isDirectory()){
            File[] subs = file.listFiles();

            if (!recursive && subs.length > 0){
                throw new IOException("dir " + file.getAbsolutePath() + " couldn't be deleted because it's not an empty dir.");
            }

            for (File f : subs){
                delete(f, true);
            }

        }
        file.deleteOnExit();
    }


    /**
     * 清理本地文件
     * @param path
     * @param fileInfos
     */
    private static void cleanLocalFile(String path, List<FileInfo> fileInfos) {
        for (FileInfo fileInfo : fileInfos){
            String filePath = path + FILE_SEP + fileInfo.getFileName();
            File file = new File(filePath);
            file.deleteOnExit();
        }
    }

    /**
     * 删除s3文件
     * @param fileInfos
     */
    private static void cleanS3File(List<FileInfo> fileInfos) {
        for (FileInfo fileInfo : fileInfos){
            deleteFile(fileInfo.getKey());
        }
    }

    /**
     * 删除单个s3文件
     * @param key
     */
    private static void deleteFile(String key) {
        AmazonS3 s3Client = getS3Client(Regions.EU_WEST_1);
        s3Client.deleteObject(BUCKET_NAME, key);
    }

    /**
     * 上传s3文件
     * @param key
     * @param file
     */
    private static void uploadToS3(String key, File file) {
        AmazonS3 s3Client = getS3Client(Regions.EU_WEST_1);
        s3Client.putObject(BUCKET_NAME, key, file);
    }

    /**
     * 下载s3文件到本地目录
     * @param path
     * @param bucketName
     * @param key
     * @param fileName
     * @return
     * @throws Exception
     */
    private static String downLoadS3File(String path, String bucketName, String key, String fileName) throws Exception {
        AmazonS3 s3Client = getS3Client(Regions.EU_WEST_1);
        S3Object s3Object = s3Client.getObject(bucketName, key);
        String filePath = path + FILE_SEP + fileName;
        File file = new File(filePath);

        //已存在则删除
        if (file.exists()){
            file.deleteOnExit();
        }

        FileOutputStream fos = new FileOutputStream(file);
        IOUtils.copy(s3Object.getObjectContent(), fos);
        fos.close();
        return filePath;
    }

    /**
     * 获取当前磁盘可用空间
     * @param folder
     * @return
     */
    private static Long getAvailableSpace(String folder){
        File file = new File(folder);
        Long freeSpace = file.getFreeSpace();
        return freeSpace;
    }

    @Data
    private static final class FileInfo{
        private String key;
        private String fileName;
        private Long size;

        public FileInfo(String key, String fileName, Long size) {
            this.key = key;
            this.fileName = fileName;
            this.size = size;
        }
    }

}
