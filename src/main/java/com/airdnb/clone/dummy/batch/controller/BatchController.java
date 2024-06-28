package com.airdnb.clone.dummy.batch.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/batch")
public class BatchController {

    private static final String UNIQUE_PARAM_ID = "param.id";
    private final JobOperator jobOperator;

    // TODO: 처리율 제한 추가
    @PostMapping("/members/save")
    public ResponseEntity<String> runSaveMember(@RequestParam Map<String, String> params) {
        try {
//            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder(); // job 파마미터 빌더
//            jobParametersBuilder.addLong(UNIQUE_PARAM_ID, System.currentTimeMillis()); // 파라미터에 유니크 아이디 부여
//            params.forEach(jobParametersBuilder::addString); // queryString -> 파라미터 빌더에 추가
//            JobParameters jobParameters = jobParametersBuilder.toJobParameters(); // 파라미터 빌더 -> job 파라미터로 변환
//            Job memberSavingJob = jobRegistry.getJob("MemberSavingJob");
//            jobLauncher.run(memberSavingJob, jobParameters); // job 실행

            Properties properties = new Properties();
            properties.put(UNIQUE_PARAM_ID, LocalDateTime.now().toString()); // 파라미터에 유니크 아이디 부여
            properties.putAll(params); // Map<String, String> 쿼리 스트링 -> properties 로 변환

            Long executeId = jobOperator.start("MemberSavingJob", properties); // 멤버 생성 job 실행

            return ResponseEntity.ok("멤버 생성 배치 ID: %d".formatted(executeId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("배치 실행 실패: " + e.getMessage());
        }
    }

    @PostMapping("/stays/save")
    public ResponseEntity<String> runSaveStay(@RequestParam Map<String, String> params) {
        try {
            Properties properties = new Properties();
            properties.put(UNIQUE_PARAM_ID, LocalDateTime.now().toString()); // 파라미터에 유니크 아이디 부여
            properties.putAll(params); // Map<String, String> 쿼리 스트링 -> properties 로 변환

            Long executeId = jobOperator.start("StaySavingJob", properties); // 숙소 생성 job 실행

            return ResponseEntity.ok("숙소 생성 배치 ID: %d".formatted(executeId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("배치 실행 실패: " + e.getMessage());
        }
    }

    @PostMapping("/bookings/save")
    public ResponseEntity<String> runSaveBooking(@RequestParam(required = false) Map<String, String> params) {
        try {
            Properties properties = new Properties();
            properties.put(UNIQUE_PARAM_ID, LocalDateTime.now().toString()); // 파라미터에 유니크 아이디 부여
            properties.putAll(params); // Map<String, String> 쿼리 스트링 -> properties 로 변환

            Long executeId = jobOperator.start("BookingSavingJob", properties); // 예약 생성 job 실행

            return ResponseEntity.ok("예약 생성 배치 ID: %d".formatted(executeId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("배치 실행 실패: " + e.getMessage());
        }
    }
}
