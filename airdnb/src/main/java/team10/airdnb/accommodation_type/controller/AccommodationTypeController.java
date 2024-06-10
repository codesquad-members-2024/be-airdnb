package team10.airdnb.accommodation_type.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccommodationTypeController {

    @GetMapping("/accommodation-type")
    public String getAccommodationTypePage() {
        return "accommodation_type/main";
    }

}
