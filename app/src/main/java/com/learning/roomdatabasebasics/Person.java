package com.learning.roomdatabasebasics;

public class Person {

    //variables
    String name;

    String age;

    //empty Constructor
    public Person(){


    }

    //Constructor with arguments
    public Person(String name, String age) {
        this.name = name;
        this.age = age;
    }

    //getters and setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
