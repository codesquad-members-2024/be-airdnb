package team10.airdnb.member.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team10.airdnb.jwt.dto.JwtTokenDto;
import team10.airdnb.jwt.service.TokenManager;
import team10.airdnb.member.controller.reponse.MemberResponse;
import team10.airdnb.member.controller.request.MemberCreationRequest;
import team10.airdnb.member.controller.request.MemberLoginRequest;
import team10.airdnb.member.entity.Member;
import team10.airdnb.member.service.MemberService;

import java.util.Arrays;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;
    private final TokenManager tokenManager;

    @PostMapping("/api/register")
    public ResponseEntity<?> registerMember(@RequestBody @Valid MemberCreationRequest request) {
        Member createdMember = memberService.createMember(request);

        log.info("Member 생성 완료 : #{}", createdMember.getId());

        JwtTokenDto jwtResponse = tokenManager.createJwtTokenDto(createdMember);

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> loginMember(@RequestBody @Valid MemberLoginRequest request) {
        Member loggedInMember = memberService.loginMember(request);

        log.info("Member 로그인 완료 : #{}", loggedInMember.getId());

        JwtTokenDto jwtResponse = tokenManager.createJwtTokenDto(loggedInMember);

        return ResponseEntity.ok(jwtResponse);
    }

}
