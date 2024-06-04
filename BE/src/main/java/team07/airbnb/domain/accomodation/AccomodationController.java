package team07.airbnb.domain.accomodation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/accomodation")
@RestController
@RequiredArgsConstructor
public class AccomodationController {

    private final AccomodationRepository accomodationRepository;

    @GetMapping
    public List<AccomodationEntity> findAll(){
        return accomodationRepository.findAll();
    }
}
