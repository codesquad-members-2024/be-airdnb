package codesquad.team05.domain.picture;

import codesquad.team05.domain.accomodation.Accommodation;
import codesquad.team05.web.picture.dto.response.PictureDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Picture {

    @Id
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    public Picture(String url) {
        this.url = url;
    }

    public Picture() {

    }

    public PictureDto toEntity() {
        PictureDto pictureDto = new PictureDto();
        pictureDto.setUrl(url);
        return pictureDto;
    }
}
