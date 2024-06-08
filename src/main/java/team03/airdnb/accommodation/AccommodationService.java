package team03.airdnb.accommodation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team03.airdnb.accommodation.dto.request.AccommodationSaveDto;
import team03.airdnb.accommodation.dto.request.AccommodationUpdateDto;
import team03.airdnb.accommodation.dto.response.AccommodationListDto;
import team03.airdnb.accommodation.dto.response.AccommodationShowDto;
import team03.airdnb.user.User;
import team03.airdnb.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final UserRepository userRepository;

    public Accommodation createAccommodation(AccommodationSaveDto accommodationSaveDto) {
        User host = userRepository.findById(accommodationSaveDto.getHostId()).get(); // 예외처리 추가할 예정입니다!
        return accommodationRepository.save(accommodationSaveDto.toEntity(host));
    }

    public List<AccommodationListDto> showAccommodationList() {
        List<Accommodation> accommodationList = accommodationRepository.findAll();

        return accommodationList.stream()
                .map(accommodation -> AccommodationListDto.of(accommodation, accommodation.getAccommodationAmenities()))
                .collect(Collectors.toList());
    }

    public AccommodationShowDto showAccommodationDetail(Long accommodationId) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).get(); // 예외처리 추가할 예정입니다!

        // 수수료(fee) 관련해서는 아직 구현하지 못해 임의의 숫자를 넣었습니다.
        return AccommodationShowDto.of(accommodation, 10000L, accommodation.getAccommodationAmenities());
    }

    public void updateAccommodation(AccommodationUpdateDto accommodationUpdateDto) {
        User host = userRepository.findById(accommodationUpdateDto.getHostId()).get(); // 예외처리 추가할 예정입니다!
        accommodationRepository.save(accommodationUpdateDto.toEntity(host));
    }

    public void deleteAccommodation(Long accommodationId) {
        accommodationRepository.deleteById(accommodationId);
    }
}