package team07.airbnb.common.auth.exception;

public class AuthorizeException extends RuntimeException{

    public AuthorizeException(){
        super("권한이 없습니다.");
    }
}
