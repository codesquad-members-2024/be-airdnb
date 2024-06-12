package team07.airbnb.common.exception.base_exceptions;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends ApplicationException {

    public UnAuthorizedException(String log){
        super("권한이 없습니다.", HttpStatus.FORBIDDEN, log);
    }
}
