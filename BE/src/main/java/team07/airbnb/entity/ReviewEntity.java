package team07.airbnb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "REVIEW")
@NoArgsConstructor
@Getter
public class ReviewEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JsonIgnore
    @Column(name = "booking_id")
    private Long bookingId;
    private String content;
    private int rating;

    @OneToMany(mappedBy = "review")
    private List<ReplyEntity> replies = new ArrayList<>();

    public ReviewEntity(Long bookingId, String content, int rating) {
        this.bookingId = bookingId;
        this.content = content;
        this.rating = rating;
    }
}
