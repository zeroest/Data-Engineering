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

@Data
@Jacksonized
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements HBaseData {
    private String id;
    private String name;
    private String nickname;

    @Override
    @JsonIgnore
    public byte[] getRowKey() {
        return Bytes.toBytes(id);
    }
}