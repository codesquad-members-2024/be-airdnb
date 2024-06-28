package com.airdnb.clone.dummy.batch.steps;

import com.airdnb.clone.domain.member.entity.Member;
import com.airdnb.clone.domain.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberJobExecutionListener implements JobExecutionListener {

    private final MemberRepository memberRepository;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        List<Member> members = memberRepository.findAll();
        jobExecution.getExecutionContext().put("members", members);
        log.info("Members added to context: {}", members.size());
    }
}
