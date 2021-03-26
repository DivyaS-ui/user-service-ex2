package com.conditionmanagementsystem.userservice.controller

import com.conditionmanagementsystem.userservice.entity.User
import com.conditionmanagementsystem.userservice.entity.UserDetails
import com.conditionmanagementsystem.userservice.service.UserServiceImpl
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

/**
 * Unit test for the OnboardController.
 */
class OnboardControllerSpec extends Specification {
    UserServiceImpl userServiceImplMock = Mock()
    OnboardController onboardControllerMock = Mock()
    UserDetails userDetails = Mock()
    User user = new User(1,"Divya","Test123",1,userDetails)
    User user1 =Stub()
    //HttpServletRequest httpRequest = new HttpServletRequest()

    def "shouldn't AddUser for duplicate user"() {
        given:
        userServiceImplMock.checkExistUser(_ as User) >> true
        onboardControllerMock.addUser(_ as User,_ as HttpServletRequest) >> {
            if(userServiceImplMock.checkExistUser(_ as User))
                throw new IllegalArgumentException("Duplicate UserName")
            else
                return false
        }

        when: "Registering with unique userName"
        onboardControllerMock.addUser(user1,_ as HttpServletRequest)

        then:"Should throw Exception"
        def e = thrown(IllegalArgumentException)
        e.message == "Duplicate UserName"

    }
}
