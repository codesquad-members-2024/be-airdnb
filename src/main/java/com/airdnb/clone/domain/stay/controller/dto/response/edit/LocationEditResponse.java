package com.airdnb.clone.domain.stay.controller.dto.response.edit;

import com.airdnb.clone.util.PointUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationEditResponse {

    private final double latitude;
    private final double longitude;

    public static LocationEditResponse of(Point point) {
        return new LocationEditResponse(point.getY(), point.getX());
    }
}
