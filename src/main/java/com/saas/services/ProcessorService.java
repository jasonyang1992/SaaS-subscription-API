package com.saas.services;

import com.saas.model.domain.RequestEvent;

public interface ProcessorService {

    void process(RequestEvent requestEvent);
    String getAction();
}
