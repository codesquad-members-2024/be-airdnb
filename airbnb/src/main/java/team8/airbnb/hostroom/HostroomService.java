package team8.airbnb.hostroom;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team8.airbnb.jwt.jwtToken.JwtUtil;
import team8.airbnb.map.MapPointResponse;
import team8.airbnb.user.User;
import team8.airbnb.user.UserService;

@Service
public class HostroomService {

  @Autowired
  private HostroomRepository hostroomRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private JwtUtil jwtUtil;

  public Hostroom createHostroom(String token, String hostroomName, int bedNumber,
      int restroomNumber,
      int bathroomNumber, String region, int limitedAdults, int limitedChildren,
      int limitedInfants, int limitedPet, boolean isPet, boolean isInstantbook,
      boolean isSelfcheckin, int price, LocalDateTime checkinDate, LocalDateTime checkoutDate) {

    String username = jwtUtil.getUsernameFromToken(token);

    User host = userService.findByUsername(username);

    Hostroom hostroom = new Hostroom();
    hostroom.setHost(host);
    hostroom.setHostroomName(hostroomName);
    hostroom.setBedNumber(bedNumber);
    hostroom.setRestroomNumber(restroomNumber);
    hostroom.setBathroomNumber(bathroomNumber);
    hostroom.setRegion(region);
    hostroom.setLimitedAdults(limitedAdults);
    hostroom.setLimitedChildren(limitedChildren);
    hostroom.setLimitedInfants(limitedInfants);
    hostroom.setLimitedPet(limitedPet);
    hostroom.setPet(isPet);
    hostroom.setInstantbook(isInstantbook);
    hostroom.setSelfcheckin(isSelfcheckin);
    hostroom.setPrice(price);
    hostroom.setCheckinDate(checkinDate);
    hostroom.setCheckoutDate(checkoutDate);
    hostroom.setReserved(false);
    return hostroomRepository.save(hostroom);
  }

  public Hostroom updateHostroom(Long id, String hostroomName, int bedNumber, int restroomNumber,
      int bathroomNumber, String region, int limitedAdults, int limitedChildren,
      int limitedInfants, int limitedPet, boolean isPet, boolean isInstantbook,
      boolean isSelfcheckin, int price, LocalDateTime checkinDate, LocalDateTime checkoutDate) {
    Hostroom hostroom = hostroomRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Hostroom not found with id: " + id));
    hostroom.setHostroomName(hostroomName);
    hostroom.setBedNumber(bedNumber);
    hostroom.setRestroomNumber(restroomNumber);
    hostroom.setBathroomNumber(bathroomNumber);
    hostroom.setRegion(region);
    hostroom.setLimitedAdults(limitedAdults);
    hostroom.setLimitedChildren(limitedChildren);
    hostroom.setLimitedInfants(limitedInfants);
    hostroom.setLimitedPet(limitedPet);
    hostroom.setPet(isPet);
    hostroom.setInstantbook(isInstantbook);
    hostroom.setSelfcheckin(isSelfcheckin);
    hostroom.setPrice(price);
    hostroom.setCheckinDate(checkinDate);
    hostroom.setCheckoutDate(checkoutDate);
    return hostroomRepository.save(hostroom);
  }

  public void deleteHostroom(Long id) {
    Hostroom hostroom = hostroomRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Hostroom not found with id: " + id));

    if (hostroom.isReserved()) {
      throw new RuntimeException("Cannot delete reserved hostroom with id: " + id);
    }

    hostroomRepository.delete(hostroom);
  }

  public Hostroom getHostroomById(Long id) {
    return hostroomRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Hostroom not found with id: " + id));
  }

  public List<Hostroom> getAllHostrooms() {
    return hostroomRepository.findAll();
  }

  public HostroomResponse convertToDto(Hostroom hostroom) {
    HostroomResponse response = new HostroomResponse();
    response.setId(hostroom.getId());
    response.setHostroomName(hostroom.getHostroomName());
    response.setBedNumber(hostroom.getBedNumber());
    response.setRestroomNumber(hostroom.getRestroomNumber());
    response.setBathroomNumber(hostroom.getBathroomNumber());
    response.setRegion(hostroom.getRegion());
    response.setLimitedAdults(hostroom.getLimitedAdults());
    response.setLimitedChildren(hostroom.getLimitedChildren());
    response.setLimitedInfants(hostroom.getLimitedInfants());
    response.setLimitedPet(hostroom.getLimitedPet());
    response.setPrice(hostroom.getPrice());
    response.setCheckinDate(hostroom.getCheckinDate());
    response.setCheckoutDate(hostroom.getCheckoutDate());
    response.setLatitude(hostroom.getLatitude());
    response.setLongitude(hostroom.getLongitude());
    response.setHostroomItems(hostroom.getHostroomItems());
    response.setSelfcheckin(hostroom.isSelfcheckin());
    response.setReserved(hostroom.isReserved());
    response.setInstantbook(hostroom.isInstantbook());
    response.setPet(hostroom.isPet());
    response.setReviews(hostroom.getReviews());
    return response;
  }

  public List<MapPointResponse> getAllposition() {
    List<Hostroom> hostrooms = getAllHostrooms();
    List<MapPointResponse> MapPointResponses = new ArrayList<>();

    for (Hostroom room : hostrooms) {
      MapPointResponse mapPointResponse = new MapPointResponse(
          room.getLatitude(),
          room.getLongitude(),
          room.getHostroomName()
      );
      MapPointResponses.add(mapPointResponse);
    }

    return MapPointResponses;
  }
}
