
# [Spark](https://spark.apache.org/)

[Getting Started with Apache Spark](https://towardsdatascience.com/getting-started-with-apache-spark-ad9d59e71f6f)

데이터 센터나 클라우드에서 대규모 분산 데이터 처리를 하기 위해 설계된 통합형 엔진

1. 속도
- 디스크 I/O를 사용하는 MapReduce와 달리 중간 결과를 메모리에 유지, 빠른 속도로 같은 작업 수행
- 질의 연산을 방향성 비순환 그래프(DAG: Directed acyclic graph)로 구성
- 클러스터의 워커 노드 위에서 병렬 실행될 수 있도록 함
- 스파크 1.5버전과 함께 공개된 Tungsten 프로젝트로 성능 개선

2. 편리성
- 높은 추상화로 클라이언트가 단일 pc, 분산환경에서 실행되는지 구별할 필요 없음
- Dataframe, Dataset 고수중 데이터 추상화 계층 아래 RDD(Resilient distributed dataset)라 불리는 단순한 자료구조를 구축해 단순성을 실현
- 연산의 종류로 Transformation, Action의 집합과 단순한 프로그래밍 모델 제공
- 여러 프로그래밍 언어 제공 (Scala, Java, Python, Kotlin, R 등)

3. 모듈성
- 스파크에 내장된 다양한 컴포넌트(SparkSQL, Structured Streaming, MLlib 등) 제공, 다양한 타입의 워크로드에 적용 가능
- 특정 워크로드를 처리하기 위해 하나의 통합된 처리 엔진을 가짐
    - 하둡 맵리듀스의 경우, Apache Hive(SQL질의), Storm(스트리밍), Mahout(머신러링) 등 다른 시스템과의 연동이 필요

4. 확장성
- 저장과 연산을 모두 포함하는 하둡과 달리, 스파크는 빠른 병렬 연산에 초점
- 다양한 데이터 소스(Hadoop, cassandra, HBase, MongoDB, Hive, S3 등) 로부터 데이터 읽기 가능
- 다양한 파일 포맷(txt, csv, parquet, roc, hdfs 등) 호환 가능 
