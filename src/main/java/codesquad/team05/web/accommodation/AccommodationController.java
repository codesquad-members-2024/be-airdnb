package codesquad.team05.web.accommodation;

import codesquad.team05.domain.accomodation.Accommodation;
import codesquad.team05.service.AccommodationService;
import codesquad.team05.service.CouponService;
import codesquad.team05.service.DiscountPolicyService;
import codesquad.team05.web.accommodation.dto.request.AccommodationSave;
import codesquad.team05.web.accommodation.dto.request.AccommodationUpdate;
import codesquad.team05.web.accommodation.dto.response.AccommodationResponse;
import codesquad.team05.web.accommodation.dto.request.DiscountForm;
import codesquad.team05.web.coupon.dto.request.CouponSave;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @ResponseStatus
    public ResponseEntity<AccommodationResponse> get(@PathVariable("id") Long id) {
        Accommodation accommodation = accommodationService.getAccommodation(id);
        AccommodationResponse result = accommodation.toEntity();

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public void register(@RequestParam("files") List<MultipartFile> files, @RequestParam("form") String form) throws JsonProcessingException {

        AccommodationSave accommodationSave = objectMapper.readValue(form, AccommodationSave.class);


        List<String> url = files.stream().map(this::uploadFile).toList();
        accommodationService.register(accommodationSave, url);

    }

    @PostMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestParam("files") List<MultipartFile> files, @RequestParam("form") String form) throws JsonProcessingException {
        AccommodationUpdate accommodationUpdate = objectMapper.readValue(form, AccommodationUpdate.class);
        List<String> url = files.stream().map(this::uploadFile).toList();
        accommodationService.updateAccommodation(id, accommodationUpdate, url);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        accommodationService.delete(id);
    }


    private String uploadFile(MultipartFile file) {

        try {
            return accommodationService.uploadFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
