package codesquad.team05.web.accommodation;

import codesquad.team05.service.AccommodationService;
import codesquad.team05.util.AccommodationMapper;
import codesquad.team05.web.accommodation.dto.request.AccommodationSave;
import codesquad.team05.web.accommodation.dto.request.AccommodationUpdate;
import codesquad.team05.web.accommodation.dto.response.AccommodationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/accommodations")
@RequiredArgsConstructor
@Slf4j
public class AccommodationController {

    private final AccommodationService accommodationService;
    private final ObjectMapper objectMapper;

    @GetMapping({"/{id}"})
    public ResponseEntity<AccommodationResponse> accommodationDetails(@PathVariable Long id) {
        return ResponseEntity
                .ok(accommodationService.getAccommodationById(id));
    }

    @PostMapping
    public ResponseEntity<Long> register(
            @RequestParam List<MultipartFile> files,
            @RequestParam String form,
            UriComponentsBuilder uriComponentsBuilder
    ) throws JsonProcessingException {
        List<String> pictureUrls = files.stream().map(this::uploadFile).toList();
        Long accommodationId = accommodationService.register(
                AccommodationMapper.toSaveService(
                        objectMapper.readValue(form, AccommodationSave.class)
                ),
                pictureUrls
        );
        URI location = uriComponentsBuilder.path("/accommodations/{id}")
                .buildAndExpand(accommodationId)
                .toUri();
        return ResponseEntity
                .created(location)
                .body(accommodationId);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestParam List<MultipartFile> files,
            @RequestParam String form
    ) throws JsonProcessingException {
        AccommodationUpdate accommodationUpdate = objectMapper.readValue(form, AccommodationUpdate.class);
        List<String> url = files.stream().map(this::uploadFile).toList();
        accommodationService.updateAccommodation(id, AccommodationMapper.toUpdateService(accommodationUpdate), url);
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accommodationService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    private String uploadFile(MultipartFile file) {
        try {
            return accommodationService.uploadFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
