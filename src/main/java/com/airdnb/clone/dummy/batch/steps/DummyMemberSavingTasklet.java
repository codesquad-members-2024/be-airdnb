package com.airdnb.clone.dummy.batch.steps;

import com.airdnb.clone.domain.member.entity.Member;
import com.airdnb.clone.dummy.member.DummyMemberGenerator;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class DummyMemberSavingTasklet implements Tasklet, InitializingBean {

    private static final int BATCH_SIZE = 2000;
    private final JdbcTemplate jdbcTemplate;

    @Value("#{jobParameters[members]}")
    private int numberOfMembers;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        /* 요청 수 만큼 멤버 생성 */
        List<Member> members = generateMembers(numberOfMembers);

        /* 배치 사이즈만큼 bulk insert 실행 */
        String sql = "INSERT INTO MEMBER (LOGIN_ID, NAME, CREATED_AT, UPDATED_AT) VALUES (?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, members, BATCH_SIZE, (ps, member) -> {
            ps.setString(1, member.getLoginId()); // 멤버 로그인 아이디
            ps.setString(2, member.getName()); // 멤버 이름
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now())); // 생성 시간
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now())); // 최근 수정 시간
        });

        /* 모든 배치 삽입 후 태스크릿 종료 */
        contribution.setExitStatus(ExitStatus.COMPLETED);
        chunkContext.setComplete();

        return RepeatStatus.FINISHED;
    }

    private List<Member> generateMembers(int size) {
        return IntStream.rangeClosed(1, size)
                .mapToObj(i -> DummyMemberGenerator.generate())
                .toList();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.state(jdbcTemplate != null, "jdbcTemplate is required");
    }
}
