package org.example.controller;

import org.example.service.VacationPayCalculationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@RestController
public class VacationPayCalculationController {

    private final VacationPayCalculationService vacationPayCalculationService;

    public VacationPayCalculationController(VacationPayCalculationService vacationPayCalculationService) {
        this.vacationPayCalculationService = vacationPayCalculationService;
    }

    @GetMapping("calculate")
    public ResponseEntity<String> calculate(
            @RequestParam("avgSalary") @Valid @Pattern(regexp = "^\\d*\\.?\\d{0,2}$", message = "Неверный денежный формат") String avgSalary,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate
    ) {
        long longAvgSalary = Long.parseLong(avgSalary);
        long amount = vacationPayCalculationService.calculate(longAvgSalary, startDate, endDate);
        return ResponseEntity.ok(amount / 100 + "." + amount % 100);
    }
}
