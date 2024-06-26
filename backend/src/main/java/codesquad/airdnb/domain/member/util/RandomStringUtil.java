package codesquad.airdnb.domain.member.util;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomStringUtil {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();
    private static final int PASSWORD_LENGTH = 20;

    private RandomStringUtil() {
        throw new UnsupportedOperationException("유틸리티 클래스의 인스턴스를 생성할 수 없습니다.");
    }

    // 클래스 내부의 상수(비밀번호 최대길이)를 이용해서 해당 상수 길이 이하의 난수문자열을 반환합니다.
    public static String generateRandomPassword() {
        byte[] randomBytes = new byte[PASSWORD_LENGTH];
        secureRandom.nextBytes(randomBytes);
        String encodedString = base64Encoder.encodeToString(randomBytes);
        return encodedString.length() > PASSWORD_LENGTH ? encodedString.substring(0, PASSWORD_LENGTH) : encodedString;
    }
}
