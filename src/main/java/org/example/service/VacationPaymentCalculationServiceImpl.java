package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.VacationPaymentData;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class VacationPaymentCalculationServiceImpl implements VacationPaymentCalculationService {

    public static final double AVG_DAYS_IN_MONTH = 29.3;

    private final HolidaysCalendarService holidaysCalendarService;

    private long countHolidays(LocalDate firstDay, LocalDate lastDay) {
        long result = 0L;

        while (!firstDay.isAfter(lastDay)) {
            if (holidaysCalendarService.isHoliday(firstDay)) {
                result++;
            }
            firstDay = firstDay.plusDays(1);
        }
        return result;
    }

    @Override
    public long calculate(VacationPaymentData data) {
        checkVacationDates(data);
        long daysBetween = Duration.between(
                data.getFirstDay().atStartOfDay(),
                data.getLastDay().atStartOfDay()
        ).toDays() + 1;
        /*
         * Предполагаем, что в период отпуска влючены дни продления отпуска всвязи с праздничными днями.
         * Праздничные дни в отпуске не оплачиваются
         * */
        long nonHolidayDays = daysBetween - countHolidays(data.getFirstDay(), data.getLastDay());
        checkNonHolidaysCount(nonHolidayDays);
        long longAvgSalary = new BigDecimal(data.getAvgSalary()).movePointRight(2).unscaledValue().longValue();
        return (long) (longAvgSalary / AVG_DAYS_IN_MONTH * nonHolidayDays);
    }

    private void checkNonHolidaysCount(long nonHolidayDays) {
        if (nonHolidayDays <= 0) {
            throw new IllegalArgumentException("В заданном периоде начисления отпускных все дни праздичные. "
                    + "Заданы неверные даты отпуска. Помните, что в случае попадания праздничного дня"
                    + " на период отпуста последний продляется.");
        }
    }

    private void checkVacationDates(VacationPaymentData data) {
        if (data.getFirstDay().isAfter(data.getLastDay())) {
            throw new IllegalArgumentException(
                    "Неверно заданы даты отпуска. Последний день отпуска "
                            + "не может быть раньше первого. Первый день: " + data.getFirstDay()
                            + ". Последний день: " + data.getLastDay()
            );
        }
    }
}
