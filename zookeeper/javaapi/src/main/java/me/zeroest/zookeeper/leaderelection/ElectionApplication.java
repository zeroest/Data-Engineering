package me.zeroest.zookeeper.leaderelection;

import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ElectionApplication {
    private static final Logger logger = LoggerFactory.getLogger(ElectionApplication.class);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        LeaderElection leaderElection = new LeaderElection();
        leaderElection.connectToZookeper();
        leaderElection.volunteerForLeadership();
        leaderElection.reelectLeader();
        leaderElection.run();
        leaderElection.close();
        logger.info("Disconnected from zooKeeper, exiting application!!");
    }
}
