package com.example.blogapplication;

public class User {
    public String firstName,lastName,emailId,password;
    public User(){

    }
    public User(String fname, String lname,String email){
        this.firstName=fname;
        this.lastName=lname;
        this.emailId=email;
        this.password="aaa";
    }
}
