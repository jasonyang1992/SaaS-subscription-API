package com.saas.scheduler;

import com.saas.model.domain.Subscription;
import com.saas.model.reference.SubscriptionPrice;
import com.saas.repository.SubscriptionPriceRepository;
import com.saas.repository.SubscriptionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@Log4j2
public class BillingScheduler {

    @Value("${scheduler.midnight-task-cron}")
    private String cronExpression;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private SubscriptionPriceRepository subscriptionPriceRepository;

    @Scheduled(cron = "${scheduler.midnight-task-cron}")
    public void processBilling(){
        // TODO billing service
        System.out.println(cronExpression);

        CompletableFuture<List<Subscription>> subscriptionCompletableFuture = CompletableFuture.supplyAsync(() ->{
            log.info("Finding subscription data");
           return subscriptionRepository.findAll();
        }).exceptionally(ex -> {
            log.error("error finding subscriptions {}", ex.getMessage());
            return Collections.emptyList();
        });

        CompletableFuture<List<SubscriptionPrice>> subscriptionPriceCompletableFuture = CompletableFuture.supplyAsync(() ->{
            log.info("Finding subscription price data");
            return subscriptionPriceRepository.findAll();
        }).exceptionally(ex -> {
            log.error("error finding subscription prices {}", ex.getMessage());
            return Collections.emptyList();
        });

        var subscriptionData = subscriptionCompletableFuture.join();
        var subscriptionPriceData = subscriptionPriceCompletableFuture.join();

        log.info("{}, {}", subscriptionData, subscriptionPriceData);

        var subscriptionPriceMap =subscriptionPriceData.stream().collect(Collectors.toMap(SubscriptionPrice::getSubscriptionType,
                subscriptionPrice -> subscriptionPrice));

        HashMap<String, Integer> priceMap = new HashMap<>();

        log.info("Processing total subscription price");
        subscriptionData.forEach(subscription -> {
            int totalPrice = 0;

            for (int i = 0; i < subscription.getSubscriptionTypeList().size(); i++){
                var price = subscriptionPriceMap.get(subscription.getSubscriptionTypeList().get(i).getType()).getPrice();
                totalPrice = totalPrice + price;

                priceMap.put(subscription.getUsername(), totalPrice);
            }
        });

        log.info(priceMap);
    }
}
