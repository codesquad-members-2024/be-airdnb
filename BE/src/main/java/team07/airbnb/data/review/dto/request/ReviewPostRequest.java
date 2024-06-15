package team07.airbnb.data.review.dto.request;

public record ReviewPostRequest(
        String content,
        int rating
) {
}
