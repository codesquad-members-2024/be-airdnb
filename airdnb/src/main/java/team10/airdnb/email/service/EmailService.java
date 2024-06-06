package team10.airdnb.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final int MAX_AUTH_NUMBER_LIMIT = 6;
    private static final int MAX_RANDOM_NUMBER_LIMIT = 10;
    private static final String ENCODING = "UTF-8";
    private static final long AUTH_NUMBER_TTL = 60 * 5L; // 5분

    @Value("${spring.mail.username}")
    private String mailUsername;

    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;

    public String joinEmail(AdminEmailRequest request) {
        String authNumber = makeRandomNumber();    // 인증번호를 생성

        String setFrom = mailUsername; // email-config에 설정한 자신의 이메일 주소를 입력
        String toMail = request.adminId();
        String title = "회원 가입 인증 이메일 입니다."; // 이메일 제목
        String content =
                "air-dnb 어드민 인증 페이지 인증번호 메일입니다." +    //html 형식으로 작성 !
                        "<br><br>" +
                        "인증 번호는 " + authNumber + "입니다." +
                        "<br>" +
                        "인증번호를 입력해주세요"; //이메일 내용 삽입

        mailSend(setFrom, toMail, title, content);

        saveAuthNumberAtRedis(toMail, authNumber);

        return authNumber;
    }

    public void checkAuthNum(String email, String authNumber) {
        String savedAuthNum = redisUtil.getData(email);
        if (savedAuthNum == null) {
            throw new RuntimeException("AuthNumber 발행되지 않음!");
        }
        if (!savedAuthNum.equals(authNumber)){
            throw new RuntimeException("AuthNumber 불일치!");
        }

    }

    private void saveAuthNumberAtRedis(String email, String authNumber) {
        redisUtil.setDataExpire(email, authNumber, AUTH_NUMBER_TTL);
    }

    private String makeRandomNumber() {    // 6자리의 auth number 를 만드는 코드
        Random random = new Random();
        StringBuilder randomNumber = new StringBuilder();
        for (int idx = 0; idx < MAX_AUTH_NUMBER_LIMIT; idx++) {
            randomNumber.append(random.nextInt(MAX_RANDOM_NUMBER_LIMIT));
        }

        return randomNumber.toString();
    }

    private void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();   // JavaMailSender 객체를 이용하여  MimeMessage 생성
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, ENCODING);//이메일 메시지와 관련된 설정을 수행합니다.
            // true를 전달하여 multipart 형식의 메시지를 지원하고, "utf-8"을 전달하여 문자 인코딩을 설정
            helper.setFrom(setFrom);//이메일의 발신자 주소 설정
            helper.setTo(toMail);//이메일의 수신자 주소 설정
            helper.setSubject(title);//이메일의 제목을 설정
            helper.setText(content, true);//이메일의 내용 설정 두 번째 매개 변수에 true를 설정하여 html 설정으로한다.
            mailSender.send(message);
        } catch (MessagingException e) {//이메일 서버에 연결할 수 없거나, 잘못된 이메일 주소를 사용하거나, 인증 오류가 발생하는 등 오류
            log.error(e.getMessage());
        }

    }

}
