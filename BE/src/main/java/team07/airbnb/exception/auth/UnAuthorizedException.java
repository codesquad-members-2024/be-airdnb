package team07.airbnb.exception.auth;

import org.springframework.http.HttpStatus;
import team07.airbnb.exception.ApplicationException;

public class UnAuthorizedException extends ApplicationException {

    public UnAuthorizedException(String log){
        super("권한이 없습니다.", HttpStatus.FORBIDDEN, log);
    }
}
