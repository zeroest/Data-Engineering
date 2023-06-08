package me.zeroest.zookeeper;

import org.apache.zookeeper.AddWatchMode;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        try (ZooKeeper zk = new ZookeeperConnection().connect()) {
            String actualPath = zk.create(
                "/hello",
                "world".getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT
            );
            logger.info("Actual path: {}", actualPath);

            Stat stat = new Stat();
            byte[] data = zk.getData(actualPath, null, stat);
            String convertData = new String(data);
            logger.info("Get data: {}, {}", convertData, stat);

            zk.addWatch(
                actualPath,
                event -> {
                    Watcher.Event.EventType type = event.getType();
                    logger.warn("Changed| Path: {}, Type: {}", event.getPath(), type);
                },
                AddWatchMode.PERSISTENT_RECURSIVE
            );

            int latestVersion = zk.exists(actualPath, true).getVersion();
            zk.setData(
                actualPath,
                "world!!!".getBytes(StandardCharsets.UTF_8),
                latestVersion // -1 force
            );

            byte[] updatedData = zk.getData(actualPath, null, stat);
            String convertUpdatedData = new String(updatedData);
            logger.info("Updated data: {}, {}", convertUpdatedData, stat);

            zk.delete(actualPath, -1);
        } catch (KeeperException | InterruptedException e) {
            // java:S2142 | Restore interrupted state...
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}
