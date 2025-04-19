package com.abc.services;

import com.abc.entities.User;

public interface UserService {
    User getUserByUserName(String userName);
    boolean registerUser(User user);
    void updateUser(User user);
}