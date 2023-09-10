
# Install Hadoop Cluster

[[heekangpark] Hadoop 설치하기](https://heekangpark.github.io/hadoop/hadoop-installation)

[[sparkdia] 하둡(Hadoop) 설치하기](https://sparkdia.tistory.com/7)

[Hadoop Download](https://hadoop.apache.org/releases.html)

```shell
# Create group hadoop
sudo groupadd -g 10000 hadoop
tail -1 /etc/group

# Create user
sudo useradd -g hadoop -u 10000 hduser
tail -1 /etc/passwd

# Change password
sudo passwd hduser # hdhdhd

# Add authority sudo
sudo chmod 640 /etc/sudoers
sudo vim /etc/sudoers # hduser  ALL=(ALL)       NOPASSWD: ALL
```

```shell
# Install java
sudo apt update
sudo apt install openjdk-11-jdk

# Download hadoop
wget https://dlcdn.apache.org/hadoop/common/hadoop-3.3.5/hadoop-3.3.5.tar.gz
tar xzf hadoop-3.3.5.tar.gz
mv hadoop-3.3.5 hadoop

# Change owner
sudo chown -R hduser:hadoop /home/ubuntu/hadoop

# Profile setup
vim ~/.bashrc
# export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64/
# export HADOOP_HOME=/home/ubuntu/hadoop/
# export PATH=$PATH:$JAVA_HOME/bin:$HADOOP_HOME/bin/:$HADOOP_HOME/sbin
```

```shell
sudo update-alternatives --config java

vim $HADOOP_HOME/etc/hadoop/hadoop-env.sh
# export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
# export HDFS_NAMENODE_USER=root
# export HDFS_DATANODE_USER=root
# export HDFS_SECONDARYNAMENODE_USER=root
# export YARN_RESOURCEMANAGER_USER=root
# export YARN_NODEMANAGER_USER=root
```

| 파일명 | 설명 |
|---|---|
| etc/hadoop/core-site.xml	 | 클러스터 내 네임노드에서 실행되는 하둡 데몬에 관한 설정 |
| etc/hadoop/hdfs-site.xml	 | 하둡 파일시스템에 관한 설정 |
| etc/hadoop/yarn-site.xml	 | Resource Manager에 관한 설정 |
| etc/hadoop/mapred-site.xml | 	맵리듀스에 관한 설정 |

core-site.xml  
기본 파일 시스템 이름 설정
[zookeeper 설정](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-hdfs/HDFSHighAvailabilityWithQJM.html) - 하둡의 고가용성을 위한 설정
```xml
<configuration>
    <property>
        <name>fs.defaultFS</name>
         <value>hdfs://bd1:9000</value>
    </property>
    <property>
        <name>ha.zookeeper.quorum</name>
        <value>bd1:2181,bd2:2181,bd3:2181</value>
    </property>
</configuration>
```

hdfs-site.xml  
namespace와 트랙잭션 로그를 저장 할 네임노드와 데이터 노드의 저장 경로를 지정하고, 데이터 복제 개수를 설정
```shell
mkdir -p /home/ubuntu/hadoop-namenode
mkdir -p /home/ubuntu/hadoop-datanode
```
```xml
<configuration>
    <property>
        <name>dfs.namenode.name.dir</name>
         <value>file:///home/ubuntu/hadoop-namenode</value>
    </property>
    <property>
        <name>dfs.datanode.data.dir</name>
         <value>file:///home/ubuntu/hadoop-datanode</value>
    </property>
    <property>
         <name>dfs.namenode.checkpoint.dir</name>
         <value>file:///home/ubuntu/hadoop-secondary-namenode</value>
    </property>
    <property>
        <name>dfs.namenode.secondary.http-address</name>
        <value>bd2:50090</value>
    </property>
    <property>
        <name>dfs.replication</name>
         <value>2</value>
    </property>
</configuration>
```

yarn-site.xml  
리소스매니저 Web-ui 주소, 노드매니저에서 중간단계 파일 및 로그를 저장할 경로를 정의

```xml
<configuration>
    <property>
        <name>yarn.resourcemanager.hostname</name>
        <value>bd1</value>
    </property>
    <property>
        <name>yarn.nodemanager.local-dirs</name>
         <value>file:///data/yarn/local</value>
    </property>
    <property>
        <name>yarn.nodemanager.log-dirs</name>
         <value>file:///data/yarn/logs</value>
    </property>
</configuration>
```

mapred-site.xml  
기본 맵리듀스 프레임워크로 yarn을 설정
```xml
<configuration>
    <property>
        <name>mapreduce.framework.name</name>
         <value>yarn</value>
    </property>
</configuration>
```

hosts 설정
```shell
sudo vim /etc/hosts
# 192.168.0.180 bd1
# 192.168.0.218 bd2
# 192.168.0.157 bd3
```

slave 설정
```shell
sudo vim $HADOOP_HOME/etc/hadoop/workers
# bd2
# bd3
```

ssh 설정
```shell
# Generate keygen
ssh-keygen

# Duplicate key
ssh-copy-id bd1
ssh-copy-id bd2
ssh-copy-id bd3
```

Hadoop 파일 시스템 포맷
```shell
bin/hdfs namenode -format
```

HDFS 시작
```shell
sbin/start-dfs.sh
```

웹을 통해 HDFS 정보 확인  
- http://bd1:9870

Yarn 시작
```shell
sbin/start-yarn.sh
```

웹을 통해 Yarn 정보 확인
- http://bd1:8088

Test
```shell
wget https://www.gutenberg.org/cache/epub/10/pg10.txt

hdfs dfs -mkdir -p /user/hadoop
hdfs dfs -put pg10.txt /user/hadoop/pg10.txt

hdfs dfs -ls /user/hadoop/

hadoop jar $HADOOP_HOME/share/hadoop/mapreduce/hadoop-mapreduce-examples-3.3.5.jar wordcount /user/hadoop/pg10.txt /user/hadoop/hadoop-wordcount-output

hdfs dfs -ls /user/hadoop/hadoop-wordcount-output # hadoop-wordcount-output 디렉토리 탐색
hdfs dfs -cat /user/hadoop/hadoop-wordcount-output/part-r-00000 # wordcount 결과 확인
hdfs dfs -get /user/hadoop/hadoop-wordcount-output/part-r-00000 # 결과 파일 (로컬로) 다운로드
```
