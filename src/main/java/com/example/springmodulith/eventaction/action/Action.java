package com.example.springmodulith.eventaction.action;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Action {
    PAYMENT("P"), EMAIL("E"), COMPLETE_PAYMENT("CP");

    private final String code;

    public static Action getActionByName(final String name) {
        return Arrays.stream(Action.values())
                .filter(action -> action.name().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }
}
