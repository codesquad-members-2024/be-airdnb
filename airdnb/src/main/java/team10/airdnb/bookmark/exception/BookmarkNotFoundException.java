package team10.airdnb.bookmark.exception;

import team10.airdnb.error.ErrorCode;
import team10.airdnb.exception.BusinessException;

public class BookmarkNotFoundException extends BusinessException {
    public BookmarkNotFoundException() {
        super(ErrorCode.BOOKMARK_NOT_EXISTS);
    }
}
