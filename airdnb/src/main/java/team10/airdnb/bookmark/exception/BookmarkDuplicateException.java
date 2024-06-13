package team10.airdnb.bookmark.exception;

import team10.airdnb.error.ErrorCode;
import team10.airdnb.exception.BusinessException;

public class BookmarkDuplicateException extends BusinessException {
    public BookmarkDuplicateException() {
        super(ErrorCode.ALREADY_SAVED_BOOKMARK);
    }
}
