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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import java.sql.Date

/**
 * Unit test for the OnboardController.
 */
class OnboardControllerSpec extends Specification {
    UserServiceImpl userServiceImplMock = Mock()
    OnboardController onboardControllerMock = Mock()


    User user1 =Stub()
    //HttpServletRequest httpRequest = new HttpServletRequest()
    @Autowired
    private MockMvc mockMvc

    def OnboardControllerMock1

    void setup() {
        OnboardControllerMock1 = new OnboardController()
        OnboardControllerMock1.userService= Mock(UserService)
        OnboardControllerMock1.headerGenerator= Mock(HeaderGenerator)
    }

    def "Check AddUser"() {
        given:
        UserDetails userDetails= new UserDetails();
        userDetails.setFirstName("Test")
        userDetails.setLastName("User")
        userDetails.setEmail("testuser@test.com")
        userDetails.setPatient("SELF" as Diabetes)
        userDetails.setDiagonizedDate("2009-12-31" as Date)
        userDetails.setDiabetesType(["TYPE1", "TYPE2"] as List<DiabetesTypes>)

        User user = new User(1,"Test_User","Test@User",1,userDetails)

       /* ObjectMapper mapper=new ObjectMapper();
        String jsonString=mapperwriteValueAsString(mapper.readValue(new File("src/test/java/com/conditionmanagementsystem/userservice/controller/RegisterUser.json" as File,Object.class)));

*/
    }
   /* def "shouldn't AddUser for duplicate user"() {
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

    }*/
}
