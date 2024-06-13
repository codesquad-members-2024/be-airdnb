package team10.airdnb.review.exception;

import team10.airdnb.error.ErrorCode;
import team10.airdnb.exception.BusinessException;

public class ReviewIdNotFoundException extends BusinessException {
    public ReviewIdNotFoundException() {
        super(ErrorCode.REVIEW_NOT_EXISTS);
    }
}
