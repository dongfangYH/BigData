package am.ps.filemerger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.hadoop.ParquetFileReader;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.GroupReadSupport;
import org.apache.parquet.hadoop.example.GroupWriteSupport;
import org.apache.parquet.hadoop.metadata.ParquetMetadata;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.apache.parquet.format.converter.ParquetMetadataConverter.NO_FILTER;

public class Test {

    private static final String schemaStr = "message hive_schema {\n" +
            "  optional binary packagename (UTF8);\n" +
            "  optional binary teyeid (UTF8);\n" +
            "  optional binary appversion (UTF8);\n" +
            "  optional binary basic_devicename (UTF8);\n" +
            "}";

    public static void main(String[] args) throws Exception{
        Configuration conf = new Configuration();
        MessageType schema = MessageTypeParser.parseMessageType(schemaStr);

        FileSystem fs = FileSystem.get(conf);
        Path root = new Path("/local/sda/data/s3/info=1970-01-01");
        RemoteIterator<LocatedFileStatus> fileIter = fs.listFiles(root, true);
        String outPath = "/local/sda/data/s3/info=1970-01-01/" + UUID.randomUUID().toString();

        Path out = new Path(outPath);
        GroupWriteSupport writeSupport = new GroupWriteSupport();
        writeSupport.setSchema(schema, conf);
        ParquetWriter<Group> writer = new ParquetWriter<>(out, conf, writeSupport);

        while (fileIter.hasNext()){
            Path file = fileIter.next().getPath();
            GroupReadSupport readSupport = new GroupReadSupport();
            ParquetReader<Group> reader = ParquetReader.builder(readSupport, file).build();
            Group line = null;
            while ((line = reader.read()) != null){
                writer.write(line);
            }
        }

        writer.close();

    }
}
