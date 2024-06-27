package team03.airdnb.kakaoMap;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/kakaoMap")
@RestController
@RequiredArgsConstructor
public class KakaoMapController {

    private final KakaoMapService kakaoMapService;

    @GetMapping
    public ResponseEntity<String> validateAddress(@RequestParam String address) {
        boolean isValid = kakaoMapService.isAddressValid(address);
        if (isValid) {
            return ResponseEntity.ok("Address is valid");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found");
        }
    }
}
