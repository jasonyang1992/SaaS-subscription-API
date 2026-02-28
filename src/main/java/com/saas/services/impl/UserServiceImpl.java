package com.saas.services.impl;

import com.saas.model.domain.User;
import com.saas.repository.UserRepository;
import com.saas.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public User createUser(User user) {
        var userExist = userRepository.findByUsername(user.getUsername()).isPresent();
        return userExist ? null : userRepository.save(user);
    }

    @Override
    public long updateUser(User user) {
        Query query = new Query(Criteria.where("username").is(user.getUsername()));

        Update update = new Update();
        update.set("password", user.getPassword());

        var response = mongoTemplate.updateFirst(query, update, User.class);
        return response.getModifiedCount();
    }

}
