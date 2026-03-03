package com.saas.processor.userProcessor;

import com.saas.mappers.UserMapper;
import com.saas.model.DTO.UserDTO;
import com.saas.model.domain.RequestEvent;
import com.saas.model.domain.Tracking;
import com.saas.model.domain.User;
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
public class UpdateUserProcessor implements ProcessorService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void process(RequestEvent requestEvent) {
        var userDTO = objectMapper.readValue(requestEvent.getData(), UserDTO.class);
        var user = userMapper.toEntity(userDTO);

        validatePassword(user, requestEvent);

        if(requestEvent.getStatusMap().stream().anyMatch(status -> status.getStatusCode() == 400)){
            log.warn("User validation failed due to {}", requestEvent.getStatusMap().toString());
            return;
        }

        Query query = new Query(Criteria.where("username").is(user.getUsername()));

        Update update = new Update();
        update.set("password", user.getPassword());
        update.set("tracking.updateDate", LocalDateTime.now());

        var response = mongoTemplate.updateFirst(query, update, User.class);

        if(response.getModifiedCount() > 0){
            log.info("Updating user data for request id {}", requestEvent.getRequestId());
            StatusUtil.successfulUserUpdate(requestEvent);
        } else {
            log.warn("Failed to update user data for request id {}", requestEvent.getRequestId());
            StatusUtil.failedUserUpdate(requestEvent);
        }
    }

    private void validatePassword(User user, RequestEvent requestEvent){
        if(user.getPassword() == null || user.getPassword().isBlank() || user.getPassword().length() < 5){
            StatusUtil.generateWorkflowStatus(requestEvent, getAction(), 400, "Invalid password or password length is less than 5", "Red");
        }
    }

    @Override
    public String getAction() {
        return "UpdateUser";
    }
}
