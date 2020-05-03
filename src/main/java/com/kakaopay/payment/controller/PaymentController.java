package com.kakaopay.payment.controller;

import com.kakaopay.payment.dto.RequestDto;
import com.kakaopay.payment.dto.ResponseDto;
import com.kakaopay.payment.model.CardInfo;
import com.kakaopay.payment.model.Payment;
import com.kakaopay.payment.model.PaymentCardInfo;
import com.kakaopay.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// TODO: grobal Exception handler
@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
public class PaymentController {

    private static final int MAX_ID_LENGTH = 20;

    private ModelMapper modelMapper;
    private PaymentService paymentService;

    @Autowired
    public PaymentController(ModelMapper modelMapper, PaymentService paymentService) {
        this.modelMapper = modelMapper;
        this.paymentService = paymentService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/payments", consumes = "application/json; charset=UTF-8")
    public String createPayment(@RequestBody @Valid RequestDto.Payment paymentReq) throws Exception {
        Payment payment = (Payment) convert(paymentReq, Payment.class);
        payment.setManagementNumber(generateManagementNumber());
        // TODO: model converter 로 구현?
        CardInfo cardInfo = CardInfo.builder()
                .cardNumber(paymentReq.getCardNumber())
                .validity(paymentReq.getValidity())
                .cvc(paymentReq.getCvc())
                .build();
        PaymentCardInfo paymentCardInfo = PaymentCardInfo.builder()
                .paymentID(payment.getManagementNumber())
                .cardInfo(cardInfo)
                .build();
        payment.setPaymentCardInfo(paymentCardInfo);

        return this.paymentService.process(payment);
    }

    // update
    // TODO: path param & request body
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/payments/cancel", consumes = "application/json; charset=UTF-8")
    public String updatePayment(@RequestBody @Valid RequestDto.Cancel cancelReq) throws Exception {
        Payment cancel = (Payment) convert(cancelReq, Payment.class);
        cancel.setManagementNumber(generateManagementNumber());
        return this.paymentService.process(cancel);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/payments/{managementNumber}", produces = "application/json")
    public ResponseDto.Payment getPayment(@PathVariable String managementNumber) throws Exception {
        Payment payment = this.paymentService.findByManagementNumber(managementNumber);
        return (ResponseDto.Payment) convert(payment, ResponseDto.Payment.class);
    }

    private Object convert(Object object, Class<?> type) {
        return modelMapper.map(object, type);
    }

    private String generateManagementNumber() {
        return RandomStringUtils.randomAlphanumeric(MAX_ID_LENGTH);
    }
}
