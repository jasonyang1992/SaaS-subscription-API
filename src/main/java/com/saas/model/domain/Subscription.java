package com.saas.model.domain;

import com.saas.model.enumModels.SubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Subscription")
public class Subscription {

    private String username;
    private List<SubscriptionType> subscriptionTypeList;
    private Tracking tracking;
}
