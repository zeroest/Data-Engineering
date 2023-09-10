#!/bin/bash

# build 를 수행한 뒤, jar 를 EMR primary node 로 scp 를 통해 복사한다.
scp -i $your_key $your_jar_path hadoop@$your_emr_primary_ec2_public_dns:~/example/jars/.
scp $your_jar_path bd01:~/yarn/

# EMR Primary node 에 접속한 후 다음 명령어를 수행한다.
# -jar 부터는 코드에서 해당 파라미터를 받을 수 있도록 구현한 로직때문에 넣는 옵션이다.
# yarn jar hadoop-yarn-app-1.0-SNAPSHOT.jar de.example.hadoop.yarn.MyClient -jar hadoop-yarn-app-1.0-SNAPSHOT.jar
yarn jar $your_jar $yarn_client_mainclass $args
~/hadoop/bin/yarn jar yarn-example-0.0.0.jar me.zeroest.yarn.example.MyClient -jar yarn-example-0.0.0.jar  -num_containers=2

# log 확인
#
# 1. Yarn 에 작업을 제출한 뒤 남는 로그는 YarnClient 입장에서의 로그이다.
# 2. Yarn UI 에서 볼 수있는 Logs는 Yarn Client 의 로그에, 편의로 추가 로그를 볼 수 있다.
#    - Application Master 를 시작하는 container 로그를 볼 수 있다.
#    - Application Master 로그의 일부를 볼 수 있다.
# 3. 각 클라이언트가 수행한 정확한 로그는 데이터노드에서 확인할 수 있다.
#
# 편의를 위해 핵심 내용은 Yarn Client, Application Master 로그에서 모두 확인할 수 있도록 하는 것이 좋다.
# ApplicationMaster 까지의 로그는 webui 에서 확인이 가능하다.
#
# EMR 기본 설치의 경우
# - ApplicationMaster의 full log는 HDFS의 `/var/log/hadoop-yarn/apps/hadoop/logs-tfile/` 경로에서 applicationid 별로 확인이 가능하다.
# - 각 노드의 `/var/log/hadoop-yarn/containers/` 에서 applicationid 별로 디렉토리가 생성된다. 그 하위에 container 별 로그 디렉토리가 생긴다. 이 경로가 *`ApplicationConstants*.***LOG_DIR_EXPANSION_VAR***` 이다. 이 하위에 자세한 로그가 파일별로 남는다.
