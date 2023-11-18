package controller;

import database.Database;
import model.CampCommMember;
import model.Student;
import model.User;

import java.util.HashMap;
import java.util.UUID;


/**
 * Controller class that manages user-related operations such as registration, password updates,
 * and user validation. This class interacts with the underlying database model objects to store and retrieve
 * user information based on view inputs.
 *
 * @author Ryan Lau
 * @version 1.0
 * @since 2023-11-14
 */
public class UserManager {

    /**
     * Reference to user data hashmap stored in database.
     */
    private static final HashMap<String, User> usersData = Database.USER_DATA;

    /**
     * Registers a student as a Camp Committee Member and adds them to the user database.
     *
     * @param student The student to be registered as a Camp Committee Member.
     * @param campID  The UUID of the camp to which the student is associated as a committee member.
     */
    public static void registerStudentAsCommMember(Student student, UUID campID){
        CampCommMember campCommMember = new CampCommMember(student, campID);
        addUser(campCommMember.getUserID(), campCommMember); // overwrites key of original student
    }

    /**
     * Displays information about all users (students and staff) in the user database.
     */
    public static void showUsers(){
        for(String userID : usersData.keySet()){
            User user = usersData.get(userID);
            System.out.print(user.getName());
            if(user instanceof Student){
                System.out.println(" student");
            }
            else{
                System.out.println(" staff");
            }
        }
    }

    /**
     * Updates the password of a given user.
     *
     * @param user     The user whose password needs to be updated.
     * @param password The new password.
     */
    public static void updatePassword(User user, String password){
        user.setPassword(password);
    }

    /**
     * Checks if a user with the given userID exists in the user database.
     *
     * @param userID The userID to check.
     * @return True if the user exists; false otherwise.
     */
    public static boolean containsUser(String userID){
        return usersData.containsKey(userID);
    }

    /**
     * Retrieves a user with the given userID from the user database.
     *
     * @param userID The userID of the user to retrieve.
     * @return The User object associated with the given userID.
     */
    public static User getUser(String userID){
        return usersData.get(userID);
    }

    /**
     * Validates the login credentials of a user.
     *
     * @param userID   The userID of the user.
     * @param password The password to validate.
     * @return true if the credentials are valid; false otherwise.
     */
    public static boolean validateUser(String userID, String password){
        User user = getUser(userID);
        return user.getPassword().equals(password);
    }

    /**
     * Adds a new user to the user database.
     *
     * @param userID The userID of the new user.
     * @param user   The User object to add to the database.
     */
    public static void addUser(String userID, User user){
        usersData.put(userID, user);
    }

    /**
     * Removes a user from the user database.
     *
     * @param userID The userID of the user to remove.
     */
    public static void removeUser(String userID){
        usersData.remove(userID);
    }
}
