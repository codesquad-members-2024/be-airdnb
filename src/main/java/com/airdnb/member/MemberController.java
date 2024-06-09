package com.airdnb.member;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> registerMember(@Valid @RequestBody MemberRegistration memberRegistration) {
        memberService.register(memberRegistration);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(memberRegistration.getId())
            .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyMember(@Valid @RequestBody MemberVerification memberVerification) {
        return ResponseEntity.ok(memberService.verify(memberVerification));
    }

}
