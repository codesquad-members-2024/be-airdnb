package team07.airbnb.common.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;

@Component
public class DateHelper {
    public boolean isDateInMonthYear(int year, int month, LocalDate date) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startOfMonth = yearMonth.atDay(1);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();

        return !date.isBefore(startOfMonth) && !date.isAfter(endOfMonth);
    }
}
