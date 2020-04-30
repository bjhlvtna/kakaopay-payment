package com.kakaopay.payment.service;

import com.kakaopay.payment.dto.RequestDto;
import com.kakaopay.payment.model.Payment;
import com.kakaopay.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Optional<Payment> findById(String id) {
        return paymentRepository.findById(id);
    }

    public void save(RequestDto.Payment paymentReq) {
        Payment payment = Payment.builder()
                .amount(paymentReq.getAmount())
                .id("1")
                .build();
        this.paymentRepository.save(payment);
    }
}
