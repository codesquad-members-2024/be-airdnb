package com.airdnb.stay.repository;

import com.airdnb.stay.entity.StayComment;
import com.airdnb.stay.entity.StayComment.CommentStatus;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StayCommentJpaRepository implements StayCommentRepository {
    private final EntityManager em;

    @Override
    public List<StayComment> findCommentsByStayId(Long stayId) {
        String jpql = "SELECT sc FROM StayComment sc WHERE sc.stay.id = :stayId AND sc.status = :status";
        return em.createQuery(jpql, StayComment.class)
                .setParameter("stayId", stayId)
                .setParameter("status", CommentStatus.ACTIVE)
                .getResultList();
    }

    @Override
    public Double findCommentRatingAvgByStayId(Long stayId) {
        String jpql = "SELECT AVG(sc.rating) FROM StayComment sc WHERE sc.stay.id = :stayId AND sc.status = :status";

        return (Double) em.createQuery(jpql)
                .setParameter("stayId", stayId)
                .setParameter("status", CommentStatus.ACTIVE)
                .getSingleResult();
    }

}
