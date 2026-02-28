package com.saas.services;

import com.saas.model.domain.RequestEvent;

import java.util.Map;

public interface OrchestratorService {

    RequestEvent process(Map<String, String> headers, Object data);
}
