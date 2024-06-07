package team10.airdnb.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team10.airdnb.exception.response.BusinessExceptionResponse;
import team10.airdnb.exception.response.ValidExceptionResponse;
import team10.airdnb.member.exception.MemberIdNotFoundException;
import team10.airdnb.oauth.exception.AuthenticationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidExceptionResponse> handleValidationException(MethodArgumentNotValidException e) {
        ValidExceptionResponse response = ValidExceptionResponse.from(e);

        log.error(response.toString());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleMemberIdNotFoundException(BusinessException e) {
        BusinessExceptionResponse response = BusinessExceptionResponse.from(e);

        log.error(response.toString());

        return new ResponseEntity<>(response, e.getErrorCode().getHttpStatus());
    }

}
