# [Scheduling and Retries](https://docs.fluentbit.io/manual/administration/scheduling-and-retries)

Fluent Bit uses an exponential backoff and jitter algorithm to determine the waiting time before a retry

## Scheduling

[SERVICE] `scheduler.cap {seconds}`

> maximum retry time in seconds

- 최대 지연시간

[SERVICE] `scheduler.base {backoff}`

> base of exponential backoff

- backoff 설정

```
min(base * (Nth power of 2), cap)
```

| Nth retry | waiting time range           |
|-----------|------------------------------|
| 1         | (3, 6 (3 * (2^1)))           |
| 2         | (3, 12 (3 * (2^2)))          |
| 3         | (3, 24 (3 * (2^3)))          |
| 4         | (3, 30 (3 * (2^4) = ~~48~~)) |

## Retries

[OUTPUT] `retry_limit {N}`

> maximum number of retries allowed (default: 1)

- 최대 재시도 횟수
- no_limits or false
- no_retries


