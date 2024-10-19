package com.example.springmodulith.eventaction;

import com.example.springmodulith.eventaction.action.Action;
import com.example.springmodulith.eventaction.action.RepublishUncompletedEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;

@ApplicationModuleTest
class EventActionIntegrationTest {

    @Autowired
    RepublishUncompletedEvent republishUncompletedEvent;

    @Test
    void verifyModule() {

    }

    @Test
    void testUncompletedEvents() {
        republishUncompletedEvent.republish(Action.PAYMENT);
    }
}