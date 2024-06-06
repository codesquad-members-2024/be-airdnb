package team07.airbnb.domain.user.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import team07.airbnb.domain.user.entity.UserEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;


@RequiredArgsConstructor
@Getter
public class JwtUserDetails implements UserDetails, OAuth2User {

    private final UserEntity user;
    private final Map<String, Object> attributes;
    private String jwt;

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey()));
    }

    @Override
    public String getPassword() {
        return jwt;
    }

    public void setPassword(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String getUsername() {
        return user.getName();
    }


    @Override
    public String getName() {
        return user.getName();
    }
}
