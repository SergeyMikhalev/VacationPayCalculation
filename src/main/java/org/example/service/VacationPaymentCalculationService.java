package org.example.service;

import org.example.dto.VacationPaymentData;

public interface VacationPaymentCalculationService {
    long calculate(VacationPaymentData data);
}
