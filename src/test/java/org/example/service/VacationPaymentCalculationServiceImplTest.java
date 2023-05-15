package org.example.service;

import org.example.dto.VacationPaymentData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class VacationPaymentCalculationServiceImplTest {

    @TestConfiguration
    static class Config {
        @MockBean
        private HolidaysCalendarService holidaysCalendarService;

        @Bean
        public VacationPaymentCalculationService vacationPayCalculationService() {
            return new VacationPaymentCalculationServiceImpl(holidaysCalendarService);
        }
    }

    @Autowired
    private HolidaysCalendarService holidaysCalendarService;

    @Autowired
    private VacationPaymentCalculationService vacationPaymentCalculationService;

    @Test
    public void when12Days0Holidays() {
        VacationPaymentData data = new VacationPaymentData(
                "10000.00",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 12)
        );

        long result = vacationPaymentCalculationService.calculate(data);

        assertEquals(409556L, result);
    }

    @Test
    public void when1Day0Holidays() {
        VacationPaymentData data = new VacationPaymentData(
                "10000.00",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 1)
        );

        long result = vacationPaymentCalculationService.calculate(data);

        assertEquals(34129L, result);
    }

    @Test
    public void when10Days2Holidays() {
        Mockito.when(holidaysCalendarService.isHoliday(LocalDate.of(2021, 1, 1))).thenReturn(true);
        Mockito.when(holidaysCalendarService.isHoliday(LocalDate.of(2021, 1, 2))).thenReturn(true);
        VacationPaymentData data = new VacationPaymentData(
                "10000.00",
                LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 1, 10)
        );

        long result = vacationPaymentCalculationService.calculate(data);

        assertEquals(273037L, result);
    }

    @Test
    public void when20DaysNoHolidaysInDifferentYears() {
        VacationPaymentData data = new VacationPaymentData(
                "10000.00",
                LocalDate.of(2022, 12, 30),
                LocalDate.of(2023, 1, 18)
        );

        long result = vacationPaymentCalculationService.calculate(data);

        assertEquals(682593L, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void when10DaysAllHolidays() {
        Mockito.when(holidaysCalendarService.isHoliday(any())).thenReturn(true);
        VacationPaymentData data = new VacationPaymentData(
                "10000.00",
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 1, 10)
        );

        vacationPaymentCalculationService.calculate(data);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLastDayBeforeFirstDay() {
        VacationPaymentData data = new VacationPaymentData(
                "10000.00",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2022, 1, 10)
        );

        vacationPaymentCalculationService.calculate(data);
    }
}