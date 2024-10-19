package com.example.springmodulith.email;

import com.example.springmodulith.order.exposed.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailService {
    @ApplicationModuleListener
    void sendEmail(final EmailDto emailDto) {
        log.info("Email properties received: {}", emailDto);

        if(!emailDto.orderCompleted()) {
            String body = "Dear " + emailDto.customerName() + ", \n" +
                    "An order was initialized with id " + emailDto.orderIdentifier() +  " and total some of: N" +
                    emailDto.amount()/100;
            log.info("initial Email order details: {}", body);
        }else{
            String body = "Dear " + emailDto.customerName() + ", \n" +
                    "Your order with id " + emailDto.orderIdentifier() +  " and total some of: N" +
                    emailDto.amount()/100 + " was completed successfully.";
            log.info("Email completion details: {}", body);
        }

        log.info("Email send to {}", emailDto.email());
    }
}
