package com.conditionmanagementsystem.userservice.controller

import com.conditionmanagementsystem.userservice.entity.Diabetes
import com.conditionmanagementsystem.userservice.entity.DiabetesTypes
import com.conditionmanagementsystem.userservice.entity.User
import com.conditionmanagementsystem.userservice.entity.UserDetails
import com.conditionmanagementsystem.userservice.http.header.HeaderGenerator
import com.conditionmanagementsystem.userservice.service.UserService
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.sql.Date

class UserControllerSpec extends Specification {

    @Autowired
    private MockMvc mockMvc

    def UserControllerMock
    UserDetails userDetails1 = Stub()
    UserDetails userDetails= new UserDetails();

    User user2 = new User(2, "Bala", "Test123", 1, userDetails1)
    User user3 = new User(3, "Sri", "Test123", 1, userDetails1)


    void setup() {
        UserService userService = Mock()
        HeaderGenerator headerGenerator = Mock()
        UserControllerMock = new UserController(userService,headerGenerator)
        this.mockMvc = MockMvcBuilders.standaloneSetup(UserControllerMock).build()

        userDetails.setId(2)
        userDetails.setFirstName("Test")
        userDetails.setLastName("User")
        userDetails.setEmail("testuser@test.com")
        userDetails.setPatient("SELF" as Diabetes)
        /*Date myDate = new Date(2009,12,31)
        userDetails.setDiagonizedDate(Date)*/ //Date type
        userDetails.setDiabetesType([DiabetesTypes.TYPE1,DiabetesTypes.TYPE2])
        User user1 = new User(1, "Divya", "Test123", 1, userDetails)

    }

    def "GetAllUsers"() {

    }

    def "GetUserByName"() {
    }

    def "GetUserById - interactions"(){
        when:
        ResponseEntity<User> result = UserControllerMock.getUserById(2)

        then:
        1 * UserControllerMock.userService.getUserById(2) >> user2
        result.getBody().getUserName().equals("Bala")
        result.getStatusCodeValue()==200

    }
    def "GetUserById - Valid User"() {
        given:
        /*List<User> users = [user1,user2,user3]
        UserControllerMock.userService.getAllUsers >> users*/

//        userDetails.setUser(user1)
        UserControllerMock.userService.getUserById(1) >> user1

        when:
        def result= mockMvc.perform(MockMvcRequestBuilders.get("/users/1")).andReturn().response
        def content = new JsonSlurper().parseText(result.contentAsString)

        then:
        result.getStatus()==200
//        content.
        result.contentAsString == '{"id":1,"userName":"Divya","userPassword":"Test123","active":1,"userDetails":{"id":2,"firstName":"Test","lastName":"User","email":"testuser@test.com","patient":"SELF","diagonizedDate":null,"diabetesType":["TYPE1","TYPE2"]}}'
    }

    def " Should Delete User - Valid User"(){
        given:
        UserControllerMock.userService.getUserById(3) >> user3
        UserControllerMock.userService.deleteUserById(3) >> true

        when:
        def result= mockMvc.perform(MockMvcRequestBuilders.delete("/delete-user/3")).andReturn().response
//        def content = new JsonSlurper().parseText(result.contentAsString)

        then:
        result.getStatus()==200
        result.getContentAsString() == "User "+user3.getUserName()+" Deleted"
    }
}
