package codesquad.team05.web.accommodation;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.service.AccommodationService;
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

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class AccommodationController {

    private final AccommodationService accommodationService;

    private final ObjectMapper objectMapper;

    @GetMapping({"/{id}"})
    public ResponseEntity<AccommodationResponse> get(@PathVariable("id") Long id) {
        Accommodation accommodation = accommodationService.getAccommodation(id);
        AccommodationResponse result = accommodation.toEntity();

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestParam("files") List<MultipartFile> files
            , @RequestParam("form") String form) throws JsonProcessingException {

        AccommodationSave accommodationSave = objectMapper.readValue(form, AccommodationSave.class);


        List<String> url = files.stream().map(this::uploadFile).toList();
        accommodationService.register(accommodationSave, url);

        return ResponseEntity.ok("Success");
    }

    @PostMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestParam("files") List<MultipartFile> files
            , @RequestParam("form") String form) throws JsonProcessingException {
        AccommodationUpdate accommodationUpdate = objectMapper.readValue(form, AccommodationUpdate.class);
        List<String> url = files.stream().map(this::uploadFile).toList();
        accommodationService.updateAccommodation(id, accommodationUpdate, url);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        accommodationService.delete(id);
    }

    private String uploadFile(MultipartFile file) {

        try {
            return accommodationService.uploadFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
