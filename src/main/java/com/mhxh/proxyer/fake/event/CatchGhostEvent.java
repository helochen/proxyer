package com.mhxh.proxyer.fake.event;

import org.springframework.context.ApplicationEvent;

public class CatchGhostEvent extends ApplicationEvent {

    public CatchGhostEvent(Object source) {
        super(source);
    }
}
