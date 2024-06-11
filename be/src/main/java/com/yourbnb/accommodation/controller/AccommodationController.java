package com.yourbnb.accommodation.controller;

import com.yourbnb.accommodation.model.dto.AccommodationResponse;
import com.yourbnb.accommodation.service.AccommodationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accommodations")
@RequiredArgsConstructor
public class AccommodationController {
    private final AccommodationService accommodationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccommodationResponse> getAccommodations() {
        return accommodationService.getAccommodations();
    }

}
