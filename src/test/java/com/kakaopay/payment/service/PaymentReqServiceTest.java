package com.kakaopay.payment.service;

import com.kakaopay.payment.dto.PaymentDto;
import com.kakaopay.payment.model.CardInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.mock;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
class PaymentReqServiceTest {

    //    private PaymentRepository paymentRepository = mock(PaymentRepository.class);
//    private PaymentCardInfoRepository paymentCardInfoRepository = mock(PaymentCardInfoRepository.class);
//    private TelegramTransfer telegramTransfer = mock(TelegramTransfer.class);
    @Autowired
    private PaymentService paymentService;

    private PaymentDto.PaymentReq paymentReqOne;
    private PaymentDto.PaymentReq paymentReqTwo;

    @BeforeEach
    public void setup() {
        CardInfo cardInfo = CardInfo.builder()
                .cardNumber("0123456789")
                .validity("0325")
                .cvc("777")
                .build();
        String managementNumber = RandomStringUtils.randomAlphanumeric(20);
        paymentReqOne = PaymentDto.PaymentReq.builder()
                .cardNumber(cardInfo.getCardNumber())
                .validity(cardInfo.getValidity())
                .cvc(cardInfo.getCvc())
                .amount(10000L)
                .installmentMonth(0)
                .build();

        paymentReqTwo = PaymentDto.PaymentReq.builder()
                .cardNumber(cardInfo.getCardNumber())
                .validity(cardInfo.getValidity())
                .cvc(cardInfo.getCvc())
                .amount(20000L)
                .installmentMonth(0)
                .build();
    }

    @Test
    public void givenManagementNumber_whenFindByManagementNumber_thenPayment() {

    }

    // payment , cancel, exception
    @Test
    public void givenPayment_whenProcess_thenManagementNumber() {

    }

//    @Test
//    public void transaction_test() throws Exception {
//        String actual1 = paymentService.payProcess(paymentReqOne);
//        String actual2 = paymentService.payProcess(paymentReqTwo);
//
//        System.out.println(actual1);
//        System.out.println(actual2);
//        System.out.println(paymentService.count());
////        System.out.println(paymentService.findByManagementNumber(actual2));
//    }
}