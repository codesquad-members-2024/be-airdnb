package team10.airdnb.accommodation_room_type.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accommodation_room_type")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccommodationRoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public void updateName(String name) {
        this.name = name;
    }
}
