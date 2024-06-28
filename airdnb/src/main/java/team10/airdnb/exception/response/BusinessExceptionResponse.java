package team10.airdnb.exception.response;

import team10.airdnb.exception.BusinessException;

public record BusinessExceptionResponse(String exceptionClass, String errorMessage) {

    public static BusinessExceptionResponse from(BusinessException e) {
        String exceptionClass = e.getClass().getSimpleName();
        String errorMessage = e.getErrorCode().getMessage();

        return new BusinessExceptionResponse(exceptionClass, errorMessage);
    }
}
