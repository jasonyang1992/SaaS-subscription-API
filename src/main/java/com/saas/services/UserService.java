package com.saas.services;

import com.saas.model.domain.User;

public interface UserService {

    User createUser(User user);
    long updateUser(User user);
}
