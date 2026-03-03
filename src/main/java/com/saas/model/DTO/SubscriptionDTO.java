package com.saas.model.DTO;

import com.saas.model.domain.Tracking;
import com.saas.model.enumModels.SubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {

    private String username;
    private List<SubscriptionType> subscriptionTypeList;
}
