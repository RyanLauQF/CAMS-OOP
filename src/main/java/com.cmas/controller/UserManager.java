package com.cmas.controller;

import com.cmas.database.Database;
import com.cmas.model.CampCommMember;
import com.cmas.model.Student;
import com.cmas.model.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
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
    private static final HashMap<String, User> usersData = Database.getUserData();

    /**
     * Registers a student as a Camp Committee Member and adds them to the user database.
     *
     * @param student The student to be registered as a Camp Committee Member.
     * @param campID  The UUID of the camp to which the student is associated as a committee member.
     */
    public static void registerStudentAsCommMember(Student student, UUID campID) {
        CampCommMember campCommMember = new CampCommMember(student, campID);
        addUser(campCommMember.getUserID(), campCommMember); // overwrites key of original student
    }

    /**
     * Displays information about all users (students and staff) in the user database.
     */
    public static void showUsers() {
        for (String userID : usersData.keySet()) {
            User user = usersData.get(userID);
            System.out.print(user.getName());
            if (user instanceof Student) {
                System.out.println(" student");
            } else {
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
    public static void updatePassword(User user, String password) {
        byte[] byteSalt = null;
        try {
            byteSalt = getSalt();
        } catch (NoSuchAlgorithmException e) {
            // do sth;
        }
        byte[] byteHashedPw = getSaltedHashSHA512(password, byteSalt);
        String strHashedPw = toHex(byteHashedPw);
        String strSalt = toHex(byteSalt);
        user.setPassword(strHashedPw);
        user.setSalt(strSalt);
    }

    /**
     * Checks if a user with the given userID exists in the user database.
     *
     * @param userID The userID to check.
     * @return True if the user exists; false otherwise.
     */
    public static boolean containsUser(String userID) {
        return usersData.containsKey(userID);
    }

    /**
     * Retrieves a user with the given userID from the user database.
     *
     * @param userID The userID of the user to retrieve.
     * @return The User object associated with the given userID.
     */
    public static User getUser(String userID) {
        return usersData.get(userID);
    }

    /**
     * Adds a new user to the user database.
     *
     * @param userID The userID of the new user.
     * @param user   The User object to add to the database.
     */
    public static void addUser(String userID, User user) {
        usersData.put(userID, user);
    }

    /**
     * Removes a user from the user database.
     *
     * @param userID The userID of the user to remove.
     */
    public static void removeUser(String userID) {
        usersData.remove(userID);
    }

    /**
     * Validates the login credentials of a user.
     *
     * @param userID   The userID of the user.
     * @param password The password to validate.
     * @return true if the credentials are valid; false otherwise.
     */
    public static boolean validateUser(String userID, String password) {
        User user = getUser(userID);
        if (user.getPassword().equals("password")) {
            return user.getPassword().equals(password);
        }

        byte[] byteSalt = fromHex(user.getSalt());
        byte[] byteHashedPw = getSaltedHashSHA512(password, byteSalt);
        byte[] byteStoredHashedPw = fromHex(user.getPassword());
        return Arrays.equals(byteHashedPw, byteStoredHashedPw);
    }

    /**
     * Gets random salt
     *
     * @return a byte array of random salt
     * @throws NoSuchAlgorithmException if hashing algorithm does not exist
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            secureRandom.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets byte array of salted and hashed password
     *
     * @param password user inputted password string
     * @param salt     byte array of random generated salt
     * @return byte array of salted and hashed password
     */
    private static byte[] getSaltedHashSHA512(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] byteData = md.digest(password.getBytes());
            md.reset();
            return byteData;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * Converts a byte array to a hexadecimal string
     *
     * @param hex hexadecimal string
     * @return byte array
     */
    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    /**
     * Converts a hexadecimal string to a byte array
     *
     * @param array byte array
     * @return hexadecimal string
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
}
