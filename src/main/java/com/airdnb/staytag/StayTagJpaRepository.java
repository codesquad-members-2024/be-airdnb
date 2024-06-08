package com.airdnb.staytag;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
@Transactional
public class StayTagJpaRepository implements StayTagRepository {
    private final EntityManager em;

    @Override
    public StayTag save(StayTag stayTag) {
        em.persist(stayTag);
        return stayTag;
    }
}
