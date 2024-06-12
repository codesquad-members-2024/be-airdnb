package team07.airbnb.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team07.airbnb.common.exception.ApplicationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<String> handleApplicationException(ApplicationException e) {
        log.debug("Occurred By : {%s}\n{%s}".formatted(e.getClass(), e.getLog()));

        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleAny(Exception e) {
        log.error("예상치 못한 예외 발생", e);
        return "서버에 오류가 발생했습니다.😭\n잠시후 다시 시도해주세요.";
    }
}
