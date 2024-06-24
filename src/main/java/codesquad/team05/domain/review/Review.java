package codesquad.team05.domain.review;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rating;
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    Accommodation accommodation;

    public Review(
            int rating,
            String comment,
            User user,
            Accommodation accommodation
    ) {
        this.rating = rating;
        this.comment = comment;
        this.user = user;
        this.accommodation = accommodation;
    }

    public void update(
            int rating,
            String comment
    ) {
        this.rating = rating;
        this.comment = comment;
    }
}
