
# Troubleshooting

## Initialization failed for Block pool

[[denver_almighty] Hadoop Datanode 실행 안됨](https://velog.io/@denver_almighty/ERROR-Hadoop-Datanode-%EC%8B%A4%ED%96%89-%EC%95%88%EB%90%A8Initialization-failed-for-Block-pool-registering-0.-Exiting)

start-dfs.sh를 실행하면 Namenode 만 실행되고 Datanode는 올라오지 않는다.

> 최초 설치가 아니고 사용중에 namenode를 포맷하면서 namenode; location=null? 을 물어볼 때 Y를 선택해서 오류가 난 것이다.
> 
> 사용중에 namenode를 포맷해야한다면
hdfs/datanode, hdfs/namenode 디렉토리를 삭제, 재생성 후 namenode -format을 해야한다

### Solve1

```shell
cat <HADOOP_STORE>/hdfs/namenode/current/VERSION
cat <HADOOP_STORE>/hdfs/datanode/current/VERSION
```

datanode와 namenode에 clusterID가 서로 다르다.  
먼저 프로세스를 죽이고, namenode의 clusterID를 datanode에 복붙하고 start-dfs.sh를 실행하면 정상적으로 올라온다

### Solve2

hdfs/datanode, hdfs/namenode 디렉토리를 지우고 다시 생성한다
