package me.zeroest.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class HdfsUtil {

    private static final Logger logger = LoggerFactory.getLogger(HdfsUtil.class);

    public static void createDirectory(String defaultFs, String directoryName) throws IOException {
        logger.info("create directory | {}", directoryName);

        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", defaultFs);
        FileSystem fileSystem = FileSystem.get(configuration);
        Path path = new Path(directoryName);
        fileSystem.mkdirs(path);

        logger.info("fin create directory");
    }

    public static void writeFile(String defaultFs, String filePathWithName, String contents) throws IOException {
        logger.info("write file | {}", filePathWithName);

        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", defaultFs);
        FileSystem fileSystem = FileSystem.get(configuration);
        //Create a path
        Path hdfsWritePath = new Path(filePathWithName);
        FSDataOutputStream fsDataOutputStream = fileSystem.create(hdfsWritePath, true);

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fsDataOutputStream, StandardCharsets.UTF_8));
        bufferedWriter.write(contents);
        bufferedWriter.newLine();
        bufferedWriter.close();
        fileSystem.close();

        logger.info("fin write file");
    }

    public static void readFile(String defaultFs, String filePathWithName) throws IOException {
        logger.info("read file | {}", filePathWithName);

        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", defaultFs);
        FileSystem fileSystem = FileSystem.get(configuration);
        //Create a path
        Path hdfsReadPath = new Path(filePathWithName);
        //Init input stream
        FSDataInputStream inputStream = fileSystem.open(hdfsReadPath);
        //Classical input stream usage
//        String out= IOUtils.toString(inputStream, "UTF-8");
//        System.out.println(out);

        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        String line;
        while ((line=bufferedReader.readLine())!=null){
            System.out.println(line);
        }

        inputStream.close();
        fileSystem.close();

        logger.info("fin read file");
    }
}
