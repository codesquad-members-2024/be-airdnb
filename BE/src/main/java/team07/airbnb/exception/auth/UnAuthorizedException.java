package team07.airbnb.exception.auth;

import org.springframework.http.HttpStatus;
import team07.airbnb.exception.ApplicationException;

public class UnAuthorizedException extends ApplicationException {

    public UnAuthorizedException(Class occuredClass, Long userId, String log){
        super("권한이 없습니다.", HttpStatus.FORBIDDEN, "In [ {%s} ] class occur -> {%d} User UnAuthorized".formatted(occuredClass.getName(), userId) + log);
    }

    public UnAuthorizedException(Class occuredClass, Long userId){
        super("권한이 없습니다.", HttpStatus.FORBIDDEN, "In [ {%s} ] class occur -> {%d} User UnAuthorized".formatted(occuredClass.getName(), userId));
    }

    public UnAuthorizedException(String log) {
        super("권한이 없습니다.", HttpStatus.FORBIDDEN, log);
    }
}
