
# HDFS

## [Architecture](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-hdfs/HdfsDesign.html)
## [Design Goal](https://hadoop.apache.org/docs/r1.2.1/hdfs_design.html)

1. Hardware Failure
   
   HDFS는 이런 장애를 빠른 시간에 감지하고 대처할 수 있게 설계되어있다.  
   HDFS에 데이터를 저장하면, 복제본도 함께 저장되어 데이터 유실을 방지한다.  
   분산 서버 사이에는 주기적으로 health check 를 통해 빠른 시간에 장애를 감지하고 대처할 수 있게 됩니다.  

2. Streaming Data Access
   
   HDFS는 클라이언트의 요청을 빠르게 처리하는 것보다 동일한 시간 내에 더 많은 데이터를 처리하는 것을 목표로 한다. HDFS는 이것을 위해 Random Access 를 고려하지 않는다.  
   user와 상호작용하는 것보다는 batch 처리에 더 맞게 디자인 되어있다.  
   따라서 은행 서비스, 쇼핑몰과 같은 trasnactional 서비스에서 기존 파일시스템 대신 HDFS 를 쓰는 것은 적합하지 않다.  
   Client 는 HDFS 명령어/API를 통해서 연속된 흐름(streaming)으로 데이터에 접근할 수 있다.  

3. Large Data Sets
   
   HDFS는 하나의 파일이 GB ~ TB 수준의 데이터 크기로 저장될 수 있게 설계되었다.  
   이것으로 높은 데이터 전송 대역폭(bandwidth)를 지원하고 하나의 클러스터에서 수백대의 노드를 구성할 수 있다.  
   하나의 인스턴스에서는 수백만개(tens of million) 이상의 파일을 지원한다.  

4. Simple Coherency Model
   
   HDFS 는 write-once-read-many access model, 한번 저장한 데이터는 수정할 수 없고 읽기만 가능하여 무결성을 지킴 (Immutable) 
   파일의 이동, 삭제, 복사 지원, 최초에 수정이 안되었지만 현재는 EOF 위치에 append 가 가능하다.  
   데이터 접근에 대한 높은 throughput을 가능하게 한다. 이 방식은 MapReduce에서 큰 장점을 발휘한다.

5. Moving Computation is Cheaper than Moving Data
   
   데이터를 이용해서 computing processing 을 한다면, 데이터가 processor와 가까울 수록 효율이 좋다.  
   데이터의 양이 클수록 이 영향이 크다. Network 혼잡을 줄이고 시스템 전체의 throughput 을 높일 수 있다.  
   HDFS는 이것을 위해 computing 자원을 data가 있는 위치로 이동시키는 것을 선택한다.  
   Data를 이동시키는 것보다 비용이 싸고 빠르기 때문이다. HDFS는 이러한 방식을 위한 인터페이스를 제공한다.

6. Portability Across Heterogeneous Hardware and Software Platforms
   
   HDFS는 쉽게 HW/SW 플랫폼을 옮길 수 있도록 디자인 되었다.  
   인텔 칩, AMD칩이 설치된 하드웨어에서 동일한 기능으로 동작한다. CentOS나 Redhat LInux 상관없이 동일하게 동작한다.  
   HDFS의 서버의 코드가 Java로 구현되어있기 때문에 가능하다.  
   대용량 데이터 셋의 플랫폼으로 채택되는 주요한 이유중 하나이다.  

