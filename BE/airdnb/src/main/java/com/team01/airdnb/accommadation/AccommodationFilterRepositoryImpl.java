package com.team01.airdnb.accommadation;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team01.airdnb.accommadation.dto.AccommodationSearchResponse;
import com.team01.airdnb.accommadation.dto.QAccommodationSearchResponse;
import com.team01.airdnb.image.QImage;
import com.team01.airdnb.reservation.QReservation;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AccommodationFilterRepositoryImpl implements AccommodationFilterRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public AccommodationFilterRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    this.jpaQueryFactory = jpaQueryFactory;
  }

  @Override
  public List<AccommodationSearchResponse> afterCheckin(LocalDate checkin, LocalDate checkout) {
    QAccommodation accommodation = QAccommodation.accommodation;
    QReservation reservation = QReservation.reservation;
    QImage image = QImage.image;

    return jpaQueryFactory
        .select(new QAccommodationSearchResponse(
            accommodation.id,
            accommodation.title,
            accommodation.price,
            accommodation.address,
            accommodation.commentsNum,
            accommodation.maxAdults,
            JPAExpressions
                .select(image.imagePath)
                .from(image)
                .where(image.accommodation.eq(accommodation))
                .limit(1)
        ))
        .from(accommodation)
        .leftJoin(reservation).on(accommodation.id.eq(reservation.accommodation.id))
        .where(reservation.id.isNull()
            .or(reservation.startDate.goe(checkout).or(reservation.endDate.loe(checkin)))).fetch();
  }

  @Override
  public List<AccommodationSearchResponse> beforeCheckout() {
    return null;
  }

  @Override
  public List<AccommodationSearchResponse> afterMinCost() {
    return null;
  }

  @Override
  public List<AccommodationSearchResponse> beforeMaxCost() {
    return null;
  }

  @Override
  public List<AccommodationSearchResponse> underMaxAdult() {
    return null;
  }

  @Override
  public List<AccommodationSearchResponse> underMaxChildren() {
    return null;
  }

  @Override
  public List<AccommodationSearchResponse> underMaxInfants() {
    return null;
  }
}
