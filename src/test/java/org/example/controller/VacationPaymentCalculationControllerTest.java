package org.example.controller;

import org.example.VacationPaymentCalculationApp;
import org.example.dto.VacationPaymentData;
import org.example.service.VacationPaymentCalculationService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = VacationPaymentCalculationApp.class)
@AutoConfigureMockMvc
class VacationPaymentCalculationControllerTest {
    @MockBean
    private VacationPaymentCalculationService vacationPaymentCalculationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldWorkDefaultWay() throws Exception {
        this.mockMvc.perform(get("/calculate")
                        .param("firstDay", "01.01.2023")
                        .param("lastDay", "12.01.2023")
                        .param("avgSalary", "10000.00"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().string("0.0"));
        ArgumentCaptor<VacationPaymentData> argument = ArgumentCaptor.forClass(VacationPaymentData.class);
        verify(vacationPaymentCalculationService).calculate(argument.capture());
        assertEquals(argument.getValue().getAvgSalary(), "10000.00");
        assertEquals(argument.getValue().getLastDay(), LocalDate.of(2023, 1, 12));
        assertEquals(argument.getValue().getFirstDay(), LocalDate.of(2023, 1, 1));
    }
}