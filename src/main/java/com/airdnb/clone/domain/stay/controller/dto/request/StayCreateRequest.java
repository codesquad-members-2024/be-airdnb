package com.airdnb.clone.domain.stay.controller.dto.request;

import com.airdnb.clone.domain.stay.entity.RoomInformation;
import com.airdnb.clone.domain.stay.entity.Stay;
import com.airdnb.clone.domain.stay.entity.Stay.Status;
import com.airdnb.clone.domain.stay.entity.Stay.StayBuilder;
import com.airdnb.clone.domain.stay.entity.Stay.Type;
import com.airdnb.clone.domain.stay.entity.StayFee;
import com.airdnb.clone.domain.stay.entity.StayImage;
import com.airdnb.clone.util.PointUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;

@Getter
@RequiredArgsConstructor
public class StayCreateRequest {

    @NotNull
    private final Long hostId;

    @NotBlank
    private final String alias;

    @NotNull
    private final double latitude;

    @NotNull
    private final double longitude;

    @NotNull
    private final LocalTime checkInTime;

    @NotNull
    private final LocalTime checkOutTime;

    private final String description;

    @Valid
    @NotNull
    private final StayFee fee;

    @Valid
    @NotNull
    private final Type type;

    private final List<Long> amenityIds;
    private final List<StayImage> images;

    @Valid
    @NotNull
    private final RoomInformation roomInfo;

    public StayBuilder toStayBuilder() {
        return Stay.builder()
                .alias(alias)
                .point(PointUtil.createPoint(latitude, longitude))
                .checkInTime(checkInTime)
                .checkOutTime(checkOutTime)
                .description(description)
                .fee(fee)
                .type(type)
                .status(Status.OPEN)
                .images(images)
                .roomInfo(roomInfo);
    }
}
