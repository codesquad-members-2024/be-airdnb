package com.airdnb.member;

import com.airdnb.global.ApiResponse;
import com.airdnb.global.UriMaker;
import com.airdnb.member.dto.MemberRegistration;
import com.airdnb.member.dto.MemberVerification;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ApiResponse> registerMember(@Valid @RequestBody MemberRegistration memberRegistration) {
        memberService.register(memberRegistration);
        URI location = UriMaker.makeUri(memberRegistration.getId());
        return ResponseEntity.created(location).body(ApiResponse.success(null));
    }

    @PostMapping("/verify")
    public ApiResponse verifyMember(@Valid @RequestBody MemberVerification memberVerification) {
        String accessToken = memberService.verify(memberVerification);
        return ApiResponse.success(accessToken);
    }
}
