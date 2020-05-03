package com.kakaopay.payment.configuration;

import com.kakaopay.payment.common.PaymentType;
import com.kakaopay.payment.dto.RequestDto;
import com.kakaopay.payment.dto.ResponseDto;
import com.kakaopay.payment.model.CardInfo;
import com.kakaopay.payment.model.Payment;
import com.kakaopay.payment.model.PaymentCardInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.omg.CORBA.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfiguration {



    @Bean(name = "modelMapper")
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(generateRequestPaymentDtoMapping());
        modelMapper.addMappings(generateRequestCancelDtoMapping());
        modelMapper.addMappings(generateResponseDtoMapping());
        return modelMapper;
    }

    private PropertyMap<RequestDto.Payment, Payment> generateRequestPaymentDtoMapping() {
        return new PropertyMap<RequestDto.Payment, Payment>() {
            protected void configure() {
                map().setPaymentType(PaymentType.PAYMENT);
            }
        };
    }

    private PropertyMap<RequestDto.Cancel, Payment> generateRequestCancelDtoMapping() {
        return new PropertyMap<RequestDto.Cancel, Payment>() {
            protected void configure() {
                map().setPaymentType(PaymentType.CANCEL);
                map().setOriginManagementNumber(source.getManagementNumber());
                map().setManagementNumber(StringUtils.EMPTY);
            }
        };
    }

    private PropertyMap<Payment, ResponseDto.Payment> generateResponseDtoMapping() {
        return new PropertyMap<Payment, ResponseDto.Payment>() {
            protected void configure() {
                map().setCardNumber(source.getPaymentCardInfo().getCardInfo().getCardNumber());
                map().setValidity(source.getPaymentCardInfo().getCardInfo().getValidity());
                map().setCvc(source.getPaymentCardInfo().getCardInfo().getCvc());
            }
        };
    }
}
