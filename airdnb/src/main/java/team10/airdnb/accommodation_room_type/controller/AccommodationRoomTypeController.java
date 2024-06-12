package team10.airdnb.accommodation_room_type.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccommodationRoomTypeController {

    @GetMapping("/accommodation-room-type")
    public String getAccommodationRoomTypePage() {
        return "accommodation_room_type/main";
    }
}
