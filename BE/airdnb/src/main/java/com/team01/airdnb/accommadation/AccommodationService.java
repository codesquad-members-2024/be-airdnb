package com.team01.airdnb.accommadation;

import com.team01.airdnb.accommadation.dto.AccommodationDetailResponse;
import com.team01.airdnb.accommadation.dto.AccommodationRegisterRequest;
import com.team01.airdnb.accommadation.dto.AccommodationSearchResponse;
import com.team01.airdnb.accommadation.dto.AccommodationUpdateRequest;
import com.team01.airdnb.amenity.Amenity;
import com.team01.airdnb.amenity.AmenityService;
import com.team01.airdnb.comment.CommentService;
import com.team01.airdnb.image.Image;
import com.team01.airdnb.image.ImageService;
import com.team01.airdnb.user.User;
import com.team01.airdnb.user.UserService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccommodationService {

  AccommodationRepository accommodationRepository;
  UserService userService;
  AmenityService amenityService;
  ImageService imageService;
  CommentService commentService;
  AccommodationFilterRepository accommodationFilterRepository;

  public AccommodationService(AccommodationRepository accommodationRepository, UserService userService,
      AmenityService amenityService, ImageService imageService,
      CommentService commentService, AccommodationFilterRepository accommodationFilterRepository) {
    this.accommodationRepository = accommodationRepository;
    this.userService = userService;
    this.amenityService = amenityService;
    this.imageService = imageService;
    this.commentService = commentService;
    this.accommodationFilterRepository = accommodationFilterRepository;
  }

  /**
   * 숙소를 등록합니다
   */
  public void register(AccommodationRegisterRequest accommodationRegisterRequest) {
    Accommodation accommodation = getAccommodation(
        accommodationRegisterRequest);

    accommodationRepository.save(accommodation);
  }

  /**
   * 숙소의 상세 내용을 확인합니다.
   */
  public AccommodationDetailResponse show(Long id){
    Accommodation accommodation = findById(id);

    return AccommodationDetailResponse.builder()
        .title(accommodation.getTitle())
        .content(accommodation.getContent())
        .price(accommodation.getPrice())
        .discountRate(accommodation.getDiscountRate())
        .address(accommodation.getAddress())
        .latitude(accommodation.getLatitude())
        .longitude(accommodation.getLongitude())
        .commentsNum(accommodation.getCommentsNum())
        .maxAdults(accommodation.getMaxAdults())
        .maxChildren(accommodation.getMaxChildren())
        .maxInfants(accommodation.getMaxInfants())
        .maxPets(accommodation.getMaxPets())
        .amenity(amenityService.findByAccommodationId(id))
        .images(imageService.findByAccommodationId(id))
        .host(userService.getHostResponse(accommodation.getUser()))
        .comment(commentService.showAllComment(id))
        .score(commentService.findAverageScoreByAccommodationId(id))
        .build();
  }

  /**
   * 숙소를 수정합니다.
   */
  public void update(Long id, AccommodationUpdateRequest accommodationUpdateRequest) {
    Accommodation find = findById(id);
    find.update(accommodationUpdateRequest);
  }

  /**
   * 숙소를 삭제합니다.
   */
  public void delete(Long id) {
    findById(id);
    accommodationRepository.deleteById(id);
  }

  private Accommodation findById(Long id) {
    return accommodationRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("해당하는 숙소가 존재하지 않습니다."));
  }

  /**
   * 숙소를 검색합니다.
   */
  public List<AccommodationSearchResponse> search(LocalDate checkIn, LocalDate checkOut,
      double minPrice,
      double maxPrice, int adults, int children, int infants, int pets) {
    List<AccommodationSearchResponse> searchResults = new ArrayList<>();

    return searchResults;
  }

  private Accommodation getAccommodation(
      AccommodationRegisterRequest accommodationRegisterRequest) {
    User user = userService.FindUserById(accommodationRegisterRequest.userId());

    Accommodation accommodation = accommodationRegisterRequest.toAccommodationEntity(user);
    Amenity amenity = accommodationRegisterRequest.toAmenityEntity();
    List<Image> images = accommodationRegisterRequest.toImageEntity();

    accommodation.setAmenityMapping(amenity);
    accommodation.setImageMapping(images);
    return accommodation;
  }

  public Accommodation findAccommodation(Long id) {
    return accommodationRepository.findById(id).orElseThrow();
  }

  /**
   * 필터?
   */
  public List<AccommodationSearchResponse> doFilter(LocalDate checkin, LocalDate checkout) {
    return accommodationFilterRepository.afterCheckin(checkin, checkout);
  }
}
