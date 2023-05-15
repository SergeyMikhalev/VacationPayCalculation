package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class VacationPaymentData {
    @NotNull(message = "Средняя заработная плана (avgSalary) не может быть null")
    @Pattern(regexp = "^\\d*\\.?\\d{0,2}$", message = "Неверно задана средняя зарплата (avgSalary) за 12 месяцев. "
            + "Правильный формат: положительное число с двумя числами после запятой.")
    String avgSalary;
    @NotNull(message = "Дата первого дня отпуска (firstDay) не может быть null")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    LocalDate firstDay;
    @NotNull(message = "Дата последнего дня отпуска (lastDay) не может быть null")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    LocalDate lastDay;
}
