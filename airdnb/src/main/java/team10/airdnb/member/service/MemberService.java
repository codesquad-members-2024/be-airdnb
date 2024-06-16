package team10.airdnb.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team10.airdnb.member.controller.request.MemberCreationRequest;
import team10.airdnb.member.controller.request.MemberLoginRequest;
import team10.airdnb.member.entity.Member;
import team10.airdnb.member.exception.MemberIdNotFoundException;
import team10.airdnb.member.exception.MemberLoginException;
import team10.airdnb.member.repository.MemberRepository;
import team10.airdnb.error.ErrorCode;
import team10.airdnb.oauth.exception.AuthenticationException;
import team10.airdnb.exception.BusinessException;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createMember(MemberCreationRequest request) {
        Member member = request.toEntity();

        validateDuplicateMember(member);

        return memberRepository.save(member);
    }

    public Member registerMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    public Member loginMember(MemberLoginRequest request) {
        Member member = findMemberByMemberId(request.email());

        if (!member.getPassword().equals(request.password())) {
            throw new MemberLoginException();
        }

        return member;
    }

    private void validateDuplicateMember(Member member) throws BusinessException {
        Optional<Member> optionalMember = memberRepository.findByEmail(member.getEmail());
        if (optionalMember.isPresent()) {
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_MEMBER);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Member findMemberByRefreshToken(String refreshToken) {
        Member member = memberRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new AuthenticationException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
        LocalDateTime tokenExpirationTime = member.getTokenExpirationTime();
        if (tokenExpirationTime.isBefore(LocalDateTime.now())) {
            throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
        return member;
    }

    public Member findMemberByMemberId(String id) {
        return memberRepository.findById(id)
                .orElseThrow(MemberIdNotFoundException::new);
    }
}

