package codesquad.team05.web.search;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.elasticsearch.AccommodationDocument;
import codesquad.team05.elasticsearch.AccommodationElasticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController

@RequestMapping("/search")
public class SearchController {

    private final AccommodationElasticService accommodationElasticService;

    @GetMapping
    public ResponseEntity<List<AccommodationDocument>> search(@RequestParam String keyword){

        List<AccommodationDocument> byAddress = accommodationElasticService.findByAddress(keyword);

        return ResponseEntity.ok(byAddress);
    }


    @PostMapping
    public void createIndex(@RequestBody Accommodation accommodation){
        accommodationElasticService.save(accommodation);
    }
}
