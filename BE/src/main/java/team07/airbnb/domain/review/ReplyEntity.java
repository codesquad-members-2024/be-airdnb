package team07.airbnb.domain.review;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import team07.airbnb.domain.user.entity.UserEntity;

@Entity
@Table(name = "REVIEW_REPLY")
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private UserEntity writer;

    @ManyToOne
    private ReviewEntity review;

    private String content;
}
