package com.airdnb.reservation;

import com.airdnb.reservation.entity.Reservation;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReservationJpaRepository implements ReservationRepository {
    private final EntityManager em;

    @Override
    public Reservation save(Reservation reservation) {
        em.persist(reservation);
        return reservation;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        Reservation reservation = em.find(Reservation.class, id);
        return Optional.ofNullable(reservation);
    }
}
