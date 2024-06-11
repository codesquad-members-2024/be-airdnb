package codesquad.team05.domain.hashtag;

import codesquad.team05.domain.accommodation.accommodation_hashtag.AccommodationHashtag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "hashtag", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<AccommodationHashtag> AccommodationHashtags = new ArrayList<>();
}
