package codesquad.team05.domain.host;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.domain.user.User;
import jakarta.persistence.*;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


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

    @OneToMany(mappedBy = "host", cascade = CascadeType.PERSIST)
    private List<Accommodation> accommodations = new ArrayList<>();
}
