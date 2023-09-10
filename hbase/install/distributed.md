
# Distributed

> HBase는 HDFS기반으로 작동한다.  
> 따라서 반드시 Hadoop 환경을 구성해야한다.  
> Hadoop 클러스터 3대의 서버에 HBase를 설치하는 것을 기반으로 설명한다.

Hbase의 JDK 및 Hadoop 간의 의존성/연관성(compatible) 버전 체크
- [Prerequisites](https://hbase.apache.org/book.html#basic.prerequisites)

## Download

[HBase Downloads](hbase.apache.org/downloads.html)

```bash
wget https://dlcdn.apache.org/hbase/2.5.5/hbase-2.5.5-bin.tar.gz
tar xvzf hbase-2.5.5-bin.tar.gz
mv hbase-2.5.5 hbase
```

## Environment

HBase 는 JDK 또한 필요로 하는데 Hadoop 설치시 JAVA_HOME 설정되어 있다는 전제조건 하에 추가로 설정하지 않는다.

### Hbase Home
```bash
cat << EOF >> ~/.bashrc
### hbase
export HBASE_HOME=/home/ubuntu/hbase
export HBASE_CONF_DIR=$HBASE_HOME/conf
export PATH=$PATH:$HBASE_HOME/bin
export HBASE_LOG_DIR=$HBASE_HOME/log

EOF

source ~/.bashrc
echo $HBASE_HOME
```

### hbase-site.xml

HBase 내장 Zookeeper 운영시 HQuorumPeer
Zookeeper 를 직접 구성하여 운영 QuorumPeerMain 

[[docs] zookeeper 설정](http://svn.apache.org/repos/asf/hbase/hbase.apache.org/trunk/0.94/book/zookeeper.html)

- hbase.rootdir : 여기에 설정된 디렉토리에 데이터베이스 파일을 관리한다. hdfs masternode의 URL을 입력하면 된다. hadoop 명령으로 URL을 확인 할 수 있다.
```bash
hadoop fs -df -h
# Filesystem         Size   Used  Available  Use%
# hdfs://bd1:9000  38.4 G  216 K     24.0 G    0%
```
- hbase.cluster.distributed : Fully distributed 모드로 구축하기 위해서 true를 설정했다.
- hbase.zookeeper.quorum : zookeeper quorum 설정을 한다.
- dfs.replication : 복제 갯수를 설정한다. 기본은 3 이다.
- hbase.zookeeper.property.clientPort : zookeeper 클라이언트 포트. 기본은 2181이다.
- hbase.wal.provider
  - HBase(Hadoop의 NoSQL 데이터베이스)에서 Write-Ahead Logging(WAL) 기능을 제어하기 위한 설정입니다. WAL은 데이터베이스의 변경 사항을 기록하는 데 사용되며, 데이터의 내구성을 보장하고 데이터 손실을 방지하는 데 중요한 역할을 합니다.
  - `hbase.wal.provider` 설정은 다양한 WAL 프로바이더를 지정하는 데 사용됩니다. HBase는 여러 WAL 프로바이더를 지원하며, 주요 WAL 프로바이더 중 하나를 선택하여 데이터베이스의 요구 사항에 맞게 구성할 수 있습니다. 주요 WAL 프로바이더에는 다음과 같은 것들이 있습니다
    - asyncfs: 이 프로바이더는 Hadoop HDFS의 HSync 기능을 사용하여 WAL을 비동기적으로 기록합니다. 이는 일반적으로 HBase에서 기본값으로 사용되는 프로바이더입니다.
    - filesystem: 이 프로바이더는 HDFS 파일 시스템을 직접 사용하여 WAL을 기록합니다. 일부 특수한 상황에서 사용할 수 있습니다.
    - memory: 이 프로바이더는 메모리에만 WAL을 기록하며, 데이터의 내구성을 보장하지 않습니다. 따라서 특수한 테스트 용도나 개발 환경에서 사용됩니다.

#### QuorumPeerMain
```bash
vim $HBASE_HOME/conf/hbase-site.xml

<configuration>
	<property>
		<name>hbase.rootdir</name>
		<value>hdfs://bd1:9000/hbase</value>
	</property>
	<property>
		<name>hbase.cluster.distributed</name>
		<value>true</value>
	</property>
	<property>
		<name>dfs.replication</name>
		<value>2</value>
	</property>

	<property>
		<name>hbase.zookeeper.quorum</name>
		<value>bd1,bd2,bd3</value>
	</property>
	<property>
		<name>hbase.zookeeper.property.clientPort</name>
		<value>2181</value> 
	</property> 
	<property>
        <name>hbase.wal.provider</name>
        <value>filesystem</value>
    </property>
</configuration>
```

### hbase-env.sh

```bash
mkdir $HBASE_HOME/pids
vim $HBASE_HOME/conf/hbase-env.sh

export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export HBASE_MANAGES_ZK=false
export HBASE_PID_DIR=/home/ubuntu/hbase
```

### regionservers

> Region은 HBase 에서의 기본단위로, 이것을 이용해서 수평 확장을 한다. 테이블들은 각 Region에 분산되서 저장이 된다. 처음에는 하나의 Region만 있지만, row가 추가되서 충분히 커지면, 다른 region server 들로 분산된다.

```
bd1
bd2
bd3
```

### backup-masters

[[Cloudera] HBase High Availability](https://docs.cloudera.com/HDPDocuments/Ambari-2.6.0.0/bk_ambari-operations/content/hbase_high_availability.html)

> Configure HBase to use node-2 as a backup Master by creating a new file in conf/ called backup-Masters, and adding a new line to it with the host name for node-2: for example, node-2.test.com.

```bash
vim $HBASE_HOME/conf/backup-masters

bd2
```

## Start

HDFS의 safe mode 확인할것
```bash
hadoop dfsadmin -safemode leave
```

master 노드에서만 start-hbase.sh 스크립트를 실행해주자

```bash
$HBASE_HOME/bin/start-hbase.sh

running master, logging to /home/ubuntu/hbase/log/hbase-ubuntu-master-ip-192-168-0-180.out
bd3: running regionserver, logging to /home/ubuntu/hbase/bin/../logs/hbase-ubuntu-regionserver-ip-192-168-0-157.out
bd2: running regionserver, logging to /home/ubuntu/hbase/bin/../logs/hbase-ubuntu-regionserver-ip-192-168-0-218.out
bd1: running regionserver, logging to /home/ubuntu/hbase/bin/../logs/hbase-ubuntu-regionserver-ip-192-168-0-180.out
```

# Reference

https://www.joinc.co.kr/w/man/12/hadoop/hbase/install
https://velog.io/@bbkyoo/HBase-%EA%B5%AC%EC%84%B1-Full-distributed-8yhdsoho
