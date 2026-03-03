package com.saas.repository;

import com.saas.model.reference.SubscriptionPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionPriceRepository extends MongoRepository<SubscriptionPrice, String> {

    SubscriptionPrice findBySubscriptionType(String subscriptionType);
}
