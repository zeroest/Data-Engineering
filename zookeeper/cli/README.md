
# [Zookeeper CLI](https://zookeeper.apache.org/doc/r3.8.1/zookeeperCLI.html)

```shell
cd $ZK_HOME
./bin/zkCli.sh -server $host:$clientPort
# ./bin/zkCli.sh -server 127.0.0.1:2181
```

### create

Create a znode  

create [-s] [-e] [-c] [-t ttl] path [data] [acl]

- `-e` create a ephemeral node
  - persistent 와 반대로 서버와 클라이언트 사이의 커넥션이 종료되면 자동으로 지워진다
- `-s` create the persistent-sequential node
- `-c` create the container node.When the last child of a container is deleted,the container becomes to be deleted
- `-t` create the ttl node (ms)


```shell
create /myroot rootvalue
# Created /myroot
create /myroot/app1 app1
# Created /myroot/app1
create /myroot/app2 app2
# Created /myroot/app2
```

### get

Get the data of the specific path

get [-s] [-w] path

- `-s` to show the stat
- `-w` to set a watch on the data change, Notice: turn on the printwatches

```shell
get /myroot
# rootvalue

get -s /myroot
# rootvalue
# cZxid = 0x2
# ctime = Tue Jun 06 04:22:45 UTC 2023
# mZxid = 0xe
# mtime = Tue Jun 06 09:22:25 UTC 2023
# pZxid = 0x8
# cversion = 2
# dataVersion = 2
# aclVersion = 0
# ephemeralOwner = 0x0
# dataLength = 11
# numChildren = 2
```

### set(update)

set [-s] [-v version] path data

- `-s` to show the stat of this node.
- `-v` to set the data with CAS,the version can be found from dataVersion using stat.

```shell
set /myroot rootupdated
```

### delete

delete [-v version] path

```shell
delete /myroot
# Node not empty: /myroot
```

### deleteall

```shell
deleteall /myroot
```

### addWatch

addWatch [-m mode] path # optional mode is one of [PERSISTENT, PERSISTENT_RECURSIVE] - default is PERSISTENT_RECURSIVE

- `-m` watch level 조정
  - PERSISTENT - 해당 노드 자신만
  - PERSISTENT_RECURSIVE - (default) 해당 노드와 자식노드까지
  
### removewatches 

removewatches path [-c|-d|-a] [-l]

### printwatches

printwatches on|off

