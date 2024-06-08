package codesquad.airdnb.domain.accommodation.dto.response;

import codesquad.airdnb.domain.accommodation.entity.Accommodation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AccoListResponse {

    private List<SimpleAccommodationResponse> accommodationList;

    public static AccoListResponse of(List<Accommodation> accommodations) {
        List<SimpleAccommodationResponse> simpleAccoList = accommodations.stream().map(SimpleAccommodationResponse::of).toList();
        return new AccoListResponse(simpleAccoList);
    }
}
