package com.example.springmodulith.payment;

import com.example.springmodulith.payment.type.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@ToString
@Entity
@Table
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;
    private long amount;
    private Timestamp paymentDate = Timestamp.from(Instant.now());
    private PaymentStatus status = PaymentStatus.INCOMPLETE;
}
