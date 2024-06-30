package codesquad.team05.web.accommodation.dto.request;

import codesquad.team05.domain.servicecharge.ServiceType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ServiceChargeDto {

    @NotNull
    private final ServiceType serviceType;
    @NotEmpty
    private final Integer fee;
    @NotEmpty
    private final Integer intervalDays;
}
