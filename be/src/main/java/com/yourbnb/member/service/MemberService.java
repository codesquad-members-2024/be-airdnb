package com.yourbnb.member.service;

import com.yourbnb.member.model.Member;
import com.yourbnb.member.model.dto.MemberResponse;
import com.yourbnb.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MemberResponse> getAllMembers(){
        return memberRepository.findAll().stream()
                    .map(MemberResponse::from)
                .collect(Collectors.toList());
    }

    public Optional<MemberResponse> getMemberById(String memberId){
        return memberRepository.findById(memberId)
                .map(MemberResponse::from);
    }
}
