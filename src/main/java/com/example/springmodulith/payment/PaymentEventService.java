package com.example.springmodulith.payment;

import com.example.springmodulith.order.exposed.CompleteOrderDto;
import com.example.springmodulith.order.exposed.OrderPaymentDto;
import com.example.springmodulith.payment.type.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentEventService {

    private final PaymentRepository paymentRepository;

    @ApplicationModuleListener
    void on(final OrderPaymentDto orderPaymentDto) {
        log.info("Order payment received in listener: {}", orderPaymentDto);

        Payment payment = new Payment();
        payment.setOrderId(orderPaymentDto.orderId());
        payment.setAmount(orderPaymentDto.amount());
        paymentRepository.save(payment);
    }

    @ApplicationModuleListener
    void on(final CompleteOrderDto completeOrderDto) {
        log.info("Complete order received in listener: {}", completeOrderDto);

        Optional<Payment> paymentOptional = paymentRepository.getPaymentsByOrderId(completeOrderDto.orderIdentifier());

        if(paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            payment.setStatus(PaymentStatus.COMPLETED);
            paymentRepository.save(payment);
        }
    }
}
