package com.conditionmanagementsystem.userservice.controller

import com.conditionmanagementsystem.userservice.entity.Diabetes
import com.conditionmanagementsystem.userservice.entity.DiabetesTypes
import com.conditionmanagementsystem.userservice.entity.User
import com.conditionmanagementsystem.userservice.entity.UserDetails
import com.conditionmanagementsystem.userservice.http.header.HeaderGenerator
import com.conditionmanagementsystem.userservice.repository.UserRepository
import com.conditionmanagementsystem.userservice.service.UserService
import com.conditionmanagementsystem.userservice.service.UserServiceImpl
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import java.sql.Date

/**
 * Unit test for the OnboardController.
 */
class OnboardControllerSpec extends Specification {

    @Autowired
    private MockMvc mockMvc

    def OnboardControllerMock
    UserDetails userDetails= new UserDetails();
    User user = new User(1,"Test_User","Test@User",1,userDetails)

    ObjectMapper mapper=new ObjectMapper();
    ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();

    void setup() {
        UserService userService = Mock()
        HeaderGenerator headerGenerator = Mock()
        OnboardControllerMock = new OnboardController(userService,headerGenerator)
        this.mockMvc = MockMvcBuilders.standaloneSetup(OnboardControllerMock).build()


        //    String jsonString=mapperwriteValueAsString(mapper.readValue(new File("src/test/java/com/conditionmanagementsystem/userservice/controller/RegisterUser.json" as File,Object.class)));

    }

    def "Check AddUser - 201"() {
        given:
        String requestJson = objectWriter.writeValueAsString(user);

        OnboardControllerMock.userService.checkExistUser(_ as User) >> false
        OnboardControllerMock.userService.saveUser(_ as User) >> user

        when:
        def result= mockMvc.perform(MockMvcRequestBuilders.post("/registration").content(requestJson).contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn().response

        then:
        result.getStatus()==201

    }

    def "Check AddUser - CONFLICT 409"() {
        given:
        String requestJson = objectWriter.writeValueAsString(user);

        OnboardControllerMock.userService.checkExistUser(_ as User) >> true
        OnboardControllerMock.userService.saveUser(_ as User) >> user

        when:
        def result= mockMvc.perform(MockMvcRequestBuilders.post("/registration").content(requestJson).contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn().response

        then:
        result.getStatus()==409

    }

    def "Check AddUser - INTERNAL SERVER ERROR 500"() {
        given:
        String requestJson = objectWriter.writeValueAsString(user);

        OnboardControllerMock.userService.checkExistUser(_ as User) >> false
        OnboardControllerMock.userService.saveUser(_ as User) >>{ User u1 ->
            if(u1.getUserName().equals("Test_User"))
                throw new IllegalArgumentException("Couldn't Save this User")
            else
                return u1
        }

        when:
        def result= mockMvc.perform(MockMvcRequestBuilders.post("/registration").content(requestJson).contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn().response

        then:
        result.getStatus()==500

    }
    def "Check AddUser - BAD REQUEST 400"() {
        given:
        User emptyUser=null;
        String requestJson = objectWriter.writeValueAsString(emptyUser);
/*
        OnboardControllerMock.userService.checkExistUser(_ as User) >> true
        OnboardControllerMock.userService.saveUser(_ as User) >> emptyUser*/

        when:
        def result= mockMvc.perform(MockMvcRequestBuilders.post("/registration").content(requestJson).contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn().response

        then:
        result.getStatus()==400

    }

}
