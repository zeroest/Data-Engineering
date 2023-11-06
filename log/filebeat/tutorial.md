
# Tutorial

## Install

다운로드 페이지  
https://www.elastic.co/kr/downloads/beats/filebeat

```bash
wget https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-8.10.4-linux-x86_64.tar.gz
tar xzvf filebeat-8.10.4-linux-x86_64.tar.gz
cd filebeat-8.10.4-linux-x86_64

# 입출력 설정 
vim filebeat.yml
```

## One line console

```yaml
# 입력 설정
filebeat.inputs:

- type: log
  id: my-log-id
  enabled: true

  paths:
    - /home/ubuntu/filebeat/*.log

# 출력 설정
output.console:
  enabled: true
  codec.json:
    pretty: true
```

```bash
# 실행 후
./filebeat

# 다른 터미널에서 입력설정한 경로에 로그 파일 생성하여 데이터 입력 및 저장
vim /home/ubuntu/filebeat/test.log
```

실행 결과
```
{
  "@timestamp": "2023-10-23T15:13:50.595Z",
  "@metadata": {
    "beat": "filebeat",
    "type": "_doc",
    "version": "8.10.4"
  },
  "input": {
    "type": "log"
  },
  "ecs": {
    "version": "8.0.0"
  },
  "host": {
    "name": "ip-192-168-0-195"
  },
  "agent": {
    "ephemeral_id": "b03d8b02-88e2-4ece-82bc-94f8886dfc07",
    "id": "a3c47f02-4021-4b1b-a45f-2ac50d000bf4",
    "name": "ip-192-168-0-195",
    "type": "filebeat",
    "version": "8.10.4"
  },
  "log": {
    "offset": 0,
    "file": {
      "path": "/home/ubuntu/filebeat/test.log"
    }
  },
  "message": "test-log-line"
}
```

## Multi line console

```yaml
# 입력 설정
filebeat.inputs:

- type: log
  id: my-log-id
  enabled: true

  paths:
    - /home/ubuntu/filebeat/*.log
  
  # fields는 beat에 추가로 적재할 데이터 내용
  fields:
    index_name: "MULTI_LINE"

  # negate와 match는 함께 사용되며, [true, after]는 같은 패턴이 나올때까지 이전 로그에 붙임.
  # https://www.elastic.co/guide/en/beats/filebeat/current/multiline-examples.html
  multiline.pattern: '^\[\d+:\d+:\d+[/]\d+[.]\d+[:]'
  multiline.negate: true
  multiline.match: after

# 출력 설정
output.console:
  enabled: true
  codec.json:
    pretty: true
```

```bash
# 실행 후
./filebeat

# 다른 터미널에서 입력설정한 경로에 로그 파일 생성하여 데이터 입력 및 저장
vim /home/ubuntu/filebeat/test.log

[10324:12060:1125/150847.213:WARNING:oom_memory_details.cc] test:
line 1
line 2
line 3
line 4

[23012:3023:5123/123111.333:WARNING:oom_memory_details.cc] test:
line 5
line 6
line 7
line 8
```

```
{
  "@timestamp": "2023-10-23T15:32:02.522Z",
  "@metadata": {
    "beat": "filebeat",
    "type": "_doc",
    "version": "8.10.4"
  },
  "host": {
    "name": "ip-192-168-0-195"
  },
  "agent": {
    "type": "filebeat",
    "version": "8.10.4",
    "ephemeral_id": "2891531e-56fe-41d5-b111-b99da7c6fc68",
    "id": "a3c47f02-4021-4b1b-a45f-2ac50d000bf4",
    "name": "ip-192-168-0-195"
  },
  "log": {
    "offset": 0,
    "file": {
      "path": "/home/ubuntu/filebeat/test.log"
    },
    "flags": [
      "multiline"
    ]
  },
  "message": "[10324:12060:1125/150847.213:WARNING:oom_memory_details.cc] test:\nline 1\nline 2\nline 3\nline 4\n",
  "input": {
    "type": "log"
  },
  "fields": {
    "index_name": "MULTI_LINE"
  },
  "ecs": {
    "version": "8.0.0"
  }
}

{
  "@timestamp": "2023-10-23T15:32:02.522Z",
  "@metadata": {
    "beat": "filebeat",
    "type": "_doc",
    "version": "8.10.4"
  },
  "fields": {
    "index_name": "MULTI_LINE"
  },
  "ecs": {
    "version": "8.0.0"
  },
  "host": {
    "name": "ip-192-168-0-195"
  },
  "agent": {
    "ephemeral_id": "2891531e-56fe-41d5-b111-b99da7c6fc68",
    "id": "a3c47f02-4021-4b1b-a45f-2ac50d000bf4",
    "name": "ip-192-168-0-195",
    "type": "filebeat",
    "version": "8.10.4"
  },
  "log": {
    "offset": 95,
    "file": {
      "path": "/home/ubuntu/filebeat/test.log"
    },
    "flags": [
      "multiline"
    ]
  },
  "message": "[23012:3023:5123/123111.333:WARNING:oom_memory_details.cc] test:\nline 5\nline 6\nline 7\nline 8\n",
  "input": {
    "type": "log"
  }
}
```

## One line kafka

```yaml
# 입력 설정
filebeat.inputs:

- type: log
  id: my-log-id
  enabled: true

  paths:
    - /home/ubuntu/filebeat/*.log
  
  # fields는 beat에 추가로 적재할 데이터 내용
  fields:
    index_name: "MULTI_LINE"
    topic_name: "filebeat-test"
 
  encoding: utf-8

# 출력 설정
output.kafka:
  enabled: true
  hosts: [ 'localhost:29092' ]
  topic: '%{[fields.topic_name]}'

  partition.round_robin:
    reachable_only: false
  
  required_acks: 1

  sasl.mechanism: PLAIN
  username: producer
  password: hello

  codec.json:
    pretty: false

logging.level: debug
path.data: /home/ubuntu/filebeat/data
path.logs: /home/ubuntu/filebeat/logs
```
