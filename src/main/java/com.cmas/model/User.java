package com.cmas.model;

import java.io.Serializable;


/**
 * Abstract model class representing a user and manages user-related operations.
 *
 * @author Ryan Lau
 * @version 1.0
 * @since 2023-11-18
 */
public abstract class User implements Serializable {
    private final String name;
    private final String email;
    private final String userID;
    private final UserGroup faculty;

    private String password;

    /**
     * Constructs a User object with the specified parameters.
     *
     * @param name      The name of the user.
     * @param email     The email of the user.
     * @param userGroup The user group to which the user belongs.
     */
    public User(String name, String email, UserGroup userGroup){
        this.name = name;
        this.email = email;
        this.userID = email.substring(0, email.indexOf("@")); // set user id from email
        this.password = "password"; // default
        this.faculty = userGroup;
    }

    // ======================= GETTER AND SETTER FUNCTIONS =======================

    /**
     * Sets the password for the user.
     *
     * @param password The new password for the user.
     */
    public void setPassword(String password){
        this.password = password;
    }


    /**
     * Gets the name of the user.
     *
     * @return The name of the user.
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword(){
        return password;
    }

    /**
     * Gets the user group to which the user belongs.
     *
     * @return The user group of the user.
     */
    public UserGroup getFaculty(){ return faculty;
    }

    /**
     * Gets the email of the user.
     *
     * @return The email of the user.
     */
    public String getEmail(){
        return email;
    }


    /**
     * Gets the user ID of the user.
     *
     * @return The user ID of the user.
     */
    public String getUserID(){
        return userID;
    }
}
