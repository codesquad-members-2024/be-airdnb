package team07.airbnb.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team07.airbnb.service.S3Service;

@RequestMapping("/file")
@RestController
@RequiredArgsConstructor
public class FileController {

    private final S3Service s3Service;

    @GetMapping("presigned-url/{fileName}")
    public String getPreSignedUrl(
            @PathVariable(name = "fileName") @Schema(description = "확장자명을 포함해주세요")
            String fileName) {
        return s3Service.getPreSignedUrl("images", fileName);
    }
}
