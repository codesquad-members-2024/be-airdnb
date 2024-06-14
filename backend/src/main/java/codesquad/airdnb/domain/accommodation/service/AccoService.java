package codesquad.airdnb.domain.accommodation.service;

import codesquad.airdnb.domain.accommodation.dto.request.AccoReservationRequest;
import codesquad.airdnb.domain.accommodation.dto.response.FilteredAccosResponse;
import codesquad.airdnb.domain.accommodation.entity.*;
import codesquad.airdnb.domain.accommodation.repository.*;
import codesquad.airdnb.domain.accommodation.util.GeometryHelper;
import codesquad.airdnb.domain.accommodation.dto.request.AccoCreateRequest;
import codesquad.airdnb.domain.accommodation.dto.response.AccoContentResponse;
import codesquad.airdnb.domain.accommodation.dto.response.AccoListResponse;
import codesquad.airdnb.domain.member.Member;
import codesquad.airdnb.domain.member.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccoService {

    private final AccoRepository accoRepository;

    private final AccoImageRepository accoImageRepository;

    private final AmenityRepository amenityRepository;

    private final MemberRepository memberRepository;

    private final AccoProductRepository accoProductRepository;

    private final ReservationRepository reservationRepositry;

    private final ReservationProductRepository reservationProductRepository;

    @Transactional
    public AccoContentResponse create(@Valid AccoCreateRequest request) {
        Member host = memberRepository.findById(request.hostId()).orElseThrow(NoSuchElementException::new);
        Accommodation accommodation = request.buildAccommodation(host);

        List<Amenity> amenities = amenityRepository.findAllById(request.amenities());
        List<AccoAmen> accoAmens = AccoAmen.of(accommodation, amenities);
        accommodation.addAmenities(accoAmens);

        List<AccoImage> accoImages = request.buildAccoImages(accommodation);
        accommodation.addImages(accoImages);

        Accommodation savedAcco = accoRepository.save(accommodation);
        createYearlyProduct(savedAcco.getId());
        return AccoContentResponse.of(savedAcco);
    }

    public AccoContentResponse get(Long accoId) {
        Accommodation accommodation = accoRepository.findById(accoId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID를 갖는 숙소가 없습니다."));

        return AccoContentResponse.of(accommodation);
    }

    public AccoListResponse getList(Long hostId) {
        List<Accommodation> accommodations = accoRepository.findAllByHostId(hostId);

        return AccoListResponse.of(accommodations);
    }

    private void createYearlyProduct(Long accoId) {
        Accommodation accommodation = accoRepository.findById(accoId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID를 갖는 숙소가 없습니다."));

        accoProductRepository.createYearlyProduct(accoId, accommodation.getBasePricePerNight());
    }

    public List<FilteredAccosResponse> getFilteredList(Integer guestCount, Integer infantCount,
                                                       LocalDate checkInDate, LocalDate checkOutDate,
                                                       Double longitude, Double latitude) {
        GeometryHelper geometryHelper = new GeometryHelper();
        Point point = geometryHelper.createPoint(longitude, latitude);
        List<Long> ids = accoRepository.findIdsByCoordAndHumanCount(point, guestCount, infantCount);

        return accoProductRepository.getAccoListFilteredBy(ids, checkInDate, checkOutDate);
    }

    // ****************** Scheduled ******************
    @Scheduled(cron = "0 1 3 * * ?") // 매일 3시 1분 실행
    // 1년 뒤의 예약을 생성하는 프로시저 실행
    public void createNextProductForAllAcco() {
        accoProductRepository.createNextProductForAllAcco();
    }
    // **************** Scheduled_END ****************

    @Transactional
    public void reservation(@Valid AccoReservationRequest request, Long memberId) {
        AccoProducts accoProducts = new AccoProducts(accoProductRepository.findAllById(request.products()));
        accoProducts.validate(request);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID를 갖는 멤버가 존재하지 않습니다."));

        Reservation reservation = request.toReservation(member, accoProducts.getTotalRoomCharge());
        reservationRepositry.save(reservation);

        List<ReservationProduct> reservationProducts = accoProducts.toReservationProducts(reservation);
        reservationProductRepository.saveAll(reservationProducts);

        accoProducts.updateRoomToBooked();
    }
}
