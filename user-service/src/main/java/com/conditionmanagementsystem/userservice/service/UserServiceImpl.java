package com.conditionmanagementsystem.userservice.service;

import com.conditionmanagementsystem.userservice.entity.User;
import com.conditionmanagementsystem.userservice.repository.UserDetailsRepository;
import com.conditionmanagementsystem.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

//    @Autowired
    private UserRepository userRepository;

/*    @Autowired
    private UserDetailsRepository userDetailsRepository;*/


    public UserServiceImpl(UserRepository userRepository){this.userRepository = userRepository;}

//    public UserServiceImpl(UserDetailsRepository userDetailsRepository){this.userDetailsRepository = userDetailsRepository;}

        @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User getUserByName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Boolean checkExistUser(User user){
        return userRepository.findByUserName(user.getUserName()) != null;
    }

    @Override
    public User saveUser(User user) {
        user.setActive(1);
        return userRepository.save(user);
    }

    @Override
    public Boolean deleteUserById(Long id) {
        if(id != null){
            userRepository.deleteById(id);
            return true;
        }
        else
            return false;
    }
}
