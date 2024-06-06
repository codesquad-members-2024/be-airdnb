package team07.airbnb.domain.user.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import team07.airbnb.domain.user.entity.UserEntity;

import java.util.Collection;
import java.util.Collections;

@Getter
@AllArgsConstructor
public class JwtAuthentication implements Authentication {

    private final String jwtToken;
    private final UserEntity userEntity;

    private boolean authenticated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return jwtToken;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userEntity;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return userEntity.getName();
    }
}
