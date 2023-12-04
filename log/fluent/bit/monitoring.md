
# [Monitoring](https://docs.fluentbit.io/manual/administration/monitoring)


## Grafana Dashboard

https://github.com/fluent/fluent-bit-docs/tree/8172a24d278539a1420036a9434e9f56d987a040/monitoring

## ETC

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
