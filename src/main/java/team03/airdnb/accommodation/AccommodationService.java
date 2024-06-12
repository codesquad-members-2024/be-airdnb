package team03.airdnb.accommodation;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team03.airdnb.accommodation.dto.request.AccommodationSaveDto;
import team03.airdnb.accommodation.dto.request.AccommodationUpdateDto;
import team03.airdnb.accommodation.dto.response.AccommodationListDto;
import team03.airdnb.accommodation.dto.response.AccommodationShowDto;
import team03.airdnb.user.User;
import team03.airdnb.user.UserRepository;

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
        Accommodation accommodation = accommodationRepository.findById(accommodationUpdateDto.getId()).get();
        User host = userRepository.findById(accommodationUpdateDto.getHostId()).get(); // 예외처리 추가할 예정입니다!
        accommodationRepository.save(accommodationUpdateDto.toEntity(host, accommodation.getAverageGrade()));
    }

    public void deleteAccommodation(Long accommodationId) {
        accommodationRepository.deleteById(accommodationId);
    }

    // Review 추가시 Accommodation averageGrade 업데이트
    public void updateAverageGradeOnReviewAdd(Long accommodationId, double addedGrade) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).get();
        double previousAverageGrade = accommodation.getAverageGrade();
        int reviewCount = accommodation.getReviews().size();

        double updatedAverageGrade = (previousAverageGrade * reviewCount + addedGrade) / (reviewCount + 1);
        accommodationRepository.updateAverageGrade(accommodationId, updatedAverageGrade);
    }

    // Review 업데이트시 Accommodation averageGrade 업데이트
    public void updateAverageGradeOnReviewUpdate(Long accommodationId, double gradeDifference) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).get();
        double previousAverageGrade = accommodation.getAverageGrade();
        int reviewCount = accommodation.getReviews().size();

        double updatedAverageGrade = (previousAverageGrade * reviewCount + gradeDifference) / reviewCount;
        accommodationRepository.updateAverageGrade(accommodationId, updatedAverageGrade);
    }

    // Review 삭제시 Accommodation averageGrade 업데이트
    public void updateAverageGradeOnReviewDelete (Long accommodationId, double deletedGrade) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).get();
        double previousAverageGrade = accommodation.getAverageGrade();
        int reviewCount = accommodation.getReviews().size() + 1; // 삭제 전 개수

        double updatedAverageGrade = (previousAverageGrade * reviewCount - deletedGrade) / (reviewCount - 1);
        accommodationRepository.updateAverageGrade(accommodationId, updatedAverageGrade);
    }

    public List<Accommodation> findAccommodationsByFilters(LocalDate checkIn, LocalDate checkOut, Double minPrice, Double maxPrice, Integer capacity) {
        return accommodationRepository.findAccommodationsByFilters(checkIn, checkOut, minPrice, maxPrice, capacity);
    }
}