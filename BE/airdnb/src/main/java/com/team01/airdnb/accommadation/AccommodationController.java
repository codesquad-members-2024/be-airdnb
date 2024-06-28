package com.team01.airdnb.accommadation;


import com.team01.airdnb.accommadation.dto.AccommodationDetailResponse;
import com.team01.airdnb.accommadation.dto.AccommodationFilterRequest;
import com.team01.airdnb.accommadation.dto.AccommodationRegisterRequest;
import com.team01.airdnb.accommadation.dto.AccommodationSearchResponse;
import com.team01.airdnb.accommadation.dto.AccommodationUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
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
  @GetMapping("/{accommodationId}")
  public AccommodationDetailResponse showAccommodation(@PathVariable("accommodationId") Long id) {
    return accommodationService.show(id);
  }

  //숙소 수정
  @PutMapping("/accommodations/{id}")
  public void updateAccommodation(@PathVariable Long id,
      @RequestBody AccommodationUpdateRequest accommodationUpdateRequest) {
    accommodationService.update(id, accommodationUpdateRequest);
  }

  //숙소 삭제
  @DeleteMapping("accommodations/{id}")
  public void deleteAccommodation(@PathVariable Long id) {
    accommodationService.delete(id);
  }

  @GetMapping("accommodations/filter")
  public Page<AccommodationSearchResponse> getAccommodationFilter(
      @ModelAttribute AccommodationFilterRequest accommodationFilterRequest,
      Pageable pageable) {
    return accommodationService.searchFilteredAccommodations(accommodationFilterRequest, pageable);
  }
}
