package com.airdnb.security;

import com.airdnb.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public SecurityMember loadUserByUsername(String id) throws UsernameNotFoundException {
        return memberRepository.findById(id)
            .map(SecurityMember::new)
            .orElseThrow(() -> new UsernameNotFoundException(id));
    }
}
