package com.airdnb.stay;

import com.airdnb.global.UriMaker;
import com.airdnb.stay.dto.StayCreate;
import com.airdnb.stay.dto.StayCreateRequest;
import com.airdnb.stay.dto.StayDetailQueryResponse;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stays")
public class StayController {
    private final StayService stayService;

    @PostMapping
    public ResponseEntity<Void> createStay(@Valid @RequestBody StayCreateRequest stayCreateRequest) {
        StayCreate stayCreate = StayCreate.of(stayCreateRequest);

        Long stayId = stayService.createStay(stayCreate);

        URI location = UriMaker.makeUri(stayId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StayDetailQueryResponse> queryStayDetail(@PathVariable Long id) {
        StayDetailQueryResponse stayDetailQueryResponse = stayService.queryStayDetailById(id);
        return ResponseEntity.ok(stayDetailQueryResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStay(@PathVariable Long id) {
        stayService.softDeleteStay(id);
        return ResponseEntity.ok().build();
    }
}
