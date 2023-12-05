
# [Multiline Parsing](https://docs.fluentbit.io/manual/administration/configuring-fluent-bit/multiline-parsing)

## Rules Definition

```
# rules   |   state name   | regex pattern                         | next state
# --------|----------------|---------------------------------------------------
rule         "start_state"   "/([a-zA-Z]+ \d+ \d+\:\d+\:\d+)(.*)/"   "cont"
rule         "cont"          "/^\s+at.*/"                            "cont"
```

Rule

1. 첫 룰의 state name 은 항상 start_state 일 것
2. 첫 룰의 정규식이 메세지의 첫 번째 줄과 일치할 것
3. 다음 룰은 연속된 패턴으로 설정할 것
4. 기타 - 모든 필드 값은 "" 더블 쿼트로 작성되어야 함

example  
https://github.com/fluent/fluent-bit/tree/master/documentation/examples/multiline/regex-001
