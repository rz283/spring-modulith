package com.example.springmodulith.order.exposed;

import com.example.springmodulith.eventaction.action.Action;
import com.example.springmodulith.eventaction.action.CustomEventMarker;
import org.jmolecules.event.types.DomainEvent;

@CustomEventMarker(eventAction = Action.PAYMENT)
public record OrderPaymentDto(String orderId, long amount) implements DomainEvent {}
