package team07.airbnb.common.auth.exception;

import org.springframework.http.HttpStatus;
import team07.airbnb.common.exception.ApplicationException;

public class UnAuthorizedException extends ApplicationException {

    public UnAuthorizedException(){
        super("권한이 없습니다.", HttpStatus.FORBIDDEN);
    }
}
