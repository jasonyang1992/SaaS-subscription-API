package com.saas.processor.subscriptionProcessor;

import com.saas.mappers.SubscriptionMapper;
import com.saas.model.DTO.SubscriptionDTO;
import com.saas.model.domain.RequestEvent;
import com.saas.model.domain.Subscription;
import com.saas.model.domain.Tracking;
import com.saas.model.domain.User;
import com.saas.repository.SubscriptionRepository;
import com.saas.repository.UserRepository;
import com.saas.services.ProcessorService;
import com.saas.utitly.StatusUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Component
@Log4j2
public class SubscriptionProcessor implements ProcessorService {

    @Autowired
    private SubscriptionMapper subscriptionMapper;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void process(RequestEvent requestEvent) {
        var subscriptionDTO = objectMapper.readValue(requestEvent.getData(), SubscriptionDTO.class);
        var userAccountExist = userRepository.findByUsername(subscriptionDTO.getUsername()).isPresent();
        if(!userAccountExist){
            StatusUtil.generateWorkflowStatus(requestEvent, getAction(), 400, "User account does not exist. Create user account first", "Red");
            log.warn("User account not found. {}", requestEvent.getStatusMap());
            return;
        }
        var subscriptionExist = subscriptionRepository.findByUsername(subscriptionDTO.getUsername()).isPresent();

        var subscription = subscriptionMapper.toEntity(subscriptionDTO);

        if (subscriptionExist){
            Query query = new Query(Criteria.where("username").is(subscription.getUsername()));

            Update update = new Update();
            update.set("subscriptionTypeList", subscription.getSubscriptionTypeList());
            update.set("tracking.updateDate", LocalDateTime.now());

            var response = mongoTemplate.updateFirst(query, update, Subscription.class);

            if(response.getModifiedCount() > 0){
                log.info("Updating user data for request id {}", requestEvent.getRequestId());
                StatusUtil.generateWorkflowStatus(requestEvent, getAction(), 200, "Successfully updated subscription", "Green");
            } else {
                log.warn("Failed to update user data for request id {}", requestEvent.getRequestId());
                StatusUtil.generateWorkflowStatus(requestEvent, getAction(), 400, "Failed to updated subscription", "Red");
            }

        } else {
            createTracking(subscription);
            subscriptionRepository.save(subscription);
            StatusUtil.generateWorkflowStatus(requestEvent, getAction(), 200, "Successfully added subscription", "Green");
            log.info("Successfully saved {}", requestEvent.getStatusMap());
        }
    }

    private void createTracking(Subscription subscription){
        var tracking = Tracking.builder()
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .updatedBy(subscription.getUsername())
                .build();
        subscription.setTracking(tracking);
    }

    @Override
    public String getAction() {
        return "Subscription";
    }
}
