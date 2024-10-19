package com.example.springmodulith.eventaction;

import com.example.springmodulith.eventaction.action.Action;
import com.example.springmodulith.eventaction.action.CustomEventMarker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class Setup implements ApplicationRunner {
    private final EventActionRepository eventActionRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider(false);

        provider.addIncludeFilter(new AnnotationTypeFilter(CustomEventMarker.class));

        Set<BeanDefinition> beanDefs = provider.findCandidateComponents("com.example.springmodulith");

        Map<String, String> eventActionMap = new HashMap<>();

        for(BeanDefinition bd: beanDefs) {
            if(bd instanceof AnnotatedBeanDefinition) {
                Map<String, Object> annotAttributeMap = ((AnnotatedBeanDefinition)bd)
                        .getMetadata()
                        .getAnnotationAttributes(CustomEventMarker.class.getCanonicalName());

                eventActionMap.put(annotAttributeMap.get("eventAction").toString(), bd.getBeanClassName());
            }
        }

        List<EventAction> eventActionList = new ArrayList<>(eventActionMap.size());

        eventActionMap.forEach((k,v) -> {
            EventAction eventAction = new EventAction();
            Action action = Action.getActionByName(k);
            eventAction.setAction(action);
            eventAction.setEventCanonicalName(v);

            if(eventActionRepository.getEventActionByAction(action).isEmpty()) {
                eventActionList.add(eventAction);
            }
        });

        if(!eventActionList.isEmpty()) {
            eventActionRepository.saveAll(eventActionList);
        }
    }
}
