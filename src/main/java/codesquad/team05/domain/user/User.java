package codesquad.team05.domain.user;

import codesquad.team05.domain.reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {

    @Id
    private String loginId;
    private String name;
    private String password;
    private String nickname;
    private String address;
    private LocalDate birthDate;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();
}
