package com.airdnb.clone.domain.member.controller;

import com.airdnb.clone.domain.member.controller.dto.MemberJoinRequest;
import com.airdnb.clone.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostMapping
    public void register(@RequestBody MemberJoinRequest request) {
        memberService.join(request.toMember());
    }
}
