package com.team01.airdnb.accommadation;


import com.team01.airdnb.accommadation.dto.AccommodationDetailResponse;
import com.team01.airdnb.accommadation.dto.AccommodationRegisterRequest;
import com.team01.airdnb.accommadation.dto.AccommodationSearchResponse;
import com.team01.airdnb.accommadation.dto.AccommodationUpdateRequest;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccommodationController {
  AccommodationService accommodationService;

  @Autowired
  public AccommodationController(AccommodationService accommodationService) {
    this.accommodationService = accommodationService;
  }
  
  //숙소 생성
  @PostMapping("/accommodations")
  public void registerAccommodation(@RequestBody AccommodationRegisterRequest accommodationRegisterRequest){
    accommodationService.register(accommodationRegisterRequest);
  }

  //숙소 검색
  @PostMapping("/accommodations/search")
  public void searchAccommodations() {

  }

  //숙소 상세보기
  @GetMapping("/accommodations/{id}")
  public AccommodationDetailResponse showAccommodation(@PathVariable Long id) {
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
  public List<AccommodationSearchResponse> getAccommodationFilter() {
    LocalDate checkin = LocalDate.of(2024,7,1);
    LocalDate checkout = LocalDate.of(2024,7,3);

    return accommodationService.doFilter(checkin, checkout);
  }
}
