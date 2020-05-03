package com.kakaopay.payment.model;

import com.kakaopay.payment.model.converter.EncryptCardInfoConverter;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_card_info")
public class PaymentCardInfo implements Serializable {

    private static final long serialVersionUID = 6517703321254679048L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    // 원거래 관리번호
    @Column(name = "payment_id", nullable = false, unique = true, length = 20, updatable = false)
    private String paymentID;
    // 암호화된 카드정보
    // TODO: class 화 toString 마스킹 처리
//    @Column(name = "encrypt_card_info", nullable = false, updatable = false)
    @Column(name = "encrypt_card_info", updatable = false)
    @Convert(converter = EncryptCardInfoConverter.class)
    private CardInfo cardInfo;
//    private EncryptCardInfo encryptCardInfo;
    // 생성일자
    @CreationTimestamp
    @Column(name = "create_time_at", nullable = false)
    private LocalDateTime createTimeAt;
}
