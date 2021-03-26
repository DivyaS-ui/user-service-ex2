package com.conditionmanagementsystem.userservice.service

import com.conditionmanagementsystem.userservice.entity.UserDetails
import com.conditionmanagementsystem.userservice.entity.User
import com.conditionmanagementsystem.userservice.repository.UserRepository
import spock.lang.Specification
import spock.lang.Unroll

class UserServiceImplSpec extends Specification {

//        def UserServiceMock = Mock(UserServiceImpl)
        UserDetails userDetails = Stub()

        User user = new User(1, "Divya", "Test123", 1, userDetails)
        User user1 = new User(2, "Bala", "Test123", 1, userDetails)
        User user2 = new User(3, "Divya", "Test123", 1, userDetails)
        User user3 = new User(4, "Bala", "Test@123", 1, userDetails)
        User user4 = new User(5, "Sri", "Test@123", 1, userDetails)

        UserServiceImpl userServiceStub = Mock()
    def UserServiceMock

    void setup() {
        UserServiceMock = new UserServiceImpl()
        UserServiceMock.userRepository= Mock(UserRepository)
    }

  /*  def "Check"(){ //need to check with Spy
        given:
        User user1=Stub()
        User user2=Stub()
        user1.getUserName() >> "Divya"
        user2.setUserName("Divya")

        when:
        UserServiceMock.getAllUsers()
        UserServiceMock.checkExistUser(user2)

        then:
        1*UserServiceMock.getAllUsers() >> [user1]

        then:
        UserServiceMock.checkExistUser(user2)
      //  UserServiceMock.checkExistUser(user2)

    }*/

    @Unroll
    def "CheckExistUser"() {

        given:
//        User user5 = new User(5,userName,"Tt@123",1,userDetails)
        User user5 = new User()
        user5.setUserName(userName)

        when:
        def res=UserServiceMock.checkExistUser(user5)

        then:
        UserServiceMock.checkExistUser(user5) >> expected
        res== expected

        where:
        userName | expected
        "Divya"  | false
        "Bala"   | false
        "Sri"    | false
    }

    def "Should return a user"(){

        given:
        UserServiceMock.userRepository.save(user) >> user
        UserServiceMock.userRepository.save(user1) >> user1

        when:
        def result= UserServiceMock.userRepository.save(user)

        then:
        UserServiceMock.userRepository.save(user)==user
        result.getUserName().equals("Divya")

        when:
        User result1= UserServiceMock.userRepository.save(user1)

        then:
        result1==user1
    }

    def "Save User interaction"(){

        when:
        UserServiceMock.saveUser(user)

        then:
        1 * UserServiceMock.userRepository.save(user)

    }

    def "Check Exist User duplicate"(){
        given:
        UserServiceMock.checkExistUser(_ as User) >> { User u1 ->
            if (UserServiceMock.userRepository.findByUserName(_)==null)
                return true
            else
                return false
        }

        when:
        def res=UserServiceMock.checkExistUser(user)

        then:
        res==true
    }

    def "Check User interaction"(){
        when:
        UserServiceMock.checkExistUser(user)

        then:
        1 * UserServiceMock.userRepository.findByUserName(_)
//        1 * user.getUserName()

    }

    @Unroll
    def "Check Exist User method returning true for the duplicate UserName"(){
        given:
        userServiceStub.checkExistUser(_ as User) >> { User u1 ->
            def UserName =u1.getUserName()
            if(UserName.equals(user.getUserName())|(UserName.equals(user1.getUserName())))
                throw new IllegalArgumentException("Duplicate UserName")
            else
                return true
        }

        when:
        userServiceStub.checkExistUser(user2)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Duplicate UserName"

        when:
        userServiceStub.checkExistUser(user3)

        then:
        def e1 = thrown(IllegalArgumentException)
        e1.message == "Duplicate UserName"

        when:
        userServiceStub.checkExistUser(user4)

        then:
        notThrown(IllegalArgumentException)


    }
}
