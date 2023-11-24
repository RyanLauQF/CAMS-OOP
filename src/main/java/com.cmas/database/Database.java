package com.cmas.database;

import com.cmas.helper.FileIO;
import com.cmas.model.*;

import java.util.HashMap;
import java.util.UUID;


/**
 * Database class to maintain persistent data integrity. Reads and writes Hashmap instances to ".txt" files.
 * This class provides methods for loading data from and saving data to serialized files,
 * as well as handling temporary CSV file processing for testing purposes.
 *
 * @author Ryan Lau
 * @version 1.0
 * @since 2023-11-14
 */
public class Database {

    /**
     * Unique instance of database to ensure Singleton database class
     */
    private static Database uniqueInstance = null;

    /**
     * HashMap to store user data with user IDs (uppercase) as keys and User objects as values.
     */
    private static HashMap<String, User> USER_DATA = new HashMap<>();

    /**
     * HashMap to store camp data with UUIDs as keys and Camp objects as values.
     */
    private static HashMap<UUID, Camp> CAMP_DATA = new HashMap<>();

    /**
     * HashMap to store suggestion data with UUIDs as keys and Suggestion objects as values.
     */
    private static HashMap<UUID, Suggestion> SUGGESTION_DATA = new HashMap<>();

    /**
     * HashMap to store enquiry data with UUIDs as keys and Enquiry objects as values.
     */
    private static HashMap<UUID, Enquiry> ENQUIRY_DATA = new HashMap<>();

    /**
     * Private constructor for Database class. Initializes the database by loading data from serialized files.
     */
    private Database() {
        loadFromDatabase();
    }

    /**
     * Retrieves single instance of database. If it does not exist, database will be initialised via a private constructor
     * @return instance of database
     */
    public static Database getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new Database();
        return uniqueInstance;
    }

    /**
     * Loads data from serialized files into the corresponding HashMaps.
     */
    @SuppressWarnings("unchecked")
    public void loadFromDatabase() {
        try {
            USER_DATA = (HashMap<String, User>) FileIO.deserializeObject("Users.txt");
            CAMP_DATA = (HashMap<UUID, Camp>) FileIO.deserializeObject("Camps.txt");
            ENQUIRY_DATA = (HashMap<UUID, Enquiry>) FileIO.deserializeObject("Enquiries.txt");
            SUGGESTION_DATA = (HashMap<UUID, Suggestion>) FileIO.deserializeObject("Suggestions.txt");
        } catch (Exception e) {
            FileIO.loadDefaultUserData(USER_DATA);
        }
    }

    /**
     * Saves data from HashMaps to serialized files, ensuring persistent data integrity.
     */
    public void saveToDatabase() {
        FileIO.serializeObject("Users.txt", USER_DATA);
        FileIO.serializeObject("Camps.txt", CAMP_DATA);
        FileIO.serializeObject("Suggestions.txt", SUGGESTION_DATA);
        FileIO.serializeObject("Enquiries.txt", ENQUIRY_DATA);
    }

    // =========================== GETTER FUNCTIONS =========================== //

    /**
     * Retrieves instance of user data store from database
     * @return HashMap containing data with user IDs (uppercase) as keys and User objects as values.
     */
    public static HashMap<String, User> getUserData() {
        return USER_DATA;
    }

    /**
     * Retrieves instance of camp data store from database
     * @return HashMap containing data with UUIDs as keys and Camp objects as values.
     */
    public static HashMap<UUID, Camp> getCampData() {
        return CAMP_DATA;
    }

    /**
     * Retrieves instance of suggestion data store from database
     * @return HashMap containing data with UUIDs as keys and Suggestion objects as values.
     */
    public static HashMap<UUID, Suggestion> getSuggestionData() {
        return SUGGESTION_DATA;
    }

    /**
     * Retrieves instance of enquiry data store from database
     * @return HashMap containing data with UUIDs as keys and Enquiry objects as values.
     */
    public static HashMap<UUID, Enquiry> getEnquiryData() {
        return ENQUIRY_DATA;
    }
}
