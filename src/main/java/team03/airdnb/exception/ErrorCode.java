package team03.airdnb.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    DUPLICATE_NAME(HttpStatus.CONFLICT, "Name already exists"),
    HOST_NOT_FOUND(HttpStatus.NOT_FOUND, "Host not found"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    ACCOMMODATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Accommodation not found"),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "Review not found"),
    AMENITY_NOT_FOUND(HttpStatus.NOT_FOUND, "Amenity not found"),
    DUPLICATE_RESERVATION(HttpStatus.CONFLICT, "Reservation is not available for the given dates.");

    private final HttpStatus httpStatus;
    private final String message;
}
