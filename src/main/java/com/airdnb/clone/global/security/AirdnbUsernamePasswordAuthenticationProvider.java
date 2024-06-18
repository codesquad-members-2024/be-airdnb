package com.airdnb.clone.global.security;

import com.airdnb.clone.domain.member.entity.Authority;
import com.airdnb.clone.domain.member.entity.Member;
import com.airdnb.clone.domain.member.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AirdnbUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginId = authentication.getName();
        String password = authentication.getCredentials().toString();
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BadCredentialsException("Login Id not found"));

        if (passwordEncoder.matches(password, member.getPassword())) {
            return new UsernamePasswordAuthenticationToken(loginId, password, getGrantedAuthorities(member.getAuthorities()));
        } else {
            throw new BadCredentialsException("Invalid password");
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        authorities.forEach(authority -> grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName())));

        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
