package codesquad.airdnb.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank
        @Size(min = 4, max = 50)
        String accountName,

        @NotBlank
        @Size(min = 4, max = 50)
        String password
) {
}
