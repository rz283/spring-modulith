package com.example.springmodulith.eventaction.action;

import com.example.springmodulith.eventaction.EventAction;
import com.example.springmodulith.eventaction.EventActionRepository;
import com.example.springmodulith.exception.ModulithException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.modulith.events.CompletedEventPublications;
import org.springframework.modulith.events.IncompleteEventPublications;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepublishUncompletedEvent {
    private final EventActionRepository eventActionRepository;
    private final IncompleteEventPublications incompleteEventPublications;
    private final CompletedEventPublications completedEventPublications;
    private final Environment environment;

    public void republish(Action action) {
        Optional<EventAction> optEventAction = eventActionRepository.getEventActionByAction(action);

        if(optEventAction.isEmpty()) {
            log.info("No event action found for action: {}", action);
        }

        if(optEventAction.isPresent()) {
            log.info("Resubmit uncompleted events for action: {}", action);

            EventAction eventAction = optEventAction.get();

            try{
                Class<?> actionClass = Class.forName(eventAction.getEventCanonicalName());

                incompleteEventPublications.resubmitIncompletePublications((ep) -> ep.getEvent().getClass() == actionClass);

//                completedEventPublications.deletePublicationsOlderThan(Duration.ofHours(environment.getProperty("delete.event.duration", Long.class, 100L)));

            }catch (ClassNotFoundException e) {
                throw new ModulithException(e.getMessage());
            }
        }
    }

    public void republish(List<Action> actions) {
        actions.forEach(this::republish);
    }

//    @Scheduled(fixedRate = 10L, timeUnit = TimeUnit.MINUTES)
    public void republish() {
        log.info("Republish uncompleted events");

        for (Action action: Action.values()) {
            republish(action);
        }
    }

}
