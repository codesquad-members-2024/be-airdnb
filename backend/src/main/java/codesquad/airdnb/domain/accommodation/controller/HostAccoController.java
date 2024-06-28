package codesquad.airdnb.domain.accommodation.controller;

import codesquad.airdnb.domain.accommodation.dto.request.AccoCreateRequest;
import codesquad.airdnb.domain.accommodation.dto.response.AccoContentResponse;
import codesquad.airdnb.domain.accommodation.dto.response.AccoListResponse;
import codesquad.airdnb.domain.accommodation.service.AccoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/host/accommodations")
@RequiredArgsConstructor
public class HostAccoController {

    private final AccoService accoService;

    @PostMapping
    public ResponseEntity<AccoContentResponse> create(@Valid @RequestBody AccoCreateRequest request) {;
        return ResponseEntity.ok(accoService.create(request));
    }

    @GetMapping("/{accoId}")
    public ResponseEntity<AccoContentResponse> get(@PathVariable Long accoId) {
        return ResponseEntity.ok(accoService.get(accoId));
    }

    @GetMapping
    public ResponseEntity<AccoListResponse> getList(@RequestHeader ("Authorization") String authHeader) {
        return ResponseEntity.ok(accoService.getList(authHeader));
    }
}
