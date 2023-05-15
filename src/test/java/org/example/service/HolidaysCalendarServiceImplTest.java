package org.example.service;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class HolidaysCalendarServiceImplTest {
    HolidaysCalendarService holidaysCalendarService = new HolidaysCalendarServiceImpl();

    @Test
    public void whenHoliday() {
        assertTrue(holidaysCalendarService.isHoliday(LocalDate.of(2023, 1, 1)));
    }

    @Test
    public void whenNotHoliday() {
        assertFalse(holidaysCalendarService.isHoliday(LocalDate.of(2023, 2, 2)));
    }


}