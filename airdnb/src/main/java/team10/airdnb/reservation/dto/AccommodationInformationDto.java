package team10.airdnb.reservation.dto;

public record AccommodationInformationDto(
        long accommodationId,
        String accommodationName
) {
    public static AccommodationInformationDto from(long accommodationId, String accommodationName) {
        return new AccommodationInformationDto(accommodationId, accommodationName);
    }
}
