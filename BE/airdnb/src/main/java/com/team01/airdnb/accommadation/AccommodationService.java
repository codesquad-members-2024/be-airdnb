package com.team01.airdnb.accommadation;

import com.team01.airdnb.accommadation.dto.AccommodationDetailResponse;
import com.team01.airdnb.accommadation.dto.AccommodationFilterRequest;
import com.team01.airdnb.accommadation.dto.AccommodationHostResponse;
import com.team01.airdnb.accommadation.dto.AccommodationRegisterRequest;
import com.team01.airdnb.accommadation.dto.AccommodationSearchResponse;
import com.team01.airdnb.accommadation.dto.AccommodationShowResponse;
import com.team01.airdnb.accommadation.dto.AccommodationUpdateRequest;
import com.team01.airdnb.amenity.AmenityService;
import com.team01.airdnb.comment.CommentService;
import com.team01.airdnb.image.ImageService;
import com.team01.airdnb.user.Role;
import com.team01.airdnb.user.User;
import com.team01.airdnb.user.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class AccommodationService {

  private final AccommodationRepository accommodationRepository;
  private final UserService userService;
  private final AmenityService amenityService;
  private final ImageService imageService;
  private final CommentService commentService;

  @Autowired
  public AccommodationService(AccommodationRepository accommodationRepository,
      UserService userService, AmenityService amenityService, ImageService imageService,
      CommentService commentService) {
    this.accommodationRepository = accommodationRepository;
    this.userService = userService;
    this.amenityService = amenityService;
    this.imageService = imageService;
    this.commentService = commentService;

  }

  /**
   * 숙소를 등록합니다
   */
  public void register(AccommodationRegisterRequest accommodationRegisterRequest) {
    User user = userService.FindUserById(accommodationRegisterRequest.userId());
    Accommodation accommodation = accommodationRegisterRequest.toAccommodationEntity(user);
    if(user.getRole() == Role.USER) {
      user.setRole(Role.HOST);  //숙소를 등록할때 유저의 역할을 유저에서 호스트로 변경 합니다.
      userService.save(user);
    }
    accommodationRepository.save(accommodation);
  }

  /**
   * 숙소의 상세 내용을 확인합니다.
   */
  public AccommodationDetailResponse show(Long id) {
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

  @Transactional(readOnly = true)
  public Accommodation findById(Long id) {
    return accommodationRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("해당하는 숙소가 존재하지 않습니다."));
  }

  /**
   * 숙소 검색을 위해 필터를 적용하여 조건에 맞는 결과를 가져옵니다.
   */
  @Transactional(readOnly = true)
  public Page<AccommodationSearchResponse> searchFilteredAccommodations(
      AccommodationFilterRequest accommodationFilterRequest, Pageable pageable) {
    return accommodationRepository.filterAccommodation(
        accommodationFilterRequest.checkin(),
        accommodationFilterRequest.checkout(),
        accommodationFilterRequest.minPrice(),
        accommodationFilterRequest.maxPrice(),
        accommodationFilterRequest.adultCount(),
        accommodationFilterRequest.childrenCount(),
        accommodationFilterRequest.infantsCount(),
        accommodationFilterRequest.location(),
        accommodationFilterRequest.latitude(),
        accommodationFilterRequest.longitude(),
        pageable);
  }

  @Transactional(readOnly = true)
  public List<AccommodationHostResponse> getAccommodationByUserId(Long userId) {
    List<Accommodation> accommodations = accommodationRepository.findAllByUserId(userId);
    List<AccommodationHostResponse> accommodationHostResponses = new ArrayList<>();

    for (Accommodation accommodation:accommodations){
      accommodationHostResponses.add(new AccommodationHostResponse(accommodation.getId(),accommodation.getTitle()));
    }
    return accommodationHostResponses;
  }

  public List<AccommodationShowResponse> findAllForAdmin() {
    List<Accommodation> accommodations = accommodationRepository.findAll();
    List<AccommodationShowResponse> accommodationShowResponses = new ArrayList<>();

    for (Accommodation accommodation:accommodations){
      accommodationShowResponses.add(AccommodationShowResponse.builder()
          .id(accommodation.getId())
          .title(accommodation.getTitle())
          .content(accommodation.getContent())
          .price(accommodation.getPrice())
          .discountRate(accommodation.getDiscountRate())
          .address(accommodation.getAddress())
          .latitude(accommodation.getLatitude())
          .longitude(accommodation.getLongitude())
          .commentsNum(accommodation.getCommentsNum())
          .user(accommodation.getUser())
          .comments(accommodation.getComments())
          .images(accommodation.getImages())
          .reservations(accommodation.getReservations())
          .amenity(accommodation.getAmenity())
          .build());
    }
    return accommodationShowResponses;
  }
}
