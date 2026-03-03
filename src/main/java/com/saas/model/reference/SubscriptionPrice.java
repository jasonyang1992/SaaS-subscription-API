package com.saas.model.reference;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document("SubscriptionPriceReference")
public class SubscriptionPrice {

    @Id
    private String _id;
    private String subscriptionType;
    private int price;
}
