package com.kakaopay.payment.service;

import com.kakaopay.payment.common.ManagementNumberGenerator;
import com.kakaopay.payment.common.PaymentType;
import com.kakaopay.payment.component.TelegramTransfer;
import com.kakaopay.payment.dto.PaymentDto;
import com.kakaopay.payment.model.CardInfo;
import com.kakaopay.payment.model.Payment;
import com.kakaopay.payment.model.PaymentCardInfo;
import com.kakaopay.payment.repository.PaymentCardInfoRepository;
import com.kakaopay.payment.repository.PaymentRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
class PaymentServiceTest {

    private PaymentRepository paymentRepository = mock(PaymentRepository.class);
    //    private PaymentCardInfoRepository paymentCardInfoRepository = mock(PaymentCardInfoRepository.class);
    private TelegramTransfer telegramTransfer = mock(TelegramTransfer.class);

    @Autowired
    private PaymentService paymentService;

    private PaymentDto.PaymentReq paymentReqOne;
    private PaymentDto.PaymentReq paymentReqTwo;
    private String managementNumber;

    @BeforeEach
    public void setup() {
        this.managementNumber = ManagementNumberGenerator.generate();
        CardInfo cardInfo = CardInfo.builder()
                .cardNumber("0123456789")
                .validity("0325")
                .cvc("777")
                .build();
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

//    @Test
//    public void givenManagementNumber_whenFindByManagementNumber_thenPaymentRes() {
//
//        String originManagementNumber = ManagementNumberGenerator.generate();
//        Payment response = Payment.builder()
//                .valueAddedTax(1000)
//                .amount(10000L)
//                .originManagementNumber(originManagementNumber)
//                .installmentMonth(0)
//                .managementNumber(managementNumber)
//                .paymentCardInfo(PaymentCardInfo.builder()
//                        .cardInfo(CardInfo.builder()
//                                .cardNumber("0123456789")
//                                .validity("0325")
//                                .cvc("777")
//                                .build())
//                        .paymentID(originManagementNumber)
//                        .build())
//                .paymentType(PaymentType.CANCEL)
//                .build();
//
//        when(this.paymentRepository.findByLatestPayment(managementNumber)).thenReturn(response);
//
//        PaymentDto.PaymentRes actual = this.paymentService.findByManagementNumber(managementNumber);
//
//        assertThat(actual.getAmount(), is(10000L));
//        assertThat(actual.getPaymentId(), is(originManagementNumber));
//        assertThat(actual.getValidity(), is("0325"));
//    }

    @Test
    public void givenPayment_whenPayProcess_thenTransactionRes() {

    }
}