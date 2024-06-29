package codesquad.team05.elasticsearch;

import codesquad.team05.domain.accommodation.Accommodation;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Builder
@Getter
@Document(indexName = "accommodation")
@Mapping(mappingPath = "static/elastic-mapping.json")
@Setting(settingPath = "static/elastic-token.json")
public class AccommodationDocument {

    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "korean")
    private String name;

    @Field(type = FieldType.Integer)
    private int price;

    @Field(type = FieldType.Text)
    private String address;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Text)
    private String amenity;

    @Field(type = FieldType.Text)
    private String accommodationType;

    public static AccommodationDocument from(Accommodation accommodation){

        return AccommodationDocument.builder().
                name(accommodation.getName()).
                price(accommodation.getPrice()).
                address(accommodation.getAddress()).
                description(accommodation.getDescription()).
                amenity(accommodation.getAmenity()).
                accommodationType(accommodation.getAccommodationType().name()).
                build();
    }


}
