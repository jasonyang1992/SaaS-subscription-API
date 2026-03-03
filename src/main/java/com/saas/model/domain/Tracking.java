package com.saas.model.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Tracking {

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String updatedBy;
}
