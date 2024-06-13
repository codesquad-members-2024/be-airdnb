package team10.airdnb.amenity.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AmenityController {

    @GetMapping("/amenity")
    public String showAmenityPage(){
        return "amenity/main";
    }
}
