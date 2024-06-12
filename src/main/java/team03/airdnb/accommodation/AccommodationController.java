package team03.airdnb.accommodation;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import team03.airdnb.accommodation.dto.request.AccommodationSaveDto;
import team03.airdnb.accommodation.dto.request.AccommodationUpdateDto;
import team03.airdnb.accommodation.dto.response.AccommodationListDto;
import team03.airdnb.accommodation.dto.response.AccommodationShowDto;

@RequestMapping("/accommodations")
@RestController
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    @PostMapping
    public ResponseEntity<Void> createAccommodation(@RequestBody AccommodationSaveDto accommodationSaveDto,
                                                    UriComponentsBuilder uriComponentsBuilder) {
        Accommodation createdAccommodation = accommodationService.createAccommodation(accommodationSaveDto);
        URI location = uriComponentsBuilder.path("/accommodations/{id}")
                .buildAndExpand(createdAccommodation.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @GetMapping
    public ResponseEntity<List<AccommodationListDto>> showAccommodationList() {
        List<AccommodationListDto> accommodationListDtos = accommodationService.showAccommodationList();

        return ResponseEntity.ok(accommodationListDtos);
    }

    @GetMapping("/{accommodationId}")
    public ResponseEntity<AccommodationShowDto> showAccommodationDetail(@PathVariable Long accommodationId) {
        AccommodationShowDto accommodationShowDto = accommodationService.showAccommodationDetail(accommodationId);

        return ResponseEntity.ok(accommodationShowDto);
    }

    @PutMapping
    public ResponseEntity<Void> updateAccommodation(@RequestBody AccommodationUpdateDto accommodationUpdateDto) {
        accommodationService.updateAccommodation(accommodationUpdateDto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{accommodationId}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long accommodationId) {
        accommodationService.deleteAccommodation(accommodationId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<AccommodationListDto>> filterAccommodations(@RequestParam LocalDate checkIn,
                                                                           @RequestParam LocalDate checkOut,
                                                                           @RequestParam Double minPrice,
                                                                           @RequestParam Double maxPrice,
                                                                           @RequestParam Integer capacity) {
        List<Accommodation> accommodationsByFilters = accommodationService.findAccommodationsByFilters(checkIn,
                checkOut, minPrice, maxPrice, capacity);

        List<AccommodationListDto> accommodationListDtos = accommodationsByFilters.stream()
                .map(accommodation -> AccommodationListDto.of(accommodation, accommodation.getAccommodationAmenities()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(accommodationListDtos);
    }
}
