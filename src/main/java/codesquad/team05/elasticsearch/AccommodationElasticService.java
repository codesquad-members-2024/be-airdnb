package codesquad.team05.elasticsearch;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationElasticService {


    private final AccommodationSearchRepository accommodationSearchRepository;
    private final AccommodationService accommodationService;
    private final ElasticsearchOperations elasticsearchOperations;

    public void save(Accommodation accommodation){
        elasticsearchOperations.save(AccommodationDocument.from(accommodation));
    }


    @Transactional(readOnly =true)
    public void indexAll(){
        Iterable<AccommodationDocument> list = accommodationSearchRepository.findAll();

    }

    public List<AccommodationDocument> findByAddress(String address){
        return accommodationSearchRepository.findByAddress(address);
    }

}
