package com.kakaopay.payment.service;

import com.kakaopay.payment.common.ValueAddedTaxUtils;
import com.kakaopay.payment.component.TelegramTransfer;
import com.kakaopay.payment.model.Payment;
import com.kakaopay.payment.model.PaymentCardInfo;
import com.kakaopay.payment.repository.PaymentCardInfoRepository;
import com.kakaopay.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;
    private PaymentCardInfoRepository paymentCardInfoRepository;
    private TelegramTransfer telegramTransfer;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, PaymentCardInfoRepository paymentCardInfoRepository, TelegramTransfer telegramTransfer) {
        this.paymentRepository = paymentRepository;
        this.paymentCardInfoRepository = paymentCardInfoRepository;
        this.telegramTransfer = telegramTransfer;
    }

    public Payment findByManagementNumber(String managementNumber) {
        return paymentRepository.findByManagementNumber(managementNumber);
    }

    // TODO: 카드사 전송 먼저 하고 저장? 트랜젝션 고민 필요
    // 일단 저장 되는거 확인 먼저 하자
    public String process(Payment payment) throws Exception {

        // TODO : 트랜젝션 문제...
        setPaymentAttribute(payment);
        this.telegramTransfer.transfer(payment);
        return this.paymentRepository.save(payment).getManagementNumber();
    }

    private void setPaymentAttribute(Payment payment) throws Exception {
        payment.setValueAddedTax(ValueAddedTaxUtils.calculate(payment.getAmount(), payment.getValueAddedTax()));
        switch (payment.getPaymentType()) {
            case PAYMENT:
                this.paymentCardInfoRepository.save(payment.getPaymentCardInfo());
                break;
            case CANCEL:
                PaymentCardInfo cardInfo = this.paymentCardInfoRepository.findByPaymentID(payment.getOriginManagementNumber());
                payment.setPaymentCardInfo(cardInfo);
                payment.setInstallmentMonth(0);
                break;
            default:
                throw new UnsupportedOperationException("");
        }
    }
}
