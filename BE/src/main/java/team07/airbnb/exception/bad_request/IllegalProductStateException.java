package team07.airbnb.exception.bad_request;

public class IllegalProductStateException extends BadRequestException{

    public IllegalProductStateException(String message, String log) {
        super(message, log);
    }
}
