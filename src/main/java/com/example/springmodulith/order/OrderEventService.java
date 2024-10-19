package com.example.springmodulith.order;

import com.example.springmodulith.order.exposed.CompleteOrderDto;
import com.example.springmodulith.order.exposed.EmailDto;
import com.example.springmodulith.order.exposed.OrderPaymentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventService {

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void completeOrder(final OrderPaymentDto orderPaymentDto, EmailDto emailDto) {
        log.info("Completing order payment with details {}", orderPaymentDto);
        eventPublisher.publishEvent(orderPaymentDto);

        log.info("Sending email for order {}", emailDto);
        eventPublisher.publishEvent(emailDto);
    }

    @Transactional
    public void completePayment(CompleteOrderDto completeOrderDto, EmailDto emailDto) {
        log.info("Attempting to complete payment {}", completeOrderDto);
        eventPublisher.publishEvent(completeOrderDto);

        log.info("Completed payment email {}", emailDto);
        eventPublisher.publishEvent(emailDto);
    }
}
