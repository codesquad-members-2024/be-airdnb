package team03.airdnb.accommodation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import team03.airdnb.accommodation.dto.request.AccommodationFilterDto;
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

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Void> createAccommodation(@RequestPart("file") MultipartFile file, @RequestPart("saveDto") AccommodationSaveDto saveDto, UriComponentsBuilder uriComponentsBuilder) {
        Long accommodationId = accommodationService.createAccommodation(saveDto, file);
        URI location = uriComponentsBuilder.path("/accommodations/{id}")
                .buildAndExpand(accommodationId)
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<AccommodationListDto>> showAccommodationList() {
        List<AccommodationListDto> accommodationListDtos = accommodationService.showAccommodationList();

        return ResponseEntity.ok(accommodationListDtos);
    }

    @GetMapping("/list/{hostId}")
    public ResponseEntity<List<AccommodationListDto>> showAccommodationListByHostId(@PathVariable Long hostId) {
        List<AccommodationListDto> accommodationListDtos = accommodationService.showAccommodationListByHostId(hostId);

        return ResponseEntity.ok(accommodationListDtos);
    }

    @GetMapping("/{accommodationId}")
    public ResponseEntity<AccommodationShowDto> showAccommodationDetail(@PathVariable Long accommodationId) {
        AccommodationShowDto accommodationShowDto = accommodationService.showAccommodationDetail(accommodationId);

        return ResponseEntity.ok(accommodationShowDto);
    }

    @PutMapping
    public ResponseEntity<Void> updateAccommodation(@RequestBody AccommodationUpdateDto updateDto) {
        accommodationService.updateAccommodation(updateDto);

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
    public ResponseEntity<List<AccommodationListDto>> filterAccommodations(@ModelAttribute AccommodationFilterDto filterDto) {
        List<AccommodationListDto> accommodationsByFilters = accommodationService.findAccommodationsByFilters(filterDto);

        return ResponseEntity.ok(accommodationsByFilters);
    }

    @GetMapping("/filter/region/{region}")
    public ResponseEntity<List<AccommodationListDto>> filterAccommodations(@PathVariable String region) {
        List<AccommodationListDto> accommodationsByFilters = accommodationService.findAccommodationsByRegion(region);

        return ResponseEntity.ok(accommodationsByFilters);
    }
}