package codesquad.team05.domain.security;

import codesquad.team05.domain.user.User;
import codesquad.team05.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(loginId).orElseThrow();

        if(user==null){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        return new PrincipalUser(user);

    }
}
