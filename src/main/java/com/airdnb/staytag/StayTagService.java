package com.airdnb.staytag;

import com.airdnb.stay.entity.Stay;
import com.airdnb.tag.TagService;
import com.airdnb.tag.entity.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StayTagService {
    private final StayTagRepository stayTagRepository;
    private final TagService tagService;

    @Transactional
    public void createStayTags(Stay stay, List<Long> tagIds) {
        for (Long tagId : tagIds) {
            Tag tag = tagService.findTagById(tagId);
            StayTag stayTag = StayTag.builder()
                    .stay(stay)
                    .tag(tag)
                    .build();
            stayTagRepository.save(stayTag);
        }
    }
}
