package codesquad.team05.elasticsearch;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccommodationSearch {

    private String nameCondition;

    public AccommodationSearch(String nameCondition) {
        this.nameCondition = nameCondition;
    }
}
