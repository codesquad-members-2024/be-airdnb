package team10.airdnb.accommodation_type.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AccommodationTypeController {

    @GetMapping("/accommodation-type")
    public String getAccommodationTypePage() {
        return "accommodation_type/main";
    }

}
