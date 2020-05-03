package com.kakaopay.payment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kakaopay.payment.common.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

// TODO: builder 제거 mapper 로 변경
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    // 관리번호
    @Column(name = "management_number", nullable = false, unique = true, length = 20, updatable = false)
    private String managementNumber;
    // 원 거래 관리 번호
    @Column(name = "origin_management_number", updatable = false)
    private String originManagementNumber;
    // 데이터구분 ( 결제 / 취소 ) 승인(PAYMENT), 취소(CANCEL)
    @Column(name = "payment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    // 할부개월수
    @Column(name = "installment_month", nullable = false)
    private Integer installmentMonth;
    // 거래금액
    @Column(name = "amount", nullable = false)
    private Long amount;
    // 부가가치세
    // TODO: not null
    @Column(name = "value_added_tax", nullable = true)
    private Integer valueAddedTax;
    // 원거래관리번호 (FK)
    @ManyToOne(targetEntity=PaymentCardInfo.class, fetch=FetchType.LAZY)
    @JoinColumn(referencedColumnName = "payment_id")
    private PaymentCardInfo paymentCardInfo;
    // 생성일자
    @CreationTimestamp
    @Column(name = "create_time_at", nullable = false)
    private LocalDateTime createTimeAt;

    public String getOriginManagementNumber() {
        return StringUtils.isEmpty(this.originManagementNumber) ? StringUtils.EMPTY : this.originManagementNumber;
    }
}
