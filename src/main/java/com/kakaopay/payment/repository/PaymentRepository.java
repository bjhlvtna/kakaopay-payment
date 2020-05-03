package com.kakaopay.payment.repository;

import com.kakaopay.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // TODO: order by created time at
//    public Payment findByManagementNumber(String managementNumber);
//    public Payment findByManagementNumberOrderByCreateTimeAt(String managementNumber);
//    public Payment findByManagementNumberOrOriginManagementNumberOrderByCreateTimeAt(String managementNumber);
    @Query(value = "SELECT * FROM PAYMENT WHERE management_number = ?1 OR origin_management_number = ?1 ORDER BY create_time_at desc LIMIT 1", nativeQuery = true)
    public Payment findByLatelyPayment(String managementNumber);
}
