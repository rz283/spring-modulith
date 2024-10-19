package com.example.springmodulith.payment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface PaymentRepository extends CrudRepository<Payment, Long> {
    Optional<Payment> getPaymentsByOrderId(String orderId);
}
