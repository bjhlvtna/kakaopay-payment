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
        return paymentRepository.findByLatelyPayment(managementNumber);
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
                cancelProcess(payment);
                break;
            default:
                throw new UnsupportedOperationException("");
        }
    }

    /*
     * TODO:
     *  - 전체 취소는 1번만 가능
     *  - 부가가치세 정보를 넘기지 않는 경우, 결제데이터의 부가가치세 금액으로 취소합니다.
     *  - 할부개월수 데이터는 00(일시불)로 저장합니다.
     *  - 부분취소 API를 구현하고 Test Case를 통과시켜주세요.
     *  - 정책
     *  - Test Case 1
     *  - 결제 한 건에 대해서 모두 취소가 될 때까지 부분 금액으로 계속 취소할 수 있습니다.
     *  - 부가가치세 검증 로직 : 결제금액의 부가가치세 = 모든 부분취소 부가가치세의 합
     *  - ex. 10,000원 결제시 1,000원씩 10번 취소 가능
     */
    private void cancelProcess(Payment payment) throws Exception {
        // 가장 최근 결제 내용 받아옴
//        Payment prevPayment = this.paymentRepository.findByManagementNumber(payment.getOriginManagementNumber());
        Payment prevPayment = this.paymentRepository.findByLatelyPayment(payment.getOriginManagementNumber());
        // valid check
        if (!validCancelAmount(prevPayment.getAmount(), prevPayment.getValueAddedTax(), payment.getAmount(), payment.getValueAddedTax())) {
            throw new Exception("");
        }
        payment.setPaymentCardInfo(prevPayment.getPaymentCardInfo());
        payment.setInstallmentMonth(0);
        payment.setAmount(prevPayment.getAmount() - payment.getAmount());
        payment.setValueAddedTax(prevPayment.getValueAddedTax() - payment.getValueAddedTax());
    }

    private boolean validCancelAmount(long prevAmount, int prevTax, long amount, int tax) {
        return prevAmount >= amount && prevTax >= tax;
    }
}
