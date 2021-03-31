package com.conditionmanagementsystem.userservice.controller;

import com.conditionmanagementsystem.userservice.entity.User;
import com.conditionmanagementsystem.userservice.http.header.HeaderGenerator;
import com.conditionmanagementsystem.userservice.repository.UserRepository;
import com.conditionmanagementsystem.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OnboardController {
//    @Autowired
    private UserService userService;

//    @Autowired
    private HeaderGenerator headerGenerator;

public OnboardController(UserService userService, HeaderGenerator headerGenerator){
    this.userService = userService;
    this.headerGenerator = headerGenerator;}

    @PostMapping(value = "/registration")
    public ResponseEntity<User> addUser(@RequestBody User user, HttpServletRequest request) {
        if (user != null) {
            try {
                if (!(userService.checkExistUser(user))) {
                    userService.saveUser(user);
                    return new ResponseEntity<User>(
                            user,
                            headerGenerator.getHeadersForSuccessPostMethod(request, user.getId()),
                            HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<User>(headerGenerator.getHeadersForError(),
                            HttpStatus.CONFLICT);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else{
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);}
    }
}
