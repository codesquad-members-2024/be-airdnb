package codesquad.team05.domain.security.oauth2;

import java.io.Serializable;

public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUser(String name, String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
}
