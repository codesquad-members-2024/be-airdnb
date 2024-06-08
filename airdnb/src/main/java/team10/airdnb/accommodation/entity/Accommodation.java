package team10.airdnb.accommodation.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team10.airdnb.accommodation_room_type.entity.AccommodationRoomType;
import team10.airdnb.accommodation_type.entity.AccommodationType;

@Entity
@Table(name = "accommodation")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "max_capacity", nullable = false)
    private long maxCapacity;

    @ManyToOne
    @JoinColumn(name = "accommodation_type", nullable = true)
    private AccommodationType accommodationType;

    @ManyToOne
    @JoinColumn(name = "accommodation_room_type", nullable = true)
    private AccommodationRoomType accommodationRoomType;

    @Column(name = "bedroom_count", nullable = false)
    private long bedroomCount;

    @Column(name = "bathroom_count", nullable = false)
    private long bathroomCount;

    @Column(name = "bed_count", nullable = false)
    private long bedCount;

    @Column(name = "per_price", nullable = false)
    private long perPrice;
}
