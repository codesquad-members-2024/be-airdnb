package team03.airdnb.authentication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResultDto {

    private final String token;
    private final String message;
}
