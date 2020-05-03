package com.kakaopay.payment.common;

public class ValueAddedTaxUtils {
    private static final float VALUE_ADDED_TAX_RATE = 11F;
//    optional 데이터이므로 값을 받지 않은 경우, 자동계산 합니다.
//      자동계산 수식 : 결제금액 / 11, 소수점이하 반올림
//      결제금액이 1,000원일 경우, 자동계산된 부가가치세는 91원입니다.
//      부가가치세는 결제금액보다 클 수 없습니다.
//      결제금액이 1,000원일 때, 부가가치세는 0원일 수 있습니다
    public static Integer calculate(Long amount, Integer valueAddedTax) throws Exception {

        if (valueAddedTax != null && amount > valueAddedTax) {
            return valueAddedTax;
        }

        if (valueAddedTax != null && amount < valueAddedTax) {
            throw new Exception("");
        }

        return Math.round(amount / VALUE_ADDED_TAX_RATE);
    }
}
