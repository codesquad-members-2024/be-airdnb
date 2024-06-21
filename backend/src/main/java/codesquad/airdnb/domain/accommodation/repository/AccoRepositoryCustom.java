package codesquad.airdnb.domain.accommodation.repository;

import codesquad.airdnb.domain.accommodation.dto.response.AccoContentResponse;
import codesquad.airdnb.domain.accommodation.entity.Accommodation;
import org.locationtech.jts.geom.Point;

import java.util.List;

public interface AccoRepositoryCustom {

    AccoContentResponse getAccoContentOf(Long accoId);

    List<Accommodation> findAllByHostId(Long hostId);

    List<Long> findIdsByCoordAndHumanCount(Point point, Integer maxGuestCount, Integer maxInfantCount);
}
