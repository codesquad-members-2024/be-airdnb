package team10.airdnb.accommodation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/accommodation")
public class AccommodationController {

    @GetMapping("/create")
    public String showAccommodationPage() {
        return "accommodation/createForm";
    }
}
