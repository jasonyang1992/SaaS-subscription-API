package com.saas.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestEvent {

    private String requestId;
    private Map<String, String> headers;
    private String data;
    private List<Status> statusMap;
}
