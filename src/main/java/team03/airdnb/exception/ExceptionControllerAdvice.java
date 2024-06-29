package team03.airdnb.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team03.airdnb.exception.duplicate.DuplicateException;
import team03.airdnb.exception.notFound.NotFoundException;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<String> handleDuplicateException(DuplicateException ex) {
        log.error("[DuplicateException] {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        log.error("[NotFoundException] {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(ReviewNotAllowedException.class)
    public ResponseEntity<String> handleReviewNotAllowedException(ReviewNotAllowedException ex) {
        log.error("[ReviewNotAllowedException] {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(FileUploadFailedException.class)
    public ResponseEntity<String> handleFileUploadFailedException(FileUploadFailedException ex) {
        log.error("[FileUploadFailedException] {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }
}
