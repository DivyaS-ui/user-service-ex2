package com.conditionmanagementsystem.userservice.controller;

import com.conditionmanagementsystem.userservice.entity.User;
import com.conditionmanagementsystem.userservice.http.header.HeaderGenerator;
import com.conditionmanagementsystem.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
//    @Autowired
    private UserService userService;

//    @Autowired
    private HeaderGenerator headerGenerator;

    public UserController(UserService userService, HeaderGenerator headerGenerator){
        this.userService = userService;
        this.headerGenerator = headerGenerator;}

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users =  userService.getAllUsers();
        if(!users.isEmpty()) {
            return new ResponseEntity<List<User>>(
                    users,
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK);
        }
        return new ResponseEntity<List<User>>(
                headerGenerator.getHeadersForError(),
                HttpStatus.NOT_FOUND);
    }

    @GetMapping (value = "/users", params = "name")
    public ResponseEntity<User> getUserByName(@RequestParam("name") String userName){
        User user = userService.getUserByName(userName);
        if(user != null) {
            return new ResponseEntity<User>(
                    user,
                    headerGenerator.
                            getHeadersForSuccessGetMethod(),
                    HttpStatus.OK);
        }
        return new ResponseEntity<User>(
                headerGenerator.getHeadersForError(),
                HttpStatus.NOT_FOUND);
    }

    @GetMapping (value = "/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
        User user = userService.getUserById(id);
        if(user != null) {
            return new ResponseEntity<User>(
                    user,
                    headerGenerator.
                            getHeadersForSuccessGetMethod(),
                    HttpStatus.OK);
        }
        return new ResponseEntity<User>(
                headerGenerator.getHeadersForError(),
                HttpStatus.NOT_FOUND);
    }

    @DeleteMapping (value = "/delete-user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id){
        User user = userService.getUserById(id);
        if(user != null) {
            Boolean userDeleteFlag =userService.deleteUserById(id);
            System.out.println("Delete Flag is - "+userDeleteFlag);
            if(userDeleteFlag){
                return new ResponseEntity<String>(
                        "User "+user.getUserName()+" Deleted",
                        headerGenerator.
                                getHeadersForSuccessGetMethod(),
                        HttpStatus.OK);
            }
            else{
                return new ResponseEntity<String>(
                        "Cannot Perform Delete - Internal Server Error",
                        headerGenerator.getHeadersForError(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<String>(
                "User Not Found",
                headerGenerator.getHeadersForError(),
                HttpStatus.NOT_FOUND);
    }
}
