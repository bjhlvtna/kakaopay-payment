package com.kakaopay.payment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class CardCompany {
    // 관리번호
    @Id
    private String id;
    // 약속된 string
    private String telegram;
    // 생성 일시
    private LocalDateTime createDateTime;
}
