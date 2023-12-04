
# [Buffer & Storage](https://docs.fluentbit.io/manual/administration/buffering-and-storage)

## Chunks

> A Chunk size usually is around 2MB

청크 단위 2MB

> the default is that all chunks are created only in memory.

기본 memory 로 동작

## [Memory Backpressure Config](https://docs.fluentbit.io/manual/administration/backpressure)

> Please note that Mem_Buf_Limit only applies when storage.type is set to the default value of memory.

**! storage.type memory(default) 설정시에만 적용됨**

[INPUT] `mem_buf_limit {unit_size}`

> The goal of `mem_buf_limit` is memory control and survival of the service.

- 옵션 설정시 해당 사이즈 만큼 메모리 제한  
- 설정 메모리 이상 될시 paused, 처리 되어 메모리 여유생길시 resume  
- 현재 offset 저장하여 resume시 offset 부터 처리

```
[input] tail.1 paused (mem buf overlimit)
[input] tail.1 resume (mem buf overlimit)
```

## Filesystem Buffering

[INPUT] `storage.type filesystem`

> Upon Chunk creation, the engine stores the content in memory and also maps a copy on disk  
> 
> called to be `up` which means "the chunk content is up in memory".

- 메모리, 디스크 함께 저장

> By default, the engine allows us to have 128 Chunks up in memory in total
> 
> Remember, chunks are never much larger than 2 MB, thus, with the default storage.max_chunks_up value of 128, each input is limited to roughly 256 MB of memory.

[SERVICE] `storage.max_chunks_up 128(default)`

- max_chunks_up(default: 128) 까지 메모리에 up 상태로 서비스
- 128(max_chunks_up) * 2MB(chunk_size) = 256MB 메모리 제한 (default)

> The active Chunks that are up are ready for delivery and the ones that are still receiving records. Any other remaining Chunk is in a down state, which means that it is only in the filesystem and won't be up in memory unless it is ready to be delivered.
> 
> when reaching the storage.max_chunks_up threshold, instead of the plugin being paused, all new data will go to Chunks that are down in the filesystem

- 그 이상될 경우 paused 되지 않고 나머지 chunk는 down 상태로 파일 시스템에 저장
- 새 데이터 chunk에만 추가 가능, 따라서 메모리에 잠시 올린뒤 데이터 수집 후 down

[INPUT] `storage.pause_on_chunks_overlimit off(default)`

> the input plugin will be paused upon exceeding storage.max_chunks_up
> 
> Thus, with this option, `storage.max_chunks_up` becomes a hard limit for the input

- `storage.max_chunks_up` 도달시 paused
- tail 의 경우 offset 저장하여 해당 시점 이후부터 resume

```
[input] tail.1 paused (storage buf overlimit)
[input] tail.1 resume (storage buf overlimit)
``` 

## Limiting Filesystem space for Chunks

[OUTPUT] `storage.total_limit_size {unit_size}`

파일 시스템의 청크 수 제한
limit 도달시 논리 대기열에서 가장 오래된 청크 삭제

storage.backlog.mem_limit (default 5M)

> Backlog chunks are filesystem chunks that were left over from a previous Fluent Bit run
> 
> If the up chunks currently consume less memory than the limit, it will bring the backlog chunks up into memory so they can be sent by outputs.

백로그 청크(이전 실행시 파일시스템에 남은)를 다시 up 상태로 메모리에 올리기 위한 기준
현재 up 상태의 소비되는 메모리 용량이 해당 설정 메모리보다 적을때 전환

cf.
- [Unit Sizes](https://docs.fluentbit.io/manual/administration/configuring-fluent-bit/unit-sizes)
