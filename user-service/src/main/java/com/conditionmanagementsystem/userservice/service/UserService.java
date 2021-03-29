package com.conditionmanagementsystem.userservice.service;

import com.conditionmanagementsystem.userservice.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByName(String userName);
    Boolean checkExistUser(User user);
    User saveUser(User user);
    Boolean deleteUserById(Long id);
}
