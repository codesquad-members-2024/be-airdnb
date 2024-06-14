package codesquad.team05.domain.host;

import codesquad.team05.domain.user.User;
import jakarta.persistence.*;
import lombok.Setter;


@Entity
@Setter
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    private String introduction;
    private double averageRating;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "login_id")
    private User user;

}
