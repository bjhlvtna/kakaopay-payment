package com.kakaopay.payment.repository;

import com.kakaopay.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "SELECT * FROM PAYMENT WHERE management_number = ?1 OR origin_management_number = ?1 ORDER BY create_time_at desc LIMIT 1", nativeQuery = true)
    public Payment findByLatestPayment(String managementNumber);
}
