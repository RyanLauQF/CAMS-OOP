package model;

import java.io.Serializable;

public class User implements Serializable {
    private final String name;
    private final String email;
    private final String userID;
    private final UserGroup faculty;

    private String password;

    public User(String name, String email, UserGroup userGroup){
        this.name = name;
        this.email = email;
        this.userID = email.substring(0, email.indexOf("@")); // set user id from email
        this.password = "password"; // default
        this.faculty = userGroup;
    }

    // ======================= GETTER AND SETTER FUNCTIONS =======================

    public void setPassword(String password){
        this.password = password;
    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public UserGroup getFaculty(){ return faculty;
    }

    public String getEmail(){
        return email;
    }

    public String getUserID(){
        return userID;
    }
}
