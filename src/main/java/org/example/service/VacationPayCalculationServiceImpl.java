package org.example.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;

@Service
public class VacationPayCalculationServiceImpl implements VacationPayCalculationService {
    @Override
    public long calculate(long avgSalary, LocalDate startDay, LocalDate endDay) {
        long daysBetween = Duration.between(startDay, endDay).toDays();
        return (long) (avgSalary / 29.3 * daysBetween);
    }
}
