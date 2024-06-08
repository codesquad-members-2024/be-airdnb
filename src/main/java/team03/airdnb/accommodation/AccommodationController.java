package team03.airdnb.accommodation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import team03.airdnb.accommodation.dto.request.AccommodationSaveDto;
import team03.airdnb.accommodation.dto.request.AccommodationUpdateDto;
import team03.airdnb.accommodation.dto.response.AccommodationListDto;
import team03.airdnb.accommodation.dto.response.AccommodationShowDto;

import java.net.URI;
import java.util.List;

@RequestMapping("/accommodations")
@RestController
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    @PostMapping
    public ResponseEntity<Void> createAccommodation(@RequestBody AccommodationSaveDto accommodationSaveDto, UriComponentsBuilder uriComponentsBuilder) {
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
}
