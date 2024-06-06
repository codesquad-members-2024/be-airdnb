package com.airdnb.tag;

import com.airdnb.tag.entity.Tag;
import java.util.Optional;

public interface TagRepository {
    Tag save(Tag tag);

    Optional<Tag> findById(Long id);
}
