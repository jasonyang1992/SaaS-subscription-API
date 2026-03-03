package com.saas.processor.userProcessor;

import com.saas.mappers.UserMapper;
import com.saas.model.DTO.UserDTO;
import com.saas.model.domain.RequestEvent;
import com.saas.model.domain.Tracking;
import com.saas.model.domain.User;
import com.saas.model.enumModels.UserRole;
import com.saas.repository.UserRepository;
import com.saas.services.ProcessorService;
import com.saas.utitly.StatusUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

import static com.saas.utitly.ConstantUtil.EMAIL_PATTERN;

@Component
@Log4j2
public class CreateUserProcessor implements ProcessorService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void process(RequestEvent requestEvent) {
        var userDTO = objectMapper.readValue(requestEvent.getData(), UserDTO.class);
        var user = userMapper.toEntity(userDTO);
        var userExist = userRepository.findByUsername(user.getUsername()).isPresent();

        validateUserData(requestEvent, user);
        setUserRole(user);
        setTracking(user);

        if(requestEvent.getStatusMap().stream().anyMatch(status -> status.getStatusCode() == 400)){
            log.warn("User validation failed due to {}", requestEvent.getStatusMap().toString());
            return;
        }

        if(userExist){
            log.warn("User {} already exist, therefore not saving data", user.getUsername());
            StatusUtil.failedUserCreation(requestEvent);
        } else {
            log.info("Saving user data for request id {}", requestEvent.getRequestId());
            userRepository.save(user);
            StatusUtil.successfulUserCreation(requestEvent);
        }
    }

    private void validateUserData(RequestEvent requestEvent, User user){
        if(user.getUsername() == null || user.getUsername().isBlank()){
            StatusUtil.generateWorkflowStatus(requestEvent, getAction(), 400, "Invalid username", "Red");
        }

        if(user.getPassword() == null || user.getPassword().isBlank() || user.getPassword().length() < 5){
            StatusUtil.generateWorkflowStatus(requestEvent, getAction(), 400, "Invalid password or password length is less than 5", "Red");
        }

        if(user.getEmail() == null || user.getEmail().isBlank() || !EMAIL_PATTERN.matcher(user.getEmail()).matches()){
            StatusUtil.generateWorkflowStatus(requestEvent, getAction(), 400, "Invalid email address", "Red");
        }
    }

    private void setUserRole(User user){
        user.setUserRole(UserRole.customer);
    }

    private void setTracking(User user){
        var tracking = Tracking.builder()
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .updatedBy(user.getUsername())
                .build();
        user.setTracking(tracking);
    }

    @Override
    public String getAction() {
        return "CreateUser";
    }
}
