package com.team01.airdnb.host;

import com.team01.airdnb.accommadation.AccommodationService;
import com.team01.airdnb.accommadation.dto.AccommodationUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/host/accommodations")
public class AccommodationHostController {
  AccommodationService accommodationService;

  @Autowired
  public AccommodationHostController(AccommodationService accommodationService) {
    this.accommodationService = accommodationService;
  }

  //숙소 수정
  @PutMapping("/{accommodationId}")
  public void updateAccommodation(@PathVariable("accommodationId") Long id,
      @RequestBody AccommodationUpdateRequest accommodationUpdateRequest) {
    accommodationService.update(id, accommodationUpdateRequest);
  }

  //숙소 삭제
  @DeleteMapping("/{accommodationId}")
  public void deleteAccommodation(@PathVariable("accommodationId") Long id) {
    accommodationService.delete(id);
  }
}
