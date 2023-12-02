
# Zookeeper

## [ZooKeeper Administrator's Guide](https://zookeeper.apache.org/doc/r3.3.6/zookeeperAdmin.html)

서버 다운과 같은 상황에서 트랜잭션 로그, 스냅샷 데이터를 기반으로 메모리에 재적재  
성능 향상을 위해 트랜잭션 로그를 따로 저장하도록 설정해야한다
> ZooKeeper stores its data in a data directory and its transaction log in a transaction log directory. By default these two directories are the same. The server can (and should) be configured to store the transaction log files in a separate directory than the data files. Throughput increases and latency decreases when transaction logs reside on a dedicated log devices.

모든 데이터를 메모리에 저장 jvm heap memory 가 저장하는 데이터를 충분히 담을 수 있도록 설정 필요
스왑 발생시 순차적으로 처리되는 큐의 모든 작업에 영향을 끼치기 때문에 스왑이 발생하지 않도록 heap memory 설정
> You should take special care to set your Java max heap size correctly. In particular, you should not create a situation in which ZooKeeper swaps to disk. The disk is death to ZooKeeper. Everything is ordered, so if processing one request swaps the disk, all other queued requests will probably do the same. the disk. DON'T SWAP.

znode 사이즈는 기본 0xfffff(1048575) 이며 1MB 아래로 설정할것을 권장
> jute.maxbuffer: (Java system property: jute.maxbuffer)  
> This option can only be set as a Java system property. There is no zookeeper prefix on it. It specifies the maximum size of the data that can be stored in a znode. The default is 0xfffff, or just under 1M. If this option is changed, the system property must be set on all servers and clients otherwise problems will arise. This is really a sanity check. ZooKeeper is designed to store data on the order of kilobytes in size.


## [ZooKeeper Programmer's Guide](https://zookeeper.apache.org/doc/r3.3.6/zookeeperProgrammers.html)

### ZNodes

노드별 ACL 설정 가능  
자식에게 recursive 하게 적용되지 않음  
schema를 사용하여 user:group:other 와 비슷한 개념을 구현
> Each node has an Access Control List (ACL) that restricts who can do what.  
> CREATE, READ, WRITE, DELETE, ADMIN  
> Note also that an ACL pertains only to a specific znode. In particular it does not apply to children. For example, if /app is only readable by ip:172.16.16.1 and /app/status is world readable, anyone will be able to read /app/status; ACLs are not recursive.  
> 
> ---  
> 
> ACL Schemes  
> **world** has a single id, anyone, that represents anyone.  
> **auth** doesn't use any id, represents any authenticated user.  
> **digest** uses a username:password string to generate MD5 hash which is then used as an ACL ID identity. Authentication is done by sending the username:password in clear text. When used in the ACL the expression will be the username:base64 encoded SHA1 password digest.  
> **ip** uses the client host IP as an ACL ID identity. The ACL expression is of the form addr/bits where the most significant bits of addr are matched against the most significant bits of the client host IP.  

Ephemeral Nodes  
Ephemeral 은 세션 종료와 동시에 제거됨, 이로인해 child 노드 생성이 불가
> ZooKeeper also has the notion of ephemeral nodes. These znodes exists as long as the session that created the znode is active. When the session ends the znode is deleted. Because of this behavior ephemeral znodes are not allowed to have children.

Sequence Nodes - Unique Naming  
znode의 uniqueness를 보장 설정한 노드네임 뒤로 시퀀셜한 숫자를 atomic 하게 붙여줌
> This counter is unique to the parent znode. The counter has a format of %010d -- that is 10 digits with 0 (zero) padding (the counter is formatted in this way to simplify sorting), i.e. "<path>0000000001".  
> Note: the counter used to store the next sequence number is a signed int (4bytes) maintained by the parent node, the counter will overflow when incremented beyond 2147483647 (resulting in a name "<path>-2147483647").

### Stat

> ZooKeeper Stat Structure  
> The Stat structure for each znode in ZooKeeper is made up of the following fields:  
> - czxid  
> The zxid of the change that caused this znode to be created.
> - mzxid  
> The zxid of the change that last modified this znode.
> - ctime  
> The time in milliseconds from epoch when this znode was created.
> - mtime  
> The time in milliseconds from epoch when this znode was last modified.
> - version  
> The number of changes to the data of this znode.
> - cversion  
> The number of changes to the children of this znode.
> - aversion  
> The number of changes to the ACL of this znode.
> - ephemeralOwner  
> The session id of the owner of this znode if the znode is an ephemeral node. If it is not an ephemeral node, it will be zero.
> - dataLength  
> The length of the data field of this znode.
> - numChildren  
> The number of children of this znode.

## [ZooKeeper Recipes and Solutions](https://zookeeper.apache.org/doc/current/recipes.html#sc_leaderElection)

## [Install](./install/README.md)
## [CLI](./cli/README.md)
