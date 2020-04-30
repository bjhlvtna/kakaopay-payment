package com.kakaopay.payment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    // 관리번호
    @Id
    private String id;
    // 데이터구분 ( 결제 / 취소 ) 승인(PAYMENT), 취소(CANCEL)
    private String paymentType;
    // 할부개월수
    private Integer installmentMonth;
    // 거래금액
    private Long amount;
    // 부가가치세
    private Long valueAddedTax;
    // 원거래관리번호 (FK)
    // manyToOne
    private String paymentId;
    // 생성일자
    @CreationTimestamp
    private LocalDateTime createTimeAt;
}
