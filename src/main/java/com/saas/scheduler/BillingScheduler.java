package com.saas.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BillingScheduler {

    @Value("${scheduler.midnight-task-cron}")
    private String cronExpression;

    @Scheduled(cron = "${scheduler.midnight-task-cron}")
    public void processBilling(){
        // TODO billing service
        System.out.println(cronExpression);
    }
}
