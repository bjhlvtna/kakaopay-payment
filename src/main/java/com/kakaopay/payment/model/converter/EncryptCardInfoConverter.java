package com.kakaopay.payment.model.converter;

import com.kakaopay.payment.common.EncryptionUtils;
import com.kakaopay.payment.model.CardInfo;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

// TODO: 우아하게 리펙토링
@Converter
public class EncryptCardInfoConverter implements AttributeConverter<CardInfo, String> {

    private final static String CARD_INFO_DELIMITER = "|";

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(CardInfo cardInfo) {
        if (cardInfo == null) {
            throw new Exception("");
        }
        return EncryptionUtils.encrypt(cardInfo.toString());
    }

    @SneakyThrows
    @Override
    public CardInfo convertToEntityAttribute(String encryptCardInfo) {
        if (StringUtils.isEmpty(encryptCardInfo)) {
            throw new Exception("");
        }
        String[] tmp = StringUtils.split(EncryptionUtils.decrypt(encryptCardInfo), CARD_INFO_DELIMITER);
        return CardInfo.builder().cardNumber(tmp[0]).validity(tmp[1]).cvc(tmp[2]).build();
    }
}