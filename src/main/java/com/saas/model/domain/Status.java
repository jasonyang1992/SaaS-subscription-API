package com.saas.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Status {

    private String workflow;
    private int statusCode;
    private String status;
    private String color;
}
