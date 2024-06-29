package team03.airdnb.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FileUploadFailedException extends RuntimeException {

    private static final ErrorCode DEFAULT_ERROR_CODE = ErrorCode.FILE_UPLOAD_FAILED;

    private final HttpStatus httpStatus;

    public FileUploadFailedException() {
        super(DEFAULT_ERROR_CODE.getMessage());
        this.httpStatus = DEFAULT_ERROR_CODE.getHttpStatus();
    }

}
