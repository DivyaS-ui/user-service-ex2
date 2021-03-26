package com.conditionmanagementsystem.userservice.entity

import spock.lang.Specification

class UserSpec extends Specification {

    User user = new User()
    UserDetails details1 = new UserDetails();

    def "GetId"() {
        when:
        user.setId(userid)

        then:
        user.getId()

        where:
        userid | expected
        7      | 7
        8      | 8
        10     | 10
        999999 | 999999

    }

    def "GetUserName"() {
        when:
        user.setUserName(userName)

        then:
        user.getUserName().equals(expected)

        where:
        userName    | expected
        "Test_User" | "Test_User"
        "Divya1"    | "Divya1"
    }

}

  /*  def "GetActive"() {
        given:
        List<DiabetesTypes> diabetesType = new ArrayList<DiabetesTypes>();
        User user1=new User();
        UserDetails details1 = new UserDetails();


        when:
        diabetesType.add("TYPE1" as DiabetesTypes);
        UserDetails details1 = new UserDetails(id=8, firstName="Divya",lastName="Sri",email="Divya@test.com",patient="SELF",diagonizedDate="2021-02-02",diabetesType,user1)
        UserDetails details2 = new UserDetails()
        User user2 = new User(id=7, userName="Divya", userPassword="fh@#fdf", active=0, details1)
        user.setActive(activeValue)

        then:
        user.getActive()

        activeValue  | expected
        1            | 1
      //  0            | 0
    }*/




