package com.team01.airdnb.accommadation;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
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
  private final QAccommodation accommodation;
  private final QReservation reservation;
  private final QImage image;


  public AccommodationFilterRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    this.jpaQueryFactory = jpaQueryFactory;
    accommodation = QAccommodation.accommodation;
    reservation = QReservation.reservation;
    image = QImage.image;
  }

  public List<AccommodationSearchResponse> filterAccommodation(LocalDate checkin,
      LocalDate checkout,
      Long minPrice, Long maxPrice, Integer adultCount, Integer childrenCount,
      Integer infantsCount, String location, Double latitude, Double longitude) {
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
                .where(image.accommodation.eq(accommodation)
                    .and(image.id.eq(
                        JPAExpressions
                            .select(image.id.min())
                            .from(image)
                            .where(image.accommodation.eq(accommodation))
                    ))
                )
        ))
        .from(accommodation)
        .leftJoin(reservation).on(accommodation.id.eq(reservation.accommodation.id))
        .where(
            notReservation()
                .or(startDateGt(checkin, checkout))
                .or(endDateLt(checkin, checkout)),
            betweenPrice(minPrice, maxPrice),
            checkAdult(adultCount),
            checkChildren(childrenCount),
            checkInfants(infantsCount),
            address(location),
            checkRadius(latitude, longitude))
        .fetch();
  }

  private BooleanExpression address(String location) {
    BooleanExpression locationCondition = Expressions.asBoolean(true).isTrue();

    if (location != null) {
      String[] locations = location.split(" ");
      for (String keyword : locations) {
        locationCondition = locationCondition.and(accommodation.address.contains(keyword));
      }
    }

    return locationCondition;
  }

  private BooleanExpression notReservation() {
    return reservation.id.isNull();
  }

  private BooleanExpression startDateGt(LocalDate checkin, LocalDate checkout) {
    if (checkin == null || checkout == null) {
      return null;
    }
    return reservation.startDate.gt(checkin).and(reservation.startDate.gt(checkout));
  }

  private BooleanExpression endDateLt(LocalDate checkin, LocalDate checkout) {
    if (checkin == null || checkout == null) {
      return null;
    }
    return reservation.endDate.lt(checkin).and(reservation.endDate.lt(checkout));
  }

  private BooleanExpression betweenPrice(Long minPrice, Long maxPrice) {
    return maxPrice != null ? accommodation.price.between(minPrice, maxPrice) : null;
  }

  private BooleanExpression checkAdult(Integer adultCount) {
    return adultCount != null ? accommodation.maxAdults.goe(adultCount) : null;
  }

  private BooleanExpression checkChildren(Integer childrenCount) {
    return childrenCount != null ? accommodation.maxChildren.goe(childrenCount) : null;
  }

  private BooleanExpression checkInfants(Integer infantsCount) {
    return infantsCount != null ? accommodation.maxInfants.goe(infantsCount) : null;
  }

  private BooleanExpression checkRadius(Double latitude, Double longitude) {
    if (latitude == null || longitude == null) {
      return null;
    }

    final double radius = 6371;
    final double maxDistance = 1;

    NumberExpression<Double> distance = Expressions.numberTemplate(Double.class,
        "{0} * acos(cos(radians({1})) * cos(radians({2})) * cos(radians({3}) - radians({4})) + sin(radians({1})) * sin(radians({2})))",
        radius, latitude, accommodation.latitude, accommodation.longitude, longitude);

    return distance.loe(maxDistance);
  }
}
