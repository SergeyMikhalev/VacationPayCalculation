package org.example.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class HolidaysCalendarServiceImpl implements HolidaysCalendarService {

    private final Set<LocalDate> holidays = new HashSet<>();


    /*
            1, 2, 3, 4, 5, 6 и 8 января — Новогодние каникулы;
            7 января — Рождество Христово;
            23 февраля — День защитника Отечества;
            8 марта — Международный женский день;
            1 мая — Праздник Весны и Труда;
            9 мая — День Победы;
            12 июня — День России;
            4 ноября — День народного единства.
     */
    public HolidaysCalendarServiceImpl() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Stream.of(new String[]{
                "01.01.2023",
                "02.01.2023",
                "03.01.2023",
                "04.01.2023",
                "05.01.2023",
                "06.01.2023",
                "07.01.2023",
                "08.01.2023",
                "23.02.2023",
                "08.03.2023",
                "01.05.2023",
                "09.05.2023",
                "12.06.2023",
                "04.11.2023"
        }).forEach(strDate -> {
            LocalDate date = LocalDate.parse(strDate, dateTimeFormatter);
            holidays.add(date);
        });
    }


    @Override
    public boolean isHoliday(LocalDate date) {
        date = LocalDate.of(2023, date.getMonth(), date.getDayOfMonth());
        return holidays.contains(date);
    }
}
