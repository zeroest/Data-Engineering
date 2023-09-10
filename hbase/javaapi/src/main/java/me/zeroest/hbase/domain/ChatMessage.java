package me.zeroest.hbase.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.hash.Hashing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import me.zeroest.hbase.repository.HBaseData;
import org.apache.hadoop.hbase.util.Bytes;

import java.nio.charset.StandardCharsets;

@Data
@Jacksonized
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatMessage implements HBaseData {
    private String chatRoomId;
    private String senderUserId;
    private String content;
    private long timestamp;

    @Override
    @JsonIgnore
    public byte[] getRowKey() {
        byte[] sha256hex = Hashing.sha256()
            .hashString(senderUserId + content, StandardCharsets.UTF_8)
            .asBytes();
        long reverseTimestamp = Long.MAX_VALUE - timestamp;
        return com.google.common.primitives.Bytes.concat(
            Bytes.toBytes(String.format("%10s", chatRoomId)),
            Bytes.toBytes(reverseTimestamp),
            sha256hex
        );
    }

}