package team10.airdnb.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 인증 && 인가
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-001", "토큰이 만료되었습니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-002", "해당 토큰은 유효한 토큰이 아닙니다."),
    NOT_EXISTS_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "A-003", "Authorization Header가 빈값입니다."),
    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "A-004", "인증 타입이 Bearer 타입이 아닙니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A-005", "해당 refresh token은 존재하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-006", "해당 refresh token은 만료됐습니다."),
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "A-007", "해당 토큰은 ACCESS TOKEN이 아닙니다."),
    FORBIDDEN_ADMIN(HttpStatus.FORBIDDEN, "A-008", "관리자 Role이 아닙니다."),

    // 이메일
    AUTH_CODE_NOT_EXISTS(HttpStatus.NOT_FOUND, "E-001", "인증 번호가 존재하지 않습니다."),
    INVALID_AUTH_CODE(HttpStatus.UNAUTHORIZED, "E-002", "인증 번호가 일치하지 않습니다."),

    // 숙소 방 유형
    ALREADY_SAVED_ROOM_TYPE(HttpStatus.BAD_REQUEST, "R-001", "이미 해당하는 이름의 방 정보가 존재합니다."),
    ACCOMMODATION_ROOM_TYPE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "R-002", "해당하는 숙소 방 유형은 존재하지 않습니다."),

    // 숙소 유형
    ALREADY_SAVED_ACCOMMODATION_TYPE(HttpStatus.BAD_REQUEST, "T-001", "이미 해당하는 이름의 숙소 유형이 존재합니다."),
    ACCOMMODATION_TYPE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "T-002", "해당 숙소 유형은 존재하지 않습니다."),

    //회원
    INVALID_MEMBER_TYPE(HttpStatus.BAD_REQUEST, "M-001", "잘못된 회원 타입 입니다.(memberType : KAKAO)"),
    ALREADY_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST, "M-002", "이미 가입된 회원 입니다."),
    MEMBER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "M-003", "해당 회원은 존재하지 않습니다."),

    //어메니티
    AMENITY_TYPE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "AM-001", "해당 어메니티 유형은 존재하지 않습니다."),
    ALREADY_SAVED_AMENITY(HttpStatus.BAD_REQUEST, "AM-002", "이미 해당하는 어메니티 정보가 존재합니다.");

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
}
