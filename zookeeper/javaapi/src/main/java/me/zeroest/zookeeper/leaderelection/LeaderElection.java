package me.zeroest.zookeeper.leaderelection;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class LeaderElection implements Watcher {
    private static final Logger logger = LoggerFactory.getLogger(LeaderElection.class);

    private static final String ZOOKEEPER_ADDRESS = "zookeeper:2181";
    private ZooKeeper zooKeeper;
    private static final int SESSION_TIMEOUT = 3000;
    private static final String ELECTION_NAMESPACE = "/election";
    private String currentZNodeName;

    public void volunteerForLeadership() throws KeeperException, InterruptedException {
        String zNodePrefix = ELECTION_NAMESPACE + "/c_";
        String zNodeFullPath = zooKeeper.create(zNodePrefix, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        logger.info("znode_name: {}", zNodeFullPath);
        this.currentZNodeName = zNodeFullPath.replace(ELECTION_NAMESPACE + "/", "");
    }

    public void reelectLeader() throws KeeperException, InterruptedException {
        Stat predecessorStat = null;
        String predecessorZnodeName = "";

        while (predecessorStat == null) {
            List<String> children = zooKeeper.getChildren(ELECTION_NAMESPACE, false);
            Collections.sort(children);

            String smallestChild = children.get(0);

            if (smallestChild.equals(currentZNodeName)) {
                logger.info("I am the leader !!");
            } else {
                logger.info("I am not the leader ");
                int predecessorIndex = Collections.binarySearch(children, currentZNodeName) - 1;
                predecessorZnodeName = children.get(predecessorIndex);
                predecessorStat = zooKeeper.exists(ELECTION_NAMESPACE + "/" + predecessorZnodeName, this);
            }
        }

        logger.info("Watching node: {}", predecessorZnodeName);
    }

    public void watchTargetZNode() throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(ELECTION_NAMESPACE, this);
        if (stat == null) {
            return;
        }

        byte[] data = zooKeeper.getData(ELECTION_NAMESPACE, this, stat);
        List<String> children = zooKeeper.getChildren(ELECTION_NAMESPACE, this);

        String dataString = new String(data);
        logger.info("Data: {}, Children: {}", dataString, children);
    }

    public void connectToZookeper() throws IOException {
        this.zooKeeper = new ZooKeeper(ZOOKEEPER_ADDRESS, SESSION_TIMEOUT, this);
    }

    public void run() throws InterruptedException {
        synchronized (zooKeeper) {
            zooKeeper.wait();
        }
    }

    public void close() throws InterruptedException {
        zooKeeper.close();
    }

    @Override
    public void process(WatchedEvent event) {
        switch (event.getType()) {
            case None:
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    logger.info("Successfully connected to zookeeper!!");
                } else {
                    synchronized (zooKeeper) {
                        logger.info("Disconnected from zookeeper event");
                        zooKeeper.notifyAll();
                    }
                }
                break;
            case NodeDeleted:
                try {
                    reelectLeader();
                } catch (KeeperException | InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                logger.info(ELECTION_NAMESPACE + " got deleted!!");
            case NodeCreated:
                logger.info(ELECTION_NAMESPACE + " got created!!");
            case NodeDataChanged:
                logger.info(ELECTION_NAMESPACE + " data changed!!");
            case NodeChildrenChanged:
                logger.info(ELECTION_NAMESPACE + " gchildren changed!!");
            default:
                break;
        }

        try {
            watchTargetZNode();
        } catch (KeeperException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }

    }
}