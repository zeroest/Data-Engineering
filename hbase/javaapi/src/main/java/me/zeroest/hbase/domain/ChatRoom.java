package me.zeroest.hbase.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import me.zeroest.hbase.repository.HBaseData;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Set;

@Data
@Jacksonized
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatRoom implements HBaseData {
    private String id;
    private Set<String> participantIds;

    @Override
    @JsonIgnore
    public byte[] getRowKey() {
        return Bytes.toBytes(id);
    }
}