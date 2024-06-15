package team10.airdnb.member.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team10.airdnb.member.controller.request.MemberCreationRequest;
import team10.airdnb.member.entity.Member;
import team10.airdnb.member.service.MemberService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping("/api/register")
    public ResponseEntity<?> registerMember(@RequestBody @Valid MemberCreationRequest request){
        Member createdMember = memberService.createMember(request);

        log.info("Member 생성 완료 : #{}", createdMember.getEmail());

        return ResponseEntity.ok(createdMember);
    }
}
