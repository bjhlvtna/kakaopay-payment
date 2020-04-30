package com.kakaopay.payment.dto;

import lombok.Data;

public class RequestDto {

    @Data
    public static class Payment {
    //- 카드번호 ( 10~16 숫자)
        private Integer cardNumber;
    //- 유효기간(4자리 숫자, mmyy)
        private Integer validity;
    //- cvs(3자리 숫자)
        private Integer cvc;
    //- 할부개월수: 0(일시불), 1~12
        private Integer installmentMonth;
    //- 결재금액 ( 100월 이상, 10억 이하, 숫자)
        private Long amount;
    //- 부가가치세 ( optional )
        private Long valueAddedTax;
    }
}
