
# Standalone

## Requirement

System default
```bash
sudo apt update && \
sudo apt install build-essential -y
```

Profile
```bash
cat << EOF >> ~/.bashrc
# Profile
if [ -f ~/.bash_profile ]; then
    . ~/.bash_profile
fi
EOF
```

JDK
```bash
sudo apt install openjdk-17-jdk -y

# which java 
echo 'export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64' >> ~/.bash_profile \
 && source ~/.bash_profile
```

## Install

### stable 버전의 hbase 다운로드
`https://dlcdn.apache.org/hbase/stable/` 

### Download hbase
```base
wget https://dlcdn.apache.org/hbase/stable/hbase-2.5.5-bin.tar.gz
tar xvzf hbase-2.5.5-bin.tar.gz
cd hbase-2.5.5
```

### Profile
```bash
export HBASE_HOME=/home/ubuntu/hbase-2.5.5 >> ~/.bash_profile \
 && source ~/.bash_profile
```

### Run HBase Server
```bash
$HBASE_HOME/bin/start-hbase.sh
# running master, logging to /home/ubuntu/hbase-2.5.5/logs/hbase-ubuntu-master-ip-192-168-0-137.out

ps -ef | grep hbase
# ubuntu     11155       1  0 09:04 pts/0    00:00:00 bash /home/ubuntu/hbase-2.5.5/bin/hbase-daemon.sh --config /home/ubuntu/hbase-2.5.5/conf foreground_start master
# ubuntu     11167   11155 57 09:04 pts/0    00:00:13 /usr/lib/jvm/java-17-openjdk-amd64/bin/java -Dproc_master -XX:OnOutOfMemoryError=kill -9 %p -XX:+UseG1GC -Djava.util.logging.config.class=org.apache.hadoop.hbase.logging.JulToSlf4jInitializer -Dorg.apache.hbase.thirdparty.io.netty.tryReflectionSetAccessible=true --add-modules jdk.unsupported --add-opens java.base/java.nio=ALL-UNNAMED --add-opens java.base/sun.nio.ch=ALL-UNNAMED --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/jdk.internal.ref=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-exports java.security.jgss/sun.security.krb5=ALL-UNNAMED -Dhbase.log.dir=/home/ubuntu/hbase-2.5.5/logs -Dhbase.log.file=hbase-ubuntu-master-ip-192-168-0-137.log -Dhbase.home.dir=/home/ubuntu/hbase-2.5.5 -Dhbase.id.str=ubuntu -Dhbase.root.logger=INFO,RFA -Dhbase.security.logger=INFO,RFAS org.apache.hadoop.hbase.master.HMaster start

# check zookeeper
netstat | grep 2181
```

Web ui
```
${server.host}:16010
```

CLI
```bash
$HBASE_HOME/bin/hbase shell

# HBase Shell
# Use "help" to get list of supported commands.
# Use "exit" to quit this interactive shell.
# For Reference, please visit: http://hbase.apache.org/2.0/book.html#shell
# Version 2.5.5, r7ebd4381261fefd78fc2acf258a95184f4147cee, Thu Jun  1 17:42:49 PDT 2023
# Took 0.0032 seconds
```
