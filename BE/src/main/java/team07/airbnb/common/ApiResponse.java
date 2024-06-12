package team07.airbnb.common;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


/**
 * 표준 응답 객체
 * @param <T>
 */
public class ApiResponse<T> {

    private final HttpStatus status;

    private final T body;

    private ApiResponse (HttpStatus status, T body) {
        this.status = status;
        this.body = body;
    }

    public static <T> ApiResponse<T> ok(T body) {
        return new ApiResponse<>(HttpStatus.OK, body);
    }

    public static <T> ApiResponse<T> created(T body) {
        return new ApiResponse<>(HttpStatus.CREATED, body);
    }

    public static <T> ApiResponse<T> of(HttpStatus status, T body) {
        return new ApiResponse<>(status, body);
    }

}
