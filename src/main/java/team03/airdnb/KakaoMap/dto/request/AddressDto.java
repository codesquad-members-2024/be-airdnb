package team03.airdnb.kakaoMap.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDto {
    private Document[] documents;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Document {
        private String addressName;
        private String x;
        private String y;
    }
}
