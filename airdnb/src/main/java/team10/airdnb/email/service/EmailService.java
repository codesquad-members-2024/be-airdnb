package team10.airdnb.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import team10.airdnb.admin.controller.request.AdminEmailRequest;
import team10.airdnb.utils.redis.RedisUtil;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private static final int AUTH_CODE_LIMIT = 6;
    private static final int RANDOM_NUMBER_LIMIT = 10;
    private static final String ENCODING = "UTF-8";
    private static final long AUTH_CODE_TTL = 60 * 5L; // 5분
    private static final String EMAIL_TITLE = "airdnb 회원 가입 인증 이메일 입니다.";
    private static final String EMAIL_CONTENT = "air-dnb 어드민 인증 페이지 인증번호 메일입니다.<br><br>" +
            "인증 번호는 [%s] 입니다.<br>인증번호를 입력해주세요";

    @Value("${spring.mail.username}")
    private String mailSenderEmail;

    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;

    public String joinEmail(AdminEmailRequest request) {
        String authCode = generateAuthCode();

        String sender = mailSenderEmail;
        String receiver = request.adminId();
        String content = String.format(EMAIL_CONTENT, authCode);

        mailSend(sender, receiver, content);

        redisUtil.setDataExpire(receiver, authCode, AUTH_CODE_TTL);

        return authCode;
    }

    public void validateAuthCode(String email, String inputAuthCode) {
        String authCode = redisUtil.getData(email);

        if (authCode == null) {
            throw new RuntimeException("AuthNumber 발행되지 않음!");
        }

        if (!authCode.equals(inputAuthCode)) {
            throw new RuntimeException("AuthNumber 불일치!");
        }
    }

    private String generateAuthCode() {
        Random random = new Random();

        StringBuilder authCodeBuilder = new StringBuilder();

        for (int idx = 0; idx < AUTH_CODE_LIMIT; idx++) {
            authCodeBuilder.append(random.nextInt(RANDOM_NUMBER_LIMIT));    // 0에서 9 [RANDOM_NUMBER_LIMIT] 까지의 random 값 할당
        }

        return authCodeBuilder.toString();
    }

    private void mailSend(String sender, String receiver, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, ENCODING); // true를 전달하여 multipart 형식의 메시지를 지원하고, "utf-8"을 전달하여 문자 인코딩을 설정
            helper.setFrom(sender);
            helper.setTo(receiver);
            helper.setSubject(EMAIL_TITLE);
            helper.setText(content, true);    //이메일의 내용 설정 두 번째 매개 변수에 true를 설정하여 html 설정으로한다.
            mailSender.send(message);
        } catch (MessagingException e) {                 //이메일 서버에 연결할 수 없거나, 잘못된 이메일 주소를 사용하거나, 인증 오류가 발생하는 등 오류
            log.error(e.getMessage());
        }
    }

}
