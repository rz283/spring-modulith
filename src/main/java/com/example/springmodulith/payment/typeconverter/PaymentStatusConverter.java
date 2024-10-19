package com.example.springmodulith.payment.typeconverter;

import com.example.springmodulith.exception.ModulithException;
import com.example.springmodulith.order.type.Status;
import com.example.springmodulith.payment.type.PaymentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = true)
public class PaymentStatusConverter  implements AttributeConverter<PaymentStatus,String> {
    @Override
    public String convertToDatabaseColumn(PaymentStatus status) {
       if(status == null) throw new ModulithException ("Status can not be null");
       return status.getCode();
    }

    @Override
    public PaymentStatus convertToEntityAttribute(String code) {
        return Arrays.stream(PaymentStatus.values())
                .filter(s -> s.getCode().equalsIgnoreCase(code))
                .findFirst().orElseThrow(() -> new ModulithException("No status found"));
    }
}
