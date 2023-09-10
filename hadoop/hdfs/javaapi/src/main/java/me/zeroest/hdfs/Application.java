package me.zeroest.hdfs;

import java.io.IOException;

public class Application {
    private static final String defaultFs = "hdfs://bd1:9000";

    public static void main(String[] args) throws IOException {
        String directoryName = "/tmp/test/";

        HdfsUtil.createDirectory(defaultFs, directoryName);

        HdfsUtil.writeFile(defaultFs, directoryName + "Hello.txt", "World!!!");

        HdfsUtil.readFile(defaultFs, directoryName + "Hello.txt");
    }
}
