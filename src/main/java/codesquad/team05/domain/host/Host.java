package codesquad.team05.domain.host;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.domain.user.User;
import jakarta.persistence.*;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "host_id")
    private Long id;
    private String introduction;
    private double averageRating;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "host", cascade = CascadeType.PERSIST)
    private List<Accommodation> accommodations = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }
}
