# [Monitoring](https://docs.fluentbit.io/manual/administration/monitoring)

INPUT 옵션으로 cpu, mem 설정시 추가적으로 CPU, Memory 메트릭도 함께 수집 가능

```
[SERVICE]
    HTTP_Server  On
    HTTP_Listen  0.0.0.0
    HTTP_PORT    2020

[INPUT]
    Name  cpu
    Alias cpu.${HOSTNAME}

[INPUT]
    Name  mem
    Alias mem.${HOSTNAME}
```

## Health Check

[SERVICE] `health_check off (default)`

- Health check 기능 활성화 여부

[SERVICE] `hc_errors_count 5 (default)`  
[SERVICE] `hc_retry_failure_count 5 (default) `  
[SERVICE] `hc_period 60 (default)`  

```
health status 
    = (HC_Errors_Count > HC_Errors_Count config value) 
    || (HC_Retry_Failure_Count > HC_Retry_Failure_Count config value) 
    IN the HC_Period interval
```

- HTTP status 200 and "ok" in response to healthy status
- HTTP status 500 and "error" in response for unhealthy status

## Grafana Dashboard

| URI                        | Description                                                                    | Data Format           |
|----------------------------|--------------------------------------------------------------------------------|-----------------------|
| /api/v1/metrics/prometheus | Internal metrics per loaded plugin ready to be consumed by a Prometheus Server | Prometheus Text 0.0.4 |

https://github.com/fluent/fluent-bit-docs/tree/8172a24d278539a1420036a9434e9f56d987a040/monitoring

## ETC

[SERVICE] `log_file ${log_file_path}`

- Fluent-bit 로그 파일 설정

https://github.com/fluent/fluent-bit/pull/1890

fluent-bit 자체 로그에 대해서 로그 파일 사이즈, 로테이션 설정이 아직 없음

아래와 같이 수동 로테이션 설정도 고려해보자

```bash
#!/bin/bash

DATE=$(date +%y%m%d_%H%M%S)
BASE_LOG=/path/base/log/flb.log
ROTATION_FILE_PATH=/path/rotation/log/flb-${DATE}.log

if [ -s $BASE_LOG ]; then
  cp ${BASE_LOG} ${ROTATION_FILE_PATH}
  cat /dev/null > ${BASE_LOG}
else
  echo "Empty base log file"
fi
```

```bash
# Example of job definition:
# .---------------- minute (0 - 59)
# |  .------------- hour (0 - 23)
# |  |  .---------- day of month (1 - 31)
# |  |  |  .------- month (1 - 12) OR jan,feb,mar,apr ...
# |  |  |  |  .---- day of week (0 - 6) (Sunday=0 or 7) OR sun,mon,tue,wed,thu,fri,sat
# |  |  |  |  |
# *  *  *  *  * user-name  command to be executed

0 1 * * * zero sh /path/to/flb_log_rotate.sh
```
