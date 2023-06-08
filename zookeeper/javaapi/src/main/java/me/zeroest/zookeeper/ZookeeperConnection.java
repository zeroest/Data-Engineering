package me.zeroest.zookeeper;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZookeeperConnection {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperConnection.class);

    private final CountDownLatch connectedSignal = new CountDownLatch(1);

    public ZooKeeper connect() {
        ZookeeperConfig config = ZookeeperConfig.getConfig();

        try {
            // retry or throw
            ZooKeeper zk = new ZooKeeper(config.getConnectString(), 3000, event -> {
                if (Watcher.Event.KeeperState.SyncConnected.equals(event.getState())) {
                    logger.info("### Success to connect zookeeper");
                    connectedSignal.countDown();
                }
                if (
                    Watcher.Event.KeeperState.AuthFailed.equals(event.getState())
                        || Watcher.Event.KeeperState.Disconnected.equals(event.getState())
                ) {
                    logger.info("### Fail to connect zookeeper");
                    // retry or throw
                }
                if (Watcher.Event.KeeperState.Closed.equals(event.getState())) {
                    logger.info("### Close connect zookeeper");
                }
            });
            connectedSignal.await();

            return zk;
        } catch (IOException | InterruptedException e) {
            // java:S2142 | Restore interrupted state...
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Fail to create connection");
        }
    }
}
