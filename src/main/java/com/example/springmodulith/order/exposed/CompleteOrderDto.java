package com.example.springmodulith.order.exposed;

import com.example.springmodulith.eventaction.action.Action;
import com.example.springmodulith.eventaction.action.CustomEventMarker;
import jakarta.validation.constraints.NotBlank;
import org.jmolecules.event.types.DomainEvent;

@CustomEventMarker(eventAction = Action.COMPLETE_PAYMENT)
public record CompleteOrderDto(
        @NotBlank(message = "OrderIdentifier is required")
        String orderIdentifier,
        boolean paymentComplete
) implements DomainEvent { }
