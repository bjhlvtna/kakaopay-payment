package com.kakaopay.payment.component;

import com.kakaopay.payment.common.TelegramFormatter;
import com.kakaopay.payment.model.CardCompany;
import com.kakaopay.payment.model.Payment;
import com.kakaopay.payment.repository.CardCompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TelegramTransfer {

    private CardCompanyRepository cardCompanyRepository;

    @Autowired
    public TelegramTransfer(CardCompanyRepository cardCompanyRepository) {
        this.cardCompanyRepository = cardCompanyRepository;
    }

    // TODO: 결과에 따라 Exception throw
    // 무조건 카드사에 전문 송신은 성공으로 가정
    public boolean transfer(Payment payment) throws Exception {
        log.info("[TELEGRAM] {}", generateTelegram(payment));
        this.cardCompanyRepository.save(generateTelegram(payment));
        return true;
    }

    private CardCompany generateTelegram(Payment payment) throws Exception {
        return TelegramFormatter.format(payment);
    }
}
