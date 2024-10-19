package com.example.springmodulith.eventaction;

import com.example.springmodulith.eventaction.action.Action;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(indexes = {
        @Index(name = "actionIdx", columnList = "action")
})
public class EventAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2, nullable = false)
    private Action action;

    @Column(name = "event_can", nullable = false)
    private String eventCanonicalName;
}
