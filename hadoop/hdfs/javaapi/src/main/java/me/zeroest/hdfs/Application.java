package me.zeroest.hdfs;

import java.io.IOException;

import static me.zeroest.hdfs.HdfsUtil.createDirectory;
import static me.zeroest.hdfs.HdfsUtil.readFile;
import static me.zeroest.hdfs.HdfsUtil.writeFile;

public class Application {
    private static final String defaultFs = "hdfs://bd1:9000";

    public static void main(String[] args) throws IOException {
        String directoryName = "/tmp/test/";

        createDirectory(defaultFs, directoryName);

        writeFile(defaultFs, directoryName + "Hello.txt", "World!!!");

        readFile(defaultFs, directoryName + "Hello.txt");
    }
}
