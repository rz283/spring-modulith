package com.example.springmodulith.payment.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {

    INCOMPLETE("I"), COMPLETED("C"), FAILED("F");

    private final String code;
}
