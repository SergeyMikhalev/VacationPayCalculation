package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.VacationPaymentData;
import org.example.service.VacationPaymentCalculationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class VacationPaymentCalculationController {

    private final VacationPaymentCalculationService vacationPaymentCalculationService;

    @GetMapping("calculate")
    public ResponseEntity<String> calculate(@Valid VacationPaymentData data
    ) {
        long amount = vacationPaymentCalculationService.calculate(data);
        return ResponseEntity.ok(amount / 100 + "." + amount % 100);
    }
}
