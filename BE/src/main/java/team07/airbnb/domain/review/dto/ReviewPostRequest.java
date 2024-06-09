package team07.airbnb.domain.review.dto;

public record ReviewPostRequest(
        String content,
        int rating
) {
}
