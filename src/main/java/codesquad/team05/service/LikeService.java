package codesquad.team05.service;

import codesquad.team05.domain.accommodation.AccommodationRepository;
import codesquad.team05.domain.like.Like;
import codesquad.team05.domain.like.LikeRepository;
import codesquad.team05.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;

    public Long like(Long userId, Long accommodationId) {
        return likeRepository.save(new Like(
                userRepository.findById(userId).get(),
                accommodationRepository.findById(accommodationId).get()
        )).getId();
    }
}
