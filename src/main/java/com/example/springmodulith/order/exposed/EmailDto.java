package com.example.springmodulith.order.exposed;

import com.example.springmodulith.eventaction.action.Action;
import com.example.springmodulith.eventaction.action.CustomEventMarker;

@CustomEventMarker(eventAction = Action.EMAIL)
public record EmailDto(String email, String customerName, String orderIdentifier, long amount, boolean orderCompleted) {
}
