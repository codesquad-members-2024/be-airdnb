package team07.airbnb.domain.accomodation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team07.airbnb.domain.accomodation.dto.AccomodationListResponse;

import java.util.List;

@RequestMapping("/accomodation")
@RestController
@RequiredArgsConstructor
public class AccomodationController {
    private final AccomodationService accomodationService;


    @GetMapping
    public List<AccomodationEntity> findAll() {
        return accomodationService.findAllAccomodations();
    }

    @GetMapping("/location")
    public List<AccomodationListResponse> findNeighbor(
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam double distance) {

        return accomodationService.findNearbyAccomodations(longitude, latitude, distance);
    }
}
