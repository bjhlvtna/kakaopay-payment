package com.kakaopay.payment.service;

import com.kakaopay.payment.component.TelegramTransfer;
import com.kakaopay.payment.dto.PaymentDto;
import com.kakaopay.payment.exception.CancelAmountException;
import com.kakaopay.payment.model.CardInfo;
import com.kakaopay.payment.model.Payment;
import com.kakaopay.payment.model.PaymentCardInfo;
import com.kakaopay.payment.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentService {

    private static final int MAX_ID_LENGTH = 20;
    private static final float VALUE_ADDED_TAX_RATE = 11F;
    private static final int DEFAULT_INSTALLMENT_MONTH = 0;

    private PaymentRepository paymentRepository;
    private TelegramTransfer telegramTransfer;

    private ModelMapper modelMapper;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, TelegramTransfer telegramTransfer, ModelMapper modelMapper) {
        this.paymentRepository = paymentRepository;
        this.telegramTransfer = telegramTransfer;
        this.modelMapper = modelMapper;
    }

    public PaymentDto.PaymentRes findByManagementNumber(String managementNumber) {
        Payment payment = paymentRepository.findByLatestPayment(managementNumber);
        return (PaymentDto.PaymentRes) convert(payment, PaymentDto.PaymentReq.class);
    }

    // TODO: 카드사 전송 먼저 하고 저장? 트랜젝션 고민 필요
    public String process(Payment payment) {
        if (this.telegramTransfer.transfer(payment)) {
            payment.setTransferSuccess(true);
        }
        return this.paymentRepository.save(payment).getManagementNumber();
    }

    //    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
//    @Transactional
    public PaymentDto.TransactionRes payProcess(PaymentDto.PaymentReq paymentReq) throws Exception {

        Payment payment = (Payment) convert(paymentReq, Payment.class);
        payment.setManagementNumber(generateManagementNumber());
        // TODO: model converter 로 구현?
        payment.setPaymentCardInfo(PaymentCardInfo.builder()
                .paymentID(payment.getManagementNumber())
                .cardInfo(CardInfo.builder()
                        .cardNumber(paymentReq.getCardNumber())
                        .validity(paymentReq.getValidity())
                        .cvc(paymentReq.getCvc())
                        .build())
                .build());

        payment.setValueAddedTax(calculateValueAddedTax(payment.getAmount(), payment.getValueAddedTax()));
        if (this.telegramTransfer.transfer(payment)) {
            payment.setTransferSuccess(true);
        }
        return PaymentDto.TransactionRes.builder()
                .managementNumber(this.paymentRepository.save(payment).getManagementNumber())
                .additionalInfo(StringUtils.EMPTY)
                .build();
//        return process(payment);
    }

    //    @Transactional
    public PaymentDto.TransactionRes cancelProcess(PaymentDto.CancelReq cancelReq) throws CancelAmountException {
        Payment cancel = (Payment) convert(cancelReq, Payment.class);
        cancel.setManagementNumber(generateManagementNumber());
        cancel.setValueAddedTax(calculateValueAddedTax(cancel.getAmount(), cancel.getValueAddedTax()));
        // 가장 최근 결제 내용 받아옴
        Payment prevPayment = this.paymentRepository.findByLatestPayment(cancel.getOriginManagementNumber());
        // valid check
        if (!validCancelAmount(prevPayment.getAmount(), prevPayment.getValueAddedTax(), cancel.getAmount(), cancel.getValueAddedTax())) {
            throw new CancelAmountException();
        }
        cancel.setPaymentCardInfo(prevPayment.getPaymentCardInfo());
        cancel.setInstallmentMonth(DEFAULT_INSTALLMENT_MONTH);
        cancel.setAmount(prevPayment.getAmount() - cancel.getAmount());
        cancel.setValueAddedTax(prevPayment.getValueAddedTax() - cancel.getValueAddedTax());

        if (this.telegramTransfer.transfer(cancel)) {
            cancel.setTransferSuccess(true);
        }
        return PaymentDto.TransactionRes.builder()
                .managementNumber(this.paymentRepository.save(cancel).getManagementNumber())
                .additionalInfo(StringUtils.EMPTY)
                .build();
//        return this.paymentRepository.save(cancel).getManagementNumber();
//        return process(cancel);
    }

    public int count() {
        return this.paymentRepository.findAll().size();
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
//    private void cancelProcess(Payment payment) throws Exception {
//        // 가장 최근 결제 내용 받아옴
//        Payment prevPayment = this.paymentRepository.findByLatestPayment(payment.getOriginManagementNumber(), LockModeType.PESSIMISTIC_WRITE);
//        // valid check
//        if (!validCancelAmount(prevPayment.getAmount(), prevPayment.getValueAddedTax(), payment.getAmount(), payment.getValueAddedTax())) {
//            throw new Exception("");
//        }
//        payment.setPaymentCardInfo(prevPayment.getPaymentCardInfo());
//        payment.setInstallmentMonth(DEFAULT_INSTALLMENT_MONTH);
//        payment.setAmount(prevPayment.getAmount() - payment.getAmount());
//        payment.setValueAddedTax(prevPayment.getValueAddedTax() - payment.getValueAddedTax());
//    }

    /*
     * optional 데이터이므로 값을 받지 않은 경우, 자동계산 합니다.
     * 자동계산 수식 : 결제금액 / 11, 소수점이하 반올림
     * 결제금액이 1,000원일 경우, 자동계산된 부가가치세는 91원입니다.
     * 부가가치세는 결제금액보다 클 수 없습니다.
     * 결제금액이 1,000원일 때, 부가가치세는 0원일 수 있습니다
     */
    private int calculateValueAddedTax(Long amount, Integer valueAddedTax) throws CancelAmountException {
        if (valueAddedTax != null && amount > valueAddedTax) {
            return valueAddedTax;
        }
        if (valueAddedTax != null && amount < valueAddedTax) {
            throw new CancelAmountException();
        }
        return Math.round(amount / VALUE_ADDED_TAX_RATE);
    }

    private boolean validCancelAmount(long prevAmount, int prevTax, long amount, int tax) {
        return prevAmount >= amount && prevTax >= tax;
    }

    private Object convert(Object object, Class<?> type) {
        return modelMapper.map(object, type);
    }

    private String generateManagementNumber() {
        return RandomStringUtils.randomAlphanumeric(MAX_ID_LENGTH);
    }
}
