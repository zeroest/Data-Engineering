
# Zookeeper Java API

### Dependencies
```groovy
// https://mvnrepository.com/artifact/org.apache.zookeeper/zookeeper
implementation 'org.apache.zookeeper:zookeeper:3.8.1'
```

# Leader Election Logic

주키퍼 코디네이션 기능을 이용해서 리더 선출 로직 구현 

직접 구현시 발생할 수 있는 문제
- 동기화 이슈
- 락 관련 이슈
- 서비스 디스커버리 이슈

pseudo code
- https://github.com/arpendu11/leader-election-zookeeper

```
when start application:
1. create my node to znode of namespace for leader election as ephemeral child node
    with larger number value than existing children have.
2. elect()

when elect:
1. get children in znode of namespace for leader election.
2. sort children by value
3. get first node in children
4. if first node is my node, I am a leader
    else I am a follower
5. watch znode of namespace for leader election on mode - PERSISTENT_RECURSIVE

when watch triggered:
1. if event is NodeDeleted, elect()
    else do nothing.
    
when stop application
1. my node is removed from znode of namespace for leader election as child node because my node was ephemeral node.
```
