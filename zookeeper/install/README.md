
# Zookeeper Install

[Started Guide](https://zookeeper.apache.org/doc/r3.1.2/zookeeperStarted.html)

[Required Software](https://zookeeper.apache.org/doc/r3.1.2/zookeeperAdmin.html#sc_systemReq)
- ZooKeeper runs in Java, release 1.6 or greater (JDK 6 or greater). It runs as an ensemble of ZooKeeper servers. Three ZooKeeper servers is the minimum recommended size for an ensemble, and we also recommend that they run on separate machines. At Yahoo!, ZooKeeper is usually deployed on dedicated RHEL boxes, with dual-core processors, 2GB of RAM, and 80GB IDE hard drives.

[Release](https://zookeeper.apache.org/releases.html)

[Download zookeeper 3.7.1](https://dlcdn.apache.org/zookeeper/zookeeper-3.7.1/apache-zookeeper-3.7.1-bin.tar.gz)

## Download & Setup

```bash
# Download
wget https://dlcdn.apache.org/zookeeper/zookeeper-3.7.1/apache-zookeeper-3.7.1-bin.tar.gz

# Untar
tar xvzf apache-zookeeper-3.7.1-bin.tar.gz
mv apache-zookeeper-3.7.1-bin zookeeper
cd zookeeper

# Set zookeeper home
echo "export ZK_HOME=$(pwd)" >> ~/.zshrc && source ~/.zshrc
```

## [Standalone](https://zookeeper.apache.org/doc/r3.1.2/zookeeperStarted.html#sc_InstallingSingleMode)

Config setup
```bash
cp conf/zoo_sample.cfg zoo.cfg
vim conf/zoo.cfg

# dataDir           // 데이터 저장 경로 수정
# clientPort        // 포트 설정
# Metrics Providers // Prometheus 모니터링을 위한 Exporter 설정
```

Start zookeeper
```bash
bin/zkServer.sh start conf/zoo.cfg
```

Stop zookeeper
```bash
bin/zkServer.sh stop conf/zoo.cfg
```


