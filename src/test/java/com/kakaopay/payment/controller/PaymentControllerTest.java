package com.kakaopay.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.payment.common.PaymentType;
import com.kakaopay.payment.common.TelegramFormatter;
import com.kakaopay.payment.configuration.PaymentConfiguration;
import com.kakaopay.payment.dto.RequestDto;
import com.kakaopay.payment.dto.ResponseDto;
import com.kakaopay.payment.model.CardInfo;
import com.kakaopay.payment.model.Payment;
import com.kakaopay.payment.model.PaymentCardInfo;
import com.kakaopay.payment.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Import(PaymentConfiguration.class)
@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    private static final String PATH = "/api/v1";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PaymentService paymentService;

    // 결제
    @Test
    public void givenPaymentDto_whenCreatePayment_thenSuccess() throws Exception {

        RequestDto.Payment paymentReq = RequestDto.Payment.builder()
                .cardNumber("0123456789")
                .validity("0325")
                .cvc("777")
                .installmentMonth(0)
                .amount(10000L)
                .build();

        when(paymentService.process(any())).thenReturn("KtwnKfUPiirkl96YMPsM");

        this.mockMvc.perform(
                post(String.format("%s/%s", PATH, "payments"))
                .content(asJsonString(paymentReq))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void givenCancelDto_whenUpdatePayment_thenSuccess() throws Exception {

        RequestDto.Cancel cancelReq = RequestDto.Cancel.builder()
                .managementNumber("KtwnKfUPiirkl96YMPsM")
                .amount(10000L)
                .build();

        when(paymentService.process(any())).thenReturn("R1KRNGTuHMsW4vaiE3Hy");

        this.mockMvc.perform(
                put(String.format("%s/%s", PATH, "payments/cancel"))
                        .content(asJsonString(cancelReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void givenManagementNumber_whenGetPayment_thenResponseDtoPayment() throws Exception {

        String managementNumber = "KtwnKfUPiirkl96YMPsM";

        Payment response = Payment.builder()
                .managementNumber(managementNumber)
                .paymentCardInfo(PaymentCardInfo.builder()
                        .paymentID(managementNumber)
                        .cardInfo(CardInfo.builder()
                                .cardNumber("0123456789")
                                .validity("0325")
                                .cvc("777")
                                .build())
                        .build())
                .paymentType(PaymentType.PAYMENT)
                .amount(10000L)
                .valueAddedTax(91)
                .build();

        when(paymentService.findByManagementNumber(any())).thenReturn(response);

        this.mockMvc.perform(
                get(String.format("%s/%s/%s", PATH, "payments", managementNumber)))
                .andDo(print())
                .andExpect(status().isOk());
    }




    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

