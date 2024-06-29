package codesquad.team05.service;

import codesquad.team05.domain.user.User;
import codesquad.team05.domain.user.UserRepository;
import codesquad.team05.web.user.dto.request.JoinRequestServiceDto;
import codesquad.team05.web.user.dto.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationProvider authenticationProvider;

    public Long save(JoinRequestServiceDto requestDto){

        String hash = bCryptPasswordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto.getLoginId()
                , requestDto.getName()
                , hash
                , requestDto.getAddress()
                , requestDto.getBirthdate());

       return userRepository.save(user).getId();
    }

    public void login(LoginRequest request) {

        try {
            Authentication authenticate
                    = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(request.getLoginId(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (AuthenticationException e) {
            throw new RuntimeException("로그인에 실패했습니다.", e);

        }
    }


}
