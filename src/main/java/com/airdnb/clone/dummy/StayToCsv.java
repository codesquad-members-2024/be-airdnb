//package com.airdnb.clone.dummy;
//
//import com.airdnb.clone.domain.stay.entity.Stay;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVPrinter;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.util.List;
//
//public class StayToCsv {
//
//    public static void main(String[] args) throws IOException {
//        List<Stay> stays = getStays(); // 예시 데이터
//        String fileName = "stays.csv";
//
//        try (FileWriter fileWriter = new FileWriter(fileName);
//             CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {
//
//            // 헤더 행 작성
//            for (Field field : Stay.class.getDeclaredFields()) {
//                csvPrinter.print(field.getName());
//            }
//
//            // 데이터 행 작성
//            for (Stay stay : stays) {
//                for (Field field : Stay.class.getDeclaredFields()) {
//                    field.setAccessible(true);
//                    Object value = field.get(stay).toString();
//                    csvPrinter.print(value);
//                }
//            }
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private static List<Stay> getStays() {
//        // 실제 데이터를 가져오는 코드를 작성합니다.
//        return IntStream.range(0, 100)
//                .mapToObj(i -> {
//                    if (0 <= i && i <= 60) {
//                        return DummyStayGenerator.generateJeju();
//                    }
//                    if (61 <= i && i <= 80) {
//                        return DummyStayGenerator.generateBusan();
//                    }
//                    if (81 <= i && i <= 100) {
//                        return DummyStayGenerator.generateGangwon();
//                    }
//                    return DummyStayGenerator.generateKorea();
//                })
//                .collect(Collectors.toList());
//    }
//}
//
