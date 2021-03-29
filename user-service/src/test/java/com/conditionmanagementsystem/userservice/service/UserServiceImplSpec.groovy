package com.conditionmanagementsystem.userservice.service

import com.conditionmanagementsystem.userservice.entity.UserDetails
import com.conditionmanagementsystem.userservice.entity.User
import com.conditionmanagementsystem.userservice.repository.UserRepository
import spock.lang.Specification
import spock.lang.Unroll

class UserServiceImplSpec extends Specification {

        UserDetails userDetails = Stub()

        User user1 = new User(1, "Divya", "Test123", 1, userDetails)
        User user2 = new User(2, "Bala", "Test123", 1, userDetails)
        User user3 = new User(3, "Sri", "Test123", 1, userDetails)
        /*User user3 = new User(4, "Bala", "Test@123", 1, userDetails)
        User user4 = new User(5, "Sri", "Test@123", 1, userDetails)*/

//        UserServiceImpl userServiceStub = Mock()
    def UserServiceMock

    void setup() {
        UserServiceMock = new UserServiceImpl()
        UserServiceMock.userRepository= Mock(UserRepository)
    }

    def "Check get all users"(){
       /* given:
        UserServiceMock.userRepository.findAll() >> [user1,user2,user3]*/

        when:
        def users = UserServiceMock.getAllUsers()

        then:
        1*UserServiceMock.userRepository.findAll() >> [user1,user2,user3]
        users == [user1,user2,user3]

    }

    def "Check get user by Id"(){
         given:
//         UserServiceMock.userRepository.getOne(1) >> user1
         UserServiceMock.userRepository.getOne(_ as Long) >> { Long userId -> // if we change the data type it will fail
             if(userId == 1)
                 return user1
             else
                 throw new IllegalArgumentException("UserId not found")
         }

        when:
        def resultUser = UserServiceMock.getUserById(1)

        then:
        resultUser == user1

        when:
        def resultUser1 = UserServiceMock.getUserById(2)

        then:
        resultUser1 = thrown(IllegalArgumentException)
        resultUser1.message == "UserId not found"
    }

    def "Check get user by Name"(){
        given:
//         UserServiceMock.userRepository.getOne(1) >> user1
        UserServiceMock.userRepository.findByUserName(_ as String) >> { String str ->
            if(str.equals("Divya"))
                return user1
            else
                throw new MissingMethodException("UserName Not found", User,null)
        }

        when:
        def resultUser = UserServiceMock.getUserByName("Divya")

        then:
        resultUser == user1
        resultUser.getUserName().equals("Divya")

        when:
        def resultUser1 = UserServiceMock.getUserById("Sri")

        then:
        thrown(MissingMethodException)
    }

    @Unroll
    def "CheckExistUser for duplicate Username and unique"() {

        given:"If UserName 'Divya' present in repository"
        User user5 = new User()
        user5.setUserName(userName)
      //  UserServiceMock.userRepository.findByUserName("Divya") >> user6
        UserServiceMock.userRepository.findByUserName(_ as String) >> { String str ->
            if(str.equals("Divya"))
                return user1
            else
                return null
        }

        when:"Checking the user name exists or not"
        def res=UserServiceMock.checkExistUser(user5)

        then:
        UserServiceMock.checkExistUser(user5) >> expected
        res== expected

        where:
        userName | expected
        "Divya"  | true
        "Bala"   | false
        "Sri"    | false
    }

    def "Should return a user"(){

        given:
        UserServiceMock.userRepository.save(user1) >> user1
        UserServiceMock.userRepository.save(user2) >> user2

        when:
        def result= UserServiceMock.userRepository.save(user1)

        then:
        UserServiceMock.userRepository.save(user1)==user1
        result.getUserName().equals("Divya")

        when:
        User result1= UserServiceMock.userRepository.save(user2)

        then:
        result1==user2
    }

    def "Save User interaction"(){

        when:
        UserServiceMock.saveUser(user1)

        then:
        1 * UserServiceMock.userRepository.save(user1)

    }

    def "Check interactions - Check Exist User"(){
        when:
        UserServiceMock.checkExistUser(user1)

        then:
        1 * UserServiceMock.userRepository.findByUserName(_)

    }

    /*@Unroll
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


    }*/
}
