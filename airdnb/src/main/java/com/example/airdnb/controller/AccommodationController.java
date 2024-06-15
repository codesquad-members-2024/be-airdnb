package com.example.airdnb.controller;

import com.example.airdnb.domain.accommodation.Accommodation;
import com.example.airdnb.dto.accommodation.AccommodationCreationRequest;
import com.example.airdnb.dto.accommodation.AccommodationResponse;
import com.example.airdnb.service.AccommodationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accommodations")
@Slf4j
public class AccommodationController {

    private final AccommodationService accommodationService;

    @GetMapping
    public List<AccommodationResponse> getAccommodationList() {
        return accommodationService.getAccommodationList();
    }

    @PostMapping
    public ResponseEntity<String> createNewAccommodation(@RequestBody AccommodationCreationRequest request) {
        log.info(request.toString());
        Accommodation newAccommodation = accommodationService.createNewAccommodation(request);
        return ResponseEntity.ok(String.format("SUCCESS!! NAME : %s", newAccommodation.getName()));
    }
}
