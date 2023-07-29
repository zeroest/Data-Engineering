
# YARN (Yet Another Resource Negotiator)

## [Architecture](https://hadoop.apache.org/docs/stable/hadoop-yarn/hadoop-yarn-site/YARN.html)

YARN은 수십, 수백 개의 노드로 구성된 Hadoop 클러스터에서 실행되는 수 많은 작업 Task들을 관리 및 스케쥴링하고 각각의 작업에 사용될 자원을 적절히 분산하여 관리해주는 기능을 수행.  
여기서 말하는 자원은 Hadoop 클러스터의 모든 장비들의 CPU 코어 수와 Memory 크기를 합친 클러스터의 총 CPU 코어와 총 Memory 크기.

Yarn과 HDFS 자체는 완전히 독립적이다. HDFS는 storage 기능 제공, Yarn은 application을 구동하는 기능 제공

The fundamental idea of YARN is to split up the functionalities of resource management and job scheduling/monitoring into separate daemons.  
The idea is to have a global ResourceManager (RM) and per-application ApplicationMaster (AM).  
An application is either a single job or a DAG of jobs.

![hadoop v2.0](./hadoop20.png)

## 구성

![architecture](architecture.png)

Yarn의 Architecture는 크게 세 가지 역할을 하는 컴포넌트로 구성

### Resource Manager (RM)
- Node Manager의 Primary? Master? 서버 역할
- Yarn이 동작한다는 것은 하나의 Resource Manager가 존재한다는 것 
- 모든 Node의 Resource + Rack Awareness 를 체크하고 있음
- 구성 컴포넌트
  - Scheduler
    - 리소스 할당을 어떻게 할지 지정
    - 어플리케이션의 리소스 요구사항을 기반으로 한 기능만 수행
      - 어플리케이션 상태 모니터링 X
      - 어플리케이션의 장애, restart 관여 X
  - Applications Manager (AM)
    - 제출된 어플리케이션 리스트 관리 
    - Application Master가 뜰 첫번째 컨테이너를 선정
    - 장애에 대해 Application Master container를 재시작하는 역할
  
### Application Master 
- 하나의 Application이 리소스를 할당 받으면서 부터 생성
- Job에서 가장 먼저 시작하는 컨테이너, 즉 Job의 라이프사이클을 관리
- RM과 지속적 통신하면서 리소스 요청 및 할당 받
- Node Manager와 지속적 통신하면서 컨테이너에 관련된 정보 교환
  
### Node Manager
- 데이터 노드에서 노드 매너저 역할 
- RM 입장에서 Node Manager는 Worker Node 로서의 역할을 수행
- HeartBeat와 함께 해당 노드에서 점유하고 있는, 가용할 수 있는 리소스 RM에게 전달 

## 작업 요청 흐름

![workflow1](workflow1.png)
![workflow2](workflow2.png)

[Refer - Hadjo yarn hello world](https://hadjo.lazyweaver.com/docs/yarn_hello_world_part2.html)

1. 클라이언트가 어플리케이션 실행 요청
  - 어플리케이션은 Yarn API를 구현한 프로그램
  - RM은 실행 요청이 유효할 경우 클라이언트에 새로운 Application ID를 할당
2. RM이 NodeManager에게 Application Master 실행 요청
  - NodeManager는 RM의 요청을 받고 컨테이너에서 Application Master를 실행
  - 컨테이너는 새로움 JVM을 생성해 Application Master를 실행
3. Application Master는 RM에게 어플리케이션을 실행하기 위한 리소스 요청
  - 이때 고려하는 리소스는 필요한 호스트, 랙정보, 메모리, CPU, Network정보, 컨테이너 개수 등으로 구성
  - RM은 전체 클러스터 리소스 상태를 확인한 후 Application Master에게 NodeManager 목록을 전달
5. Application Master는 할당받은 NodeManager들에게 컨테이너 실행을 요청
6. NodeManager들은 컨테이너에 새로운 JVM을 생성한 후, 해당 어플리케이션을 실행
  - 어플리케이션이 종료되면 해당 Application Master가 종료된다
  - 마지막으로 RM은 종료된 Application Master에게 할당햇던 리소스를 해제한다
