package team07.airbnb.exception;

import org.springframework.http.HttpStatus;

public class IllegalRequestException extends ApplicationException{

    public IllegalRequestException(Class occuredClass) {
        super("잘못된 클라이언트 요청입니다", HttpStatus.BAD_REQUEST, "Class {%s}에서 웹 페이지가 아닌 다른 경로로 요청 발생".formatted(occuredClass.getName()));
    }
}
