package codesquad.airdnb.domain.accommodation.entity;

import codesquad.airdnb.domain.accommodation.dto.request.AccoReservationRequest;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;

public class AccoProducts {

    private final List<AccoProduct> accoProducts;

    public AccoProducts(List<AccoProduct> accoProducts) {
        this.accoProducts = accoProducts;
    }

    // TODO: 일부 검증 로직(isReserved, reserveDate)을 QueryDSL!! 사용해서 처리하도록 변경해보기
    // TODO: 같은 상품 여러 개나 날짜 겹치는 케이스 방어 로직 추가(상품의 날짜가 serial하게 있는지 체크)
    public void validate(AccoReservationRequest request) {
        // 예약하려는 모든 상품이 존재하는지.
//        if (request.products().size() > accoProducts.size())
//            throw new NoSuchElementException("예약하려는 상품 중 존재하지 않는 상품이 있습니다.");
        if (ChronoUnit.DAYS.between(request.startDate(), request.endDate()) != accoProducts.size()) {
            throw new IllegalArgumentException("해당 날짜에 예약이 불가능한 숙소입니다.");
        }

        for (AccoProduct accoProduct : accoProducts) {
            // 예약하려는 상품이 이미 예약되어 있는건 아닌 지.
            if (accoProduct.isReserved())
                throw new IllegalArgumentException("이미 예약된 상품입니다.");
            //
            if (accoProduct.getReserveDate().isBefore(request.startDate()) ||
                    accoProduct.getReserveDate().isAfter(request.endDate().minusDays(1))) {
                throw new IllegalArgumentException("예약 범위를 벗어난 날짜의 상품입니다.");
            }
            if (accoProduct.getAccommodation().getFloorPlan().getMaxGuestCount() < request.adultCount() + request.childCount() ||
                    accoProduct.getAccommodation().getFloorPlan().getMaxInfantCount() < request.infantCount())
                throw new IllegalArgumentException("최대 인원 수를 초과하였습니다.");
        }
    }

    public Long getTotalRoomCharge() {
        return accoProducts.stream()
                .map(AccoProduct::getPrice)
                .reduce(Long::sum)
                .orElseThrow(() -> new NoSuchElementException("해당하는 숙소상품이 존재하지 않습니다."));
    }

    public List<ReservationProduct> toReservationProducts(Reservation reservation) {
        return accoProducts.stream()
                .map(accoProduct -> ReservationProduct.builder()
                        .reservation(reservation)
                        .accoProduct(accoProduct)
                        .build())
                .toList();
    }

    public void updateRoomToBooked() {
        accoProducts.forEach(AccoProduct::reserve);
    }
}
