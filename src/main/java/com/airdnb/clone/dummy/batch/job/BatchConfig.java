package com.airdnb.clone.dummy.batch.job;

import com.airdnb.clone.domain.booking.entity.Booking;
import com.airdnb.clone.domain.member.entity.Member;
import com.airdnb.clone.domain.stay.entity.Stay;
import com.airdnb.clone.dummy.batch.steps.BookingProcessor;
import com.airdnb.clone.dummy.batch.steps.DummyMemberSavingTasklet;
import com.airdnb.clone.dummy.stay.DummyStayGenerator;
import jakarta.persistence.EntityManagerFactory;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class BatchConfig {

    private final DummyMemberSavingTasklet dummyMemberSavingTasklet;
    private final BookingProcessor bookingProcessor;
    private final EntityManagerFactory entityManagerFactory;
    private final DataSource dataSource;

    @Bean
    @Qualifier(value = "MemberSavingJob")
    public Job saveMemberJob(JobRepository jobRepository, Step memberSaveStep) {
        return new JobBuilder("MemberSavingJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(memberSaveStep)
                .build();
    }

    @Bean
    public Step memberSaveStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("memberSaveStep", jobRepository)
                .tasklet(dummyMemberSavingTasklet, transactionManager)
                .build();
    }

    @Bean
    @Qualifier(value = "StaySavingJob")
    public Job saveStayJob(JobRepository jobRepository, Step staySaveStep) {
        return new JobBuilder("StaySavingJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(staySaveStep)
                .build();
    }

    @Bean
    public Step staySaveStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("StaySavingStep", jobRepository)
                .<Member, List<Stay>>chunk(1000, transactionManager)
                .reader(readMembers())
                .processor(setHost(null))
                .writer(saveStayByJdbc(jdbcStayWriter()))
                .build();
    }

    @Bean
    @StepScope
    public JpaCursorItemReader<Member> readMembers() {
        return new JpaCursorItemReaderBuilder<Member>()
                .name("jpaMemberReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT m FROM Member m")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Member, List<Stay>> setHost(@Value("#{jobParameters[stays]}") Integer requestCount) {
        log.info("숙소 요청 개수: {}", requestCount);
        AtomicInteger currentNumber = new AtomicInteger(0);
        int incrementStep = 1;
        float jejuRatio = 0.3F;
        float busanRatio = 0.2F;
        float gangwonRatio = 0.2F;

        return member -> IntStream.rangeClosed(1, requestCount)
                .mapToObj(i -> {
                    if (currentNumber.getAndAdd(incrementStep) < requestCount * jejuRatio) {
                        return DummyStayGenerator.generateJeju(member);
                    }
                    if (currentNumber.get() < requestCount * (jejuRatio + busanRatio)) {
                        return DummyStayGenerator.generateBusan(member);
                    }
                    if (currentNumber.get() < requestCount * (jejuRatio + busanRatio + gangwonRatio)) {
                        return DummyStayGenerator.generateGangwon(member);
                    }
                    return DummyStayGenerator.generateKorea(member);
                })
                .toList();
    }

    @Bean
    @StepScope
    public ItemWriter<List<Stay>> saveStayByJpa(JpaItemWriter<Stay> jpaStayWriter) {
        return stayChunk -> {
            for (List<Stay> stays : stayChunk) {
                jpaStayWriter.write(new Chunk<>(stays));
            }
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<Stay> jpaStayWriter() {
        return new JpaItemWriterBuilder<Stay>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    @StepScope
    public ItemWriter<List<Stay>> saveStayByJdbc(JdbcBatchItemWriter<Stay> jdbcBatchItemWriter) {
        return stayChunk -> {
            for (List<Stay> stays : stayChunk) {
                jdbcBatchItemWriter.write(new Chunk<>(stays));
            }
        };
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Stay> jdbcStayWriter() {
        return new JdbcBatchItemWriterBuilder<Stay>()
                .dataSource(dataSource)
                .sql("INSERT INTO STAY (HOST_ID, ALIAS, DESCRIPTION, POINT, TYPE, STATUS, BATH_COUNT, BEDROOM_COUNT, BED_COUNT, GUEST_COUNT, CHECK_IN_TIME, CHECK_OUT_TIME, PER_NIGHT, CLEANING_FEE, CREATED_AT, UPDATED_AT) "
                        + "VALUES (?, ?, ?, ST_PointFromText(?, 4326), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
                .itemPreparedStatementSetter((stay, ps) -> {
                    ps.setLong(1, stay.getHost().getId());
                    ps.setString(2, stay.getAlias());
                    ps.setString(3, stay.getDescription());
                    ps.setString(4, "Point(%s %s)".formatted(stay.getPoint().getY(), stay.getPoint().getX()));
                    ps.setString(5, stay.getType().name());
                    ps.setString(6, stay.getStatus().name());
                    ps.setInt(7, stay.getRoomInfo().getBathCount());
                    ps.setInt(8, stay.getRoomInfo().getBedroomCount());
                    ps.setInt(9, stay.getRoomInfo().getBedCount());
                    ps.setInt(10, stay.getRoomInfo().getGuestCount());
                    ps.setTime(11, Time.valueOf(stay.getCheckInTime()));
                    ps.setTime(12, Time.valueOf(stay.getCheckOutTime()));
                    ps.setLong(13, stay.getFee().getPerNight());
                    ps.setLong(14, stay.getFee().getCleaningFee());
                    ps.setTimestamp(15, Timestamp.valueOf(LocalDateTime.now()));
                    ps.setTimestamp(16, Timestamp.valueOf(LocalDateTime.now()));
                })
                // TODO: 성능 비교 분석
//                .sql("INSERT INTO STAY (HOST_ID, ALIAS, DESCRIPTION, POINT, TYPE, STATUS, BATH_COUNT, BEDROOM_COUNT, BED_COUNT, GUEST_COUNT, CHECK_IN_TIME, CHECK_OUT_TIME, PER_NIGHT, CLEANING_FEE, CREATED_AT, UPDATED_AT) "
//                        + "VALUES (:hostId, :alias, :description, ST_PointFromText(:point, 4326), :type, :status, :bathCount, :bedroomCount, :bedCount, :guestCount, :checkInTime, :checkOutTime, :perNight, :cleaningFee, :createdAt, :updatedAt)")
//                .itemSqlParameterSourceProvider((stay) -> {
//                    MapSqlParameterSource params = new MapSqlParameterSource();
//                    params.addValue("hostId", stay.getHost().getId());
//                    params.addValue("alias", stay.getAlias());
//                    params.addValue("description", stay.getDescription());
//                    params.addValue("point", "POINT(%s %s)".formatted(stay.getPoint().getY(), stay.getPoint().getX()));
//                    params.addValue("type", stay.getType().name());
//                    params.addValue("status", stay.getStatus().name());
//                    params.addValue("bathCount", stay.getRoomInfo().getBathCount());
//                    params.addValue("bedroomCount", stay.getRoomInfo().getBedroomCount());
//                    params.addValue("bedCount", stay.getRoomInfo().getBedCount());
//                    params.addValue("guestCount", stay.getRoomInfo().getGuestCount());
//                    params.addValue("checkInTime", Time.valueOf(stay.getCheckInTime()));
//                    params.addValue("checkOutTime", Time.valueOf(stay.getCheckOutTime()));
//                    params.addValue("perNight", stay.getFee().getPerNight());
//                    params.addValue("cleaningFee", stay.getFee().getCleaningFee());
//                    params.addValue("createdAt", LocalDateTime.now());
//                    params.addValue("updatedAt", LocalDateTime.now());
//                    return params;
//                })
                .build();
    }

    @Bean
    @Qualifier(value = "BookingSavingJob")
    public Job saveBookingJob(JobRepository jobRepository, Step bookingSaveStep) {
        return new JobBuilder("BookingSavingJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(bookingSaveStep)
                .build();
    }

    @Bean
    public Step bookingSaveStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("BookingSavingStep", jobRepository)
                .<Stay, List<Booking>>chunk(1000, transactionManager)
                .reader(stayReader())
                .processor(bookingProcessor)
                .writer(saveBookingByJdbc(jdbcBookingWriter()))
                .build();
    }

    @Bean
    @StepScope
    public JpaCursorItemReader<Stay> stayReader() {
        return new JpaCursorItemReaderBuilder<Stay>()
                .name("JpaStayReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT s FROM Stay s")
                .build();
    }

    @Bean
    @StepScope
    public ItemWriter<List<Booking>> saveBookingByJdbc(JdbcBatchItemWriter<Booking> jdbcBatchItemWriter) {
        return bookingChunk -> {
            for (List<Booking> bookings : bookingChunk) {
                jdbcBatchItemWriter.write(new Chunk<>(bookings));
            }
        };
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Booking> jdbcBookingWriter() {
        return new JdbcBatchItemWriterBuilder<Booking>()
                .dataSource(dataSource)
                .sql("INSERT INTO BOOKING (MEMBER_ID, STAY_ID, CHECK_IN, CHECK_OUT, TOTAL_RATE, STATUS, GUEST_COUNT, CREATED_AT, UPDATED_AT) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")
                .itemPreparedStatementSetter((booking, ps) -> {
                    ps.setLong(1, booking.getMember().getId());
                    ps.setLong(2, booking.getStay().getId());
                    ps.setTimestamp(3, Timestamp.valueOf(booking.getCheckIn()));
                    ps.setTimestamp(4, Timestamp.valueOf(booking.getCheckOut()));
                    ps.setLong(5, booking.getTotalRate());
                    ps.setString(6, booking.getStatus().name());
                    ps.setInt(7, booking.getGuestCount());
                    ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                    ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
                })
                .build();
    }
}
