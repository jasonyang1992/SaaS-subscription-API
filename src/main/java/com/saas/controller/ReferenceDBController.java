package com.saas.controller;

import com.saas.model.reference.SubscriptionPrice;
import com.saas.repository.SubscriptionPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ReferenceDBController {

    @Autowired
    private SubscriptionPriceRepository subscriptionPriceRepository;

    @QueryMapping
    @Cacheable(value = "subscriptions", key = "#subscriptionType")
    public SubscriptionPrice getSubscriptionType(@Argument String subscriptionType) {
        return subscriptionPriceRepository.findBySubscriptionType(subscriptionType);
    }
}
