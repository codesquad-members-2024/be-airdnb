package com.airdnb.clone.dummy;

import com.airdnb.clone.domain.member.entity.Member;
import com.airdnb.clone.domain.member.repository.MemberRepository;
import com.airdnb.clone.domain.stay.entity.Stay;
import com.airdnb.clone.domain.stay.repository.StayRepository;
import com.airdnb.clone.dummy.member.DummyMemberGenerator;
import jakarta.annotation.PostConstruct;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DummyStayService {

    @Autowired
    private StayRepository stayRepository;

    @Autowired
    private MemberRepository memberRepository;

    // 더미데이터 만들지 않을 때는 주석 풀지 않기
    @PostConstruct
    public void init() {
//        List<Member> members = IntStream.range(0, 10000)
//                .mapToObj(i -> DummyMemberGenerator.generate())
//                .collect(Collectors.toList());
//        memberRepository.saveAll(members);
//
//        List<Member> allMembers = memberRepository.findAll();
//
//        List<Stay> stays = IntStream.range(0, 200000)
//                .mapToObj(i -> {
//                    if (0 <= i && i <= 60000) {
//                        return DummyStayGenerator.generateJeju();
//                    }
//                    else if (60001 <= i && i <= 100000) {
//                        return DummyStayGenerator.generateBusan();
//                    }
//                    else if (100001 <= i && i <= 140000) {
//                        return DummyStayGenerator.generateGangwon();
//                    }
//                    return DummyStayGenerator.generate();
//                })
//                .collect(Collectors.toList());
//
//        try {
//            writeStaysToCSV(allMembers, stays, "stays.csv");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void writeStaysToCSV(List<Member> allMembers, List<Stay> stays, String fileName) throws IOException {
        try (FileWriter out = new FileWriter(fileName);
             CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                     .withHeader("BATH_COUNT", "BEDROOM_COUNT", "BED_COUNT", "CHECK_IN_TIME"
                             , "CHECK_OUT_TIME", "GUEST_COUNT", "CLEANING_FEE", "HOST_ID"
                             , "PER_NIGHT", "ALIAS", "DESCRIPTION", "@lan", "@lon"))) {

            int count = 0;
            for (Stay stay : stays) {
                printer.printRecord(stay.getRoomInfo().getBathCount(), stay.getRoomInfo().getBedroomCount(), stay.getRoomInfo().getBedCount(),
                        stay.getCheckInTime(), stay.getCheckOutTime(), stay.getRoomInfo().getGuestCount(),
                        stay.getFee().getCleaningFee(), allMembers.get(count++).getId(), stay.getFee().getPerNight(),
                        stay.getAlias(), stay.getDescription(), stay.getPoint().getY(), stay.getPoint().getX());
                if (count == 9999) {
                    count = 0;
                }
            }
        }
    }
}
