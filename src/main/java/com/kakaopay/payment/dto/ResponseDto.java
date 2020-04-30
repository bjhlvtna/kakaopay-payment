package com.kakaopay.payment.dto;

import lombok.Data;

public class ResponseDto {

    @Data
    public static class Payment {
    //- 관리번호 ( UUID, 20자리 )
        private String id;
    //- 추가 필드 ( optional )
        private String externalInfo;
    }
}
