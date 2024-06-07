package team10.airdnb.exception.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
@RequiredArgsConstructor
public class ValidExceptionResponse {
    private final String errorField;
    private final String errorMessage;

    public static ValidExceptionResponse from(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getFieldError();

        if (fieldError != null) {
            String errorField = String.format("%s.%s", fieldError.getObjectName(), fieldError.getField());
            String errorMessage = fieldError.getDefaultMessage();
            return new ValidExceptionResponse(errorField, errorMessage);
        }

        return new ValidExceptionResponse(e.getClass().getSimpleName(), e.getMessage());
    }

    @Override
    public String toString() {
        return String.format("%s : %s", errorField, errorMessage);
    }

}
