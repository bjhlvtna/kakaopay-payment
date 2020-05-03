package com.kakaopay.payment.model.generator;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class RandomIdGenerator implements IdentifierGenerator {

    private static final int MAX_ID_LENGTH = 20;

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return RandomStringUtils.randomAlphanumeric(MAX_ID_LENGTH);
    }
}

