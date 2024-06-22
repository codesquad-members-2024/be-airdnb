package codesquad.team05.web.accommodation;

import codesquad.team05.service.AccommodationService;
import codesquad.team05.service.CouponService;
import codesquad.team05.service.DiscountPolicyService;
import codesquad.team05.util.AccommodationMapper;
import codesquad.team05.web.accommodation.dto.request.AccommodationSave;
import codesquad.team05.web.accommodation.dto.request.AccommodationUpdate;
import codesquad.team05.web.accommodation.dto.response.AccommodationResponse;
import codesquad.team05.web.accommodation.dto.request.DiscountForm;
import codesquad.team05.web.coupon.dto.request.CouponSave;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/accommodations")
@RequiredArgsConstructor
@Slf4j
public class AccommodationController {

    private final AccommodationService accommodationService;
    private final DiscountPolicyService discountPolicyService;
    private final CouponService couponService;

    private final ObjectMapper objectMapper;

    @GetMapping({"/{id}"})
    public ResponseEntity<AccommodationResponse> accommodationDetails(@PathVariable Long id) {
        return ResponseEntity
                .ok(accommodationService.getAccommodationById(id));
    }

    @PostMapping
    public ResponseEntity<Long> register(
            @RequestPart(required = false) List<MultipartFile> files,
            @RequestPart AccommodationSave accommodationSave,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        Long accommodationId = accommodationService.register(AccommodationMapper.toSaveService(accommodationSave, files));
        URI location = uriComponentsBuilder.path("/accommodations/{id}")
                .buildAndExpand(accommodationId)
                .toUri();
        return ResponseEntity
                .created(location)
                .body(accommodationId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestPart List<MultipartFile> files,
            @RequestPart AccommodationUpdate accommodationUpdate
    ) {
        accommodationService.updateAccommodation(id, AccommodationMapper.toUpdateService(accommodationUpdate, files));
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accommodationService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/{id}/discount")
    public void setDiscount(@PathVariable("id") Long id, @RequestBody DiscountForm form) {
        discountPolicyService.setDiscountPolicy(id, form);
    }

    @PutMapping("/updateDiscount")
    public void checkAndUpdateDiscounts() {
        discountPolicyService.checkAndUpdateDiscounts();
    }

    @PostMapping("/applyCoupon")
    public void applyCoupon(@RequestBody Long couponId) {
        Long userId = 1L;
        couponService.apply(userId, couponId);
    }

    @PostMapping("/coupon")
    public void create(@Valid @RequestBody CouponSave coupon) {
        couponService.create(coupon);
    }
}
