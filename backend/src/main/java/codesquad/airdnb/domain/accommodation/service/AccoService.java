package codesquad.airdnb.domain.accommodation.service;

import codesquad.airdnb.domain.accommodation.controller.AccoListResponse;
import codesquad.airdnb.domain.accommodation.dto.request.AccoCreateRequest;
import codesquad.airdnb.domain.accommodation.dto.response.AccoContentResponse;
import codesquad.airdnb.domain.accommodation.entity.AccoAmen;
import codesquad.airdnb.domain.accommodation.entity.AccoImage;
import codesquad.airdnb.domain.accommodation.entity.Accommodation;
import codesquad.airdnb.domain.accommodation.entity.Amenity;
import codesquad.airdnb.domain.accommodation.repository.AccoImageRepository;
import codesquad.airdnb.domain.accommodation.repository.AccoRepository;
import codesquad.airdnb.domain.accommodation.repository.AmenityRepository;
import codesquad.airdnb.domain.member.Member;
import codesquad.airdnb.domain.member.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccoService {

    private final AccoRepository accoRepository;

    private final AccoImageRepository accoImageRepository;

    private final AmenityRepository amenityRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public AccoContentResponse create(@Valid AccoCreateRequest request) {
        Member host = memberRepository.findById(request.getHostId()).orElseThrow(NoSuchElementException::new);
        Accommodation accommodation = request.buildAccommodation(host);

        List<Amenity> amenities = amenityRepository.findAllById(request.getAmenities());
        List<AccoAmen> accoAmens = AccoAmen.of(accommodation, amenities);
        accommodation.addAmenities(accoAmens);
        List<AccoImage> accoImages = request.buildAccoImages(accommodation);
        accommodation.addImages(accoImages);

        Accommodation savedAcco = accoRepository.save(accommodation);
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
}
