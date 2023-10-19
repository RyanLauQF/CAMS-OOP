package model;

import java.io.Serializable;

public class User implements Serializable {
    private final String name;
    private final String email;
    private final String userID;
    private String password;
    private final String faculty;

    public User(String name, String email, String faculty){
        this.name = name;
        this.email = email;
        this.userID = email.substring(0, email.indexOf("@")); // set user id from email
        this.password = "password"; // default
        this.faculty = faculty;
    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public String getFaculty(){
        return faculty;
    }

    public String getEmail(){
        return email;
    }

    public String getUserID(){
        return userID;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
