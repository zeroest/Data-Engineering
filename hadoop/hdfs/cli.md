
# [HDFS CLI](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/FileSystemShell.html)

## [mkdir](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/FileSystemShell.html#mkdir)

```shell
hdfs dfs -mkdir /tmp
```

## [put](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/FileSystemShell.html#put)

```shell
hdfs dfs -put abc.txt /tmp/abc.txt
```

## [ls](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/FileSystemShell.html#ls)

- -d : Directories are listed as plain files.

```shell
hdfs dfs -ls /tmp
# Found 1 items
# -rw-r--r--   2 ubuntu supergroup          8 2023-06-13 15:27 /tmp/abc.txt
```

## [cat](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/FileSystemShell.html#cat)

```shell
hdfs dfs -cat /tmp/abc.txt
# a b c
```

## [stat](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/FileSystemShell.html#stat)

Print statistics about the file/directory at <path> in the specified format

Format accepts permissions in octal (%a) and symbolic (%A), filesize in bytes (%b), type (%F), group name of owner (%g), name (%n), block size (%o), replication (%r), user name of owner(%u), access date(%x, %X), and modification date (%y, %Y). %x and %y show UTC date as “yyyy-MM-dd HH:mm:ss”, and %X and %Y show milliseconds since January 1, 1970 UTC. If the format is not specified, %y is used by default.

- %b - 파일크기
- %o - 파일 블록 크기
- %r - 복제 수
- %u - 소유자명
- %n - 파일명

Example:
- hadoop fs -stat "type:%F perm:%a %u:%g size:%b mtime:%y atime:%x name:%n" /file

```shell
hadoop fs -stat "type:%F perm:%a %u:%g size:%b mtime:%y atime:%x name:%n" /tmp/abcd.txt
# type:regular file perm:644 ubuntu:supergroup size:8 mtime:2023-06-13 15:27:06 atime:2023-06-13 15:27:04 name:abcd.txt
hdfs dfs -stat '%b %o %r %u %n' /tmp/abc.txt
# 8 134217728 2 ubuntu abc.txt
```

## [mv](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/FileSystemShell.html#mv)

```shell
hdfs dfs -mv /tmp/abc.txt /tmp/abcd.txt
```

## [get](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/FileSystemShell.html#get)

```shell
hdfs dfs -get /tmp/abc.txt
```

## [rm](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/FileSystemShell.html#rm)

cf) rmdir, rmr

If trash is enabled, file system instead moves the deleted file to a trash directory (given by FileSystem#getTrashRoot).

Currently, the trash feature is disabled by default. User can enable trash by setting a value greater than zero for parameter fs.trash.interval (in core-site.xml).

```shell
hdfs dfs -rm /tmp/abc.txt
```

## fsck

'file system check'의 약자로 리눅스 파일시스템에서 파일을 체크하고 수리하는 명령

```shell
hdfs fsck /

: <<'END'
Connecting to namenode via http://bd1:9870/fsck?ugi=ubuntu&path=%2F
FSCK started by ubuntu (auth:SIMPLE) from /192.168.0.180 for path / at Tue Jun 13 15:44:43 UTC 2023


/tmp/hadoop-yarn/staging/ubuntu/.staging/job_1686588634769_0001/job.jar: MISSING 1 blocks of total size 281338 B.
/tmp/hadoop-yarn/staging/ubuntu/.staging/job_1686588634769_0001/job.split: MISSING 1 blocks of total size 108 B.
/tmp/hadoop-yarn/staging/ubuntu/.staging/job_1686588634769_0001/job.splitmetainfo: MISSING 1 blocks of total size 24 B.
/tmp/hadoop-yarn/staging/ubuntu/.staging/job_1686588634769_0001/job.xml: MISSING 1 blocks of total size 235750 B.
/user/hadoop/pg10.txt: MISSING 1 blocks of total size 4456300 B.
Status: CORRUPT
# 데이터 노드 개수
 Number of data-nodes:	2
# 데이터 노드가 존재하는 랙의 개수 
 Number of racks:		1
 Total dirs:			9
 Total symlinks:		0

Replicated Blocks:
# # 현재 사용중인 byte (hadoop fs -du -s / 명령어와 동일한 값)
 Total size:	4973528 B
 Total files:	7
 Total blocks (validated):	6 (avg. block size 828921 B)
  ********************************
  UNDER MIN REPL'D BLOCKS:	5 (83.333336 %)
  MINIMAL BLOCK REPLICATION:	1
  CORRUPT FILES:	5
  MISSING BLOCKS:	5
  MISSING SIZE:		4973520 B
  ********************************
# 최소 개수로 복제된 블록
 Minimally replicated blocks:	1 (16.666666 %)
# 기본 설정값 보다 더 복제된 블록의 수, 기본 복제값을 넘어서는 블록은 자동으로 삭제됨
 Over-replicated blocks:	0 (0.0 %)
기본 복제값에 맞지 않게 복제된 플록의 수
 Under-replicated blocks:	0 (0.0 %)
# block replica placement policy 규정에 위반된 블록(같은 랙에 복제 되면 규정 위반이기 때문에 다른 랙에 복제 되어야 함)
 Mis-replicated blocks:		0 (0.0 %)
# hdfs-site.xml에 위치한 dfs.replication의 설정값
 Default replication factor:	2
# hdfs상 파일들의 복제값의 평균
 Average block replication:	0.33333334
 Missing blocks:		5
# 오류가 발생한 블럭, 정상적인 블럭이 있다면 네임노드가 정상블럭을 복제하여 복구
 Corrupt blocks:		0
# 복제 블럭이 존재하지 않는 블럭
 Missing replicas:		0 (0.0 %)
 Blocks queued for replication:	0

Erasure Coded Block Groups:
 Total size:	0 B
 Total files:	0
 Total block groups (validated):	0
 Minimally erasure-coded block groups:	0
 Over-erasure-coded block groups:	0
 Under-erasure-coded block groups:	0
 Unsatisfactory placement block groups:	0
 Average block group size:	0.0
 Missing block groups:		0
 Corrupt block groups:		0
 Missing internal blocks:	0
 Blocks queued for replication:	0
FSCK ended at Tue Jun 13 15:44:43 UTC 2023 in 5 milliseconds


The filesystem under path '/' is CORRUPT
END
```

## dfsadmin -report

하둡 파일시스템의 기본 정보 및 통계를 보여줌

```shell
hdfs dfsadmin -report

: << 'END'
Configured Capacity: 41235644416 (38.40 GB)
Present Capacity: 28461568019 (26.51 GB)
DFS Remaining: 28461498368 (26.51 GB)
DFS Used: 69651 (68.02 KB)
DFS Used%: 0.00%
Replicated Blocks:
	Under replicated blocks: 0
	Blocks with corrupt replicas: 0
	Missing blocks: 0
	Missing blocks (with replication factor 1): 0
	Low redundancy blocks with highest priority to recover: 0
	Pending deletion blocks: 0
Erasure Coded Block Groups:
	Low redundancy block groups: 0
	Block groups with corrupt internal blocks: 0
	Missing block groups: 0
	Low redundancy blocks with highest priority to recover: 0
	Pending deletion blocks: 0

-------------------------------------------------
Live datanodes (2):

Name: 192.168.0.157:9866 (bd3)
Hostname: bd3
Decommission Status : Normal
Configured Capacity: 20617822208 (19.20 GB)
DFS Used: 45056 (44 KB)
Non DFS Used: 6369361920 (5.93 GB)
DFS Remaining: 14231638016 (13.25 GB)
DFS Used%: 0.00%
DFS Remaining%: 69.03%
Configured Cache Capacity: 0 (0 B)
Cache Used: 0 (0 B)
Cache Remaining: 0 (0 B)
Cache Used%: 100.00%
Cache Remaining%: 0.00%
Xceivers: 0
Last contact: Tue Jun 13 16:33:19 UTC 2023
Last Block Report: Tue Jun 13 16:22:46 UTC 2023
Num of Blocks: 1


Name: 192.168.0.218:9866 (bd2)
Hostname: bd2
Decommission Status : Normal
Configured Capacity: 20617822208 (19.20 GB)
DFS Used: 24595 (24.02 KB)
Non DFS Used: 6371160045 (5.93 GB)
DFS Remaining: 14229860352 (13.25 GB)
DFS Used%: 0.00%
DFS Remaining%: 69.02%
Configured Cache Capacity: 0 (0 B)
Cache Used: 0 (0 B)
Cache Remaining: 0 (0 B)
Cache Used%: 100.00%
Cache Remaining%: 0.00%
Xceivers: 0
Last contact: Tue Jun 13 16:33:18 UTC 2023
Last Block Report: Tue Jun 13 16:23:48 UTC 2023
Num of Blocks: 1

END
```

##[safe mode](https://wikidocs.net/25321)

HDFS의 세이프모드(safemode)는 데이터 노드를 수정할 수 없는 상태 입니다.  
세이프 모드가 되면 데이터는 읽기 전용 상태가 되고, 데이터 추가와 수정이 불가능 하며 데이터 복제도 일어나지 않습니다.  
관리자가 서버 운영 정비를 위해 세이프 모드를 설정 할 수도 있고, 네임노드에 문제가 생겨서 정상적인 동작을 할 수 없을 때 자동으로 세이프 모드로 전환됩니다.

```shell
# 상태 확인 
hdfs dfsadmin -safemode get
# 모드 해제
hdfs dfsadmin -safemode leave
# 모드 진입
hdfs dfsadmin -safemode enter

# 손상된 파일을 강제로 삭제한다
hdfs fsck / -delete
# 손상된 파일을 /lost + found 디렉터리로 옮긴다
hdfs fsck / -move
```

