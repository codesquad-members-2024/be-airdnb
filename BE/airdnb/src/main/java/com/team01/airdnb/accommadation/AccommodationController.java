package com.team01.airdnb.accommadation;


import com.team01.airdnb.accommadation.dto.AccommodationDetailResponse;
import com.team01.airdnb.accommadation.dto.AccommodationFilterRequest;
import com.team01.airdnb.accommadation.dto.AccommodationRegisterRequest;
import com.team01.airdnb.accommadation.dto.AccommodationSearchResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accommodations")
public class AccommodationController {

  AccommodationService accommodationService;

  @Autowired
  public AccommodationController(AccommodationService accommodationService) {
    this.accommodationService = accommodationService;
  }

  //숙소 생성
  @PostMapping
  public void registerAccommodation(
      @RequestBody AccommodationRegisterRequest accommodationRegisterRequest) {
    accommodationService.register(accommodationRegisterRequest);
  }

  //숙소 상세보기
  @GetMapping("/{id}")
  public AccommodationDetailResponse showAccommodation(@PathVariable Long id) {
    return accommodationService.show(id);
  }

  //숙소 필터
  @GetMapping("/filter")
  public List<AccommodationSearchResponse> getFilteredAccommodations(
      @ModelAttribute AccommodationFilterRequest accommodationFilterRequest) {
    return accommodationService.searchFilteredAccommodations(accommodationFilterRequest);
  }
}
