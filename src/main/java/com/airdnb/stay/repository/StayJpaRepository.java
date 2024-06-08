package com.airdnb.stay.repository;

import com.airdnb.stay.entity.Stay;
import com.airdnb.stay.entity.Stay.StayStatus;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional
public class StayJpaRepository implements StayRepository {
    private final EntityManager em;

    @Override
    public Stay save(Stay stay) {
        em.persist(stay);
        return stay;
    }

    @Override
    public Optional<Stay> findById(Long id) {
        String jpql = "SELECT s FROM Stay s WHERE s.id = :id AND s.status = :status";
        return em.createQuery(jpql, Stay.class)
                .setParameter("id", id)
                .setParameter("status", StayStatus.ACTIVE)
                .getResultList()
                .stream()
                .findFirst();
    }
}
