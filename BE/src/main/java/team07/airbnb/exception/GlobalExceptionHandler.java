package team07.airbnb.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team07.airbnb.common.ApiResponse;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ApiResponse<String> handleApplicationException(ApplicationException e) {
        log.debug("Occurred By : {%s}\n{%s}".formatted(e.getClass(), e.getLog()));

        return ApiResponse.of(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));

        log.debug("[ Validation Error Occurred ]\n" + errors);

        return ApiResponse.of(HttpStatus.BAD_REQUEST, errors);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public String handleAnyRunTimeException(RuntimeException e) {
        log.warn("예상치 못한 예외 발생", e);
        return "서버에 오류가 발생했습니다.😭\n잠시후 다시 시도해주세요.";
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleAnyCheckedException(Exception e) {
        log.error("예상치 못한 예외 발생", e);
        return "서버에 오류가 발생했습니다.😭\n잠시후 다시 시도해주세요.";
    }
}
