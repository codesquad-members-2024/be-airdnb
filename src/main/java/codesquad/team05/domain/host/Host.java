package codesquad.team05.domain.host;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.domain.user.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "host_id")
    private Long id;

    private String introduction;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login_id")
    private User user;

    @OneToMany(mappedBy = "host", cascade = CascadeType.PERSIST)
    private List<Accommodation> accommodations = new ArrayList<>();
}
