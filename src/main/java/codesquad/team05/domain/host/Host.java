package codesquad.team05.domain.host;


import codesquad.team05.domain.user.User;
import jakarta.persistence.*;

@Entity
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double averageRating;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

}
