package codesquad.airdnb.domain.accommodation.repository;

import codesquad.airdnb.domain.accommodation.dto.additionals.FloorPlanData;
import codesquad.airdnb.domain.accommodation.dto.additionals.LocationData;
import codesquad.airdnb.domain.accommodation.dto.response.AccoContentResponse;
import codesquad.airdnb.domain.accommodation.entity.*;
import codesquad.airdnb.domain.member.QMember;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Repository
@RequiredArgsConstructor
public class AccoRepositoryImpl implements AccoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final EntityManager entityManager;

    @Override
    public List<Accommodation> findAllByHostId(Long hostId) {
        QAccommodation accommodation = QAccommodation.accommodation;
        QMember member = QMember.member;
        QAccoImage accoImage = QAccoImage.accoImage;

        return queryFactory.selectFrom(accommodation)
                .join(accommodation.host, member).fetchJoin()
                .join(accommodation.images, accoImage).fetchJoin()
                .where(accommodation.host.id.eq(hostId))
                .fetch();
    }

    @Override
    public AccoContentResponse getAccoContentOf(Long accoId) {
        QAccommodation acco = QAccommodation.accommodation;
        QMember member = QMember.member;
        QAccoImage accoImage = QAccoImage.accoImage;
        QAccoAmen accoAmen = QAccoAmen.accoAmen;
        QAmenity amenity = QAmenity.amenity;

        AccoContentResponse accoContentResponse = queryFactory.from(acco)
                .leftJoin(acco.host, member)
                .leftJoin(acco.images, accoImage)
                .leftJoin(accoAmen).on(accoAmen.accommodation.id.eq(acco.id))
                .leftJoin(accoAmen.amenity, amenity)
                .where(acco.id.eq(accoId))
                .transform(groupBy(acco.id).list(
                        Projections.constructor(codesquad.airdnb.domain.accommodation.dto.response.AccoContentResponse.class,
                                acco.id,
                                member.id,
                                acco.title,
                                acco.placeCategory,
                                acco.basePricePerNight,
                                acco.description,
                                acco.checkInTime,
                                acco.checkOutTime,
                                Projections.constructor(LocationData.class,
                                        acco.location.country,
                                        acco.location.province,
                                        acco.location.city,
                                        acco.location.district,
                                        acco.location.streetAddress,
                                        acco.location.streetAddressDetail,
                                        acco.location.postalCode,
                                        acco.location.coordinate),
                                Projections.constructor(FloorPlanData.class,
                                        acco.floorPlan.maxGuestCount,
                                        acco.floorPlan.maxInfantCount,
                                        acco.floorPlan.bedroomCount,
                                        acco.floorPlan.bedCount,
                                        acco.floorPlan.bathroomCount),
                                list(amenity.name),
                                list(accoImage.url))
                )).stream().distinct().findFirst().orElseThrow(NoSuchElementException::new);

        // QueryDsl에서 별도 distinct 사용이 불가하여 중복 제거 로직 추가
        Set<String> imageUrls = new HashSet<>(accoContentResponse.imageUrls());
        Set<String> amenityNames = new HashSet<>(accoContentResponse.amenityNames());

        accoContentResponse.imageUrls().clear();
        accoContentResponse.imageUrls().addAll(imageUrls);

        accoContentResponse.amenityNames().clear();
        accoContentResponse.amenityNames().addAll(amenityNames);

        return accoContentResponse;
    }
}
