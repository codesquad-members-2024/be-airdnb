package codesquad.airdnb.global.util;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomStringUtil {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    private RandomStringUtil() {
        throw new UnsupportedOperationException("유틸리티 클래스의 인스턴스를 생성할 수 없습니다.");
    }

    public static String generateRandomPassword() {
        int length = 20;
        byte[] randomBytes = new byte[20];
        secureRandom.nextBytes(randomBytes);
        String encodedString = base64Encoder.encodeToString(randomBytes);
        return encodedString.length() > length ? encodedString.substring(0, length) : encodedString;
    }
}
