package org.example.service;

import java.time.LocalDate;

public interface VacationPayCalculationService {
    long calculate(long avgSalary, LocalDate startDay, LocalDate endDay);
}
