
# Zookeeper Install

[Started Guide](https://zookeeper.apache.org/doc/r3.1.2/zookeeperStarted.html)

[Required Software](https://zookeeper.apache.org/doc/r3.1.2/zookeeperAdmin.html#sc_systemReq)
- ZooKeeper runs in Java, release 1.6 or greater (JDK 6 or greater). It runs as an ensemble of ZooKeeper servers. Three ZooKeeper servers is the minimum recommended size for an ensemble, and we also recommend that they run on separate machines. At Yahoo!, ZooKeeper is usually deployed on dedicated RHEL boxes, with dual-core processors, 2GB of RAM, and 80GB IDE hard drives.

[Release](https://zookeeper.apache.org/releases.html)

[Download zookeeper 3.7.1](https://dlcdn.apache.org/zookeeper/zookeeper-3.7.1/apache-zookeeper-3.7.1-bin.tar.gz)

## Download & Setup

```shell
# Download
wget https://dlcdn.apache.org/zookeeper/zookeeper-3.7.1/apache-zookeeper-3.7.1-bin.tar.gz

# Untar
tar xvzf apache-zookeeper-3.7.1-bin.tar.gz
mv apache-zookeeper-3.7.1-bin zookeeper
cd zookeeper

# Set zookeeper home
echo "export ZK_HOME=$(pwd)" >> ~/.bashrc && source ~/.bashrc
```

## [Standalone](https://zookeeper.apache.org/doc/r3.1.2/zookeeperStarted.html#sc_InstallingSingleMode)

Config setup
```shell
cp conf/zoo_sample.cfg conf/zoo.cfg
vim conf/zoo.cfg

# dataDir           // 데이터 저장 경로 수정
# clientPort        // 포트 설정
# Metrics Providers // Prometheus 모니터링을 위한 Exporter 설정
```

Start zookeeper
```shell
bin/zkServer.sh start conf/zoo.cfg
```

Stop zookeeper
```shell
bin/zkServer.sh stop conf/zoo.cfg
```

## [Clustered](https://zookeeper.apache.org/doc/r3.7.1/zookeeperStarted.html#sc_RunningReplicatedZooKeeper)

Config setup

zoo.cfg 파일에 클러스터로 구성된 자기 자신을 포함하여 모든 서버 정보를 저장한다
- `$host`:`$port_follower_connect_leader`:`$port_for_leader_election`
  - `$host` - zookeeper 서버에서 접근 가능한 hostname 또는 IP 주소
  - `$port_follower_connect_leader` - follower 가 leader 와 connection 을 맺을때 사용하는 port
  - `$port_for_leader_election` - leader election 때 사용하는 포트
```shell
# server 1
server.1=0.0.0.0:2888:3888
server.2=192.168.0.164:2888:3888
server.3=192.168.0.83:2888:3888

# server 2
server.1=192.168.0.213:2888:3888
server.2=0.0.0.0:2888:3888
server.3=192.168.0.83:2888:3888

# server 3
server.1=192.168.0.213:2888:3888
server.2=192.168.0.164:2888:3888
server.3=0.0.0.0:2888:3888
```

서버별 독립적인 아이디 번호를 가지고 있어야 한다 
- 설정한 dataDir 폴더에 myid 파일에 저장한다
- myid 파일을 보고 자신이 몇번 서버인지 인식한다
```shell
# server 1
echo 1 > ~/zookeeper/data/myid
# server 2
echo 2 > ~/zookeeper/data/myid
# server 3
echo 3 > ~/zookeeper/data/myid

cp conf/zoo_sample.cfg conf/zoo.cfg
vim conf/zoo.cfg

# dataDir           // 데이터 저장 경로 수정
# clientPort        // 포트 설정
# Metrics Providers // Prometheus 모니터링을 위한 Exporter 설정
```
