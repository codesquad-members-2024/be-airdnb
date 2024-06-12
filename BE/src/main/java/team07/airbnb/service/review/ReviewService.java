package team07.airbnb.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team07.airbnb.entity.UserEntity;

@Service
@RequiredArgsConstructor
public class ReviewService {

    public void addReplyTo(long reviewId, String content, UserEntity writer) {
        // 해당 예약의 HOST인지 확인 필요 
    }
}
