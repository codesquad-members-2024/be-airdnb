package com.team01.airdnb.accommadation;

import com.team01.airdnb.accommadation.dto.AccommodationRegisterRequest;
import com.team01.airdnb.amenity.Amenity;
import com.team01.airdnb.image.Image;
import com.team01.airdnb.user.User;
import com.team01.airdnb.user.UserService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AccommodationService {
  AccommodationRepository accommodationRepository;
  UserService userService;

  public AccommodationService(AccommodationRepository accommodationRepository, UserService userService){
    this.accommodationRepository = accommodationRepository;
    this.userService = userService;
  }


  /**
   * 숙소를 등록합니다
   */
  public void register(AccommodationRegisterRequest accommodationRegisterRequest) {
    User user = userService.FindUserById(accommodationRegisterRequest.userId());

    Accommodation accommodation = accommodationRegisterRequest.toAccommodationEntity();
    Amenity amenity = accommodationRegisterRequest.toAmenityEntity();
    List<Image> image = accommodationRegisterRequest.toImageEntity();

    accommodation.setUser(user);
    accommodation.setAmenityMapping(amenity);
    accommodation.setImageMapping(image);

    accommodationRepository.save(accommodation);
  }

  /**
   * 숙소의 상세 내용을 확인합니다.
   */

  /**
   * 숙소를 수정합니다.
   */
  public void updateAccommodation() {
  }

  /**
   * 숙소를 삭제합니다.
   */
  public void deleteAccommodation(Long id) {
    accommodationRepository.deleteById(id);
  }

  /**
   * 숙소를 검색합니다.
   */

}
