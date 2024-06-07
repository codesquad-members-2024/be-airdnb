package com.airdnb.tag;

import com.airdnb.tag.entity.Tag;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
@Transactional
public class TagJpaRepository implements TagRepository {
    private final EntityManager em;

    @Override
    public Tag save(Tag tag) {
        em.persist(tag);
        return tag;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        Tag tag = em.find(Tag.class, id);
        return Optional.ofNullable(tag);
    }
}
