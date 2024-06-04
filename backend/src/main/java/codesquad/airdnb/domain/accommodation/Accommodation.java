package codesquad.airdnb.domain.accommodation;

import codesquad.airdnb.domain.accommodation.embedded.FloorPlan;
import codesquad.airdnb.domain.accommodation.embedded.Location;
import codesquad.airdnb.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACCOMMODATION")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "HOST_ID")
    private Member host;

    private String title;

    private String placeCategory;

    private Integer price;

    private String description;

    private Time checkInTime;

    private Time checkOutTime;

    @Embedded
    private FloorPlan floorPlan = new FloorPlan();

    @Embedded
    private Location location = new Location();
}
