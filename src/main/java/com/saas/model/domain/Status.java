package com.saas.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Status {

    private String workflow;
    private int statusCode;
    private String status;
    private String color;
}
