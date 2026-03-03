package com.saas.services.impl;

import com.saas.model.domain.RequestEvent;
import com.saas.model.domain.User;
import com.saas.processor.factory.ProcessorFactory;
import com.saas.services.OrchestratorService;
import com.saas.utitly.StatusUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Service
@Log4j2
public class OrchestratorServiceImpl implements OrchestratorService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProcessorFactory processorFactory;

    @Override
    public RequestEvent process(Map<String, String> headers, Object data) {
        var requestEvent = buildRequestEvent(headers, data);

        processEvent(requestEvent);
        return requestEvent;
    }

    private void processEvent(RequestEvent requestEvent){
        var event = requestEvent.getHeaders().get("event");
        var action = requestEvent.getHeaders().get("action");
        log.info("Processing event : {}, action : {} requestId {}", event, action, requestEvent.getRequestId());

        switch (event){
            case "userEvent" -> processUserEvent(requestEvent);
            case "billingEvent" -> {
                // TODO paying bill
            }
            case "subscriptionEvent" -> {
                // TODO add subscriptions
            }
            default -> StatusUtil.invalidEventName(requestEvent);
        }
    }

    private void processUserEvent(RequestEvent requestEvent){
        var action = requestEvent.getHeaders().get("action");

        try {
            processorFactory.getProcessor(action).process(requestEvent);
        } catch (Exception e){
            log.warn("Invalid action {}, {}", action, e);
            StatusUtil.invalidActionName(requestEvent);
        }
    }

    private RequestEvent buildRequestEvent(Map<String, String> headers, Object data){
        log.info("Building request event");
        return RequestEvent.builder()
                .requestId(UUID.randomUUID().toString())
                .headers(headers)
                .data(objectMapper.writeValueAsString(data))
                .statusMap(new ArrayList<>())
                .build();
    }
}
