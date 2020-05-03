package com.kakaopay.payment.dto;

import com.kakaopay.payment.common.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ResponseDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payment {
    //- 관리번호 ( UUID, 20자리 )
        // TODO: response validation check
        private String paymentId;

        private String cardNumber;
        private String validity;
        private String cvc;

        private PaymentType paymentType;

        private Long amount;
    //- 추가 필드 ( optional )
        private String externalInfo;
    }
}
