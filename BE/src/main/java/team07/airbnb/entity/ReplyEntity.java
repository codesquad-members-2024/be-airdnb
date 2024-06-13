package team07.airbnb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "REVIEW_REPLY")
@Getter
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private UserEntity writer;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private ReviewEntity review;

    private String content;
}
