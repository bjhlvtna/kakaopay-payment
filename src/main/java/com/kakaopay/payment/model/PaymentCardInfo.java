package com.kakaopay.payment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class PaymentCardInfo {
    // 원거래 관리번호
    @Id
    private String id;
    // 암호화된 카드정보
    private String encryptCardInfo;
    // 생성일자
    private LocalDateTime createDateTime;
}
