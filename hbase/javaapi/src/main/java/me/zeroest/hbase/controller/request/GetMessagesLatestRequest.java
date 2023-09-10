package me.zeroest.hbase.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Data
@Jacksonized
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetMessagesLatestRequest {
    private String chatRoomId;
    private int limit;
}
