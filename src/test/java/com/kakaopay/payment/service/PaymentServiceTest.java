package com.kakaopay.payment.service;

import com.kakaopay.payment.component.TelegramTransfer;
import com.kakaopay.payment.repository.PaymentCardInfoRepository;
import com.kakaopay.payment.repository.PaymentRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PaymentServiceTest {

    private PaymentRepository paymentRepository = mock(PaymentRepository.class);
    private PaymentCardInfoRepository paymentCardInfoRepository = mock(PaymentCardInfoRepository.class);
    private TelegramTransfer telegramTransfer = mock(TelegramTransfer.class);

    @Test
    public void givenManagementNumber_whenFindByManagementNumber_thenPayment() {

    }

    // payment , cancel, exception
    @Test
    public void givenPayment_whenProcess_thenManagementNumber() {

    }
}