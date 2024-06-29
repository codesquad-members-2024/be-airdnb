package codesquad.team05.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface AccommodationSearchRepository extends ElasticsearchRepository<AccommodationDocument, Long> {

    List<AccommodationDocument> findByAddress(String address);


}
