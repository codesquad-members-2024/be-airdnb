package codesquad.airdnb.domain.accommodation.controller;

import codesquad.airdnb.domain.accommodation.entity.Accommodation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AccoListResponse {

    private List<SimpleAccommodation> accommodationList;

    public static AccoListResponse of(List<Accommodation> accommodations) {
        List<SimpleAccommodation> simpleAccoList = accommodations.stream().map(SimpleAccommodation::of).toList();
        return new AccoListResponse(simpleAccoList);
    }
}
