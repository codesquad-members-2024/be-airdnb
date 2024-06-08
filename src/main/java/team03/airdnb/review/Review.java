package team03.airdnb.review;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "REVIEWS")
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private double grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
