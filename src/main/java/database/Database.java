package database;

import model.*;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

/**
 * Database class to maintain persistent data integrity. Reads and writes Hashmap instances to ".dat" files.
 * This class provides methods for loading data from and saving data to serialized files,
 * as well as handling temporary CSV file processing for testing purposes.
 *
 * @author Ryan Lau
 * @version 1.0
 * @since 2023-11-14
 */
public class Database {

    /**
     * HashMap to store user data with user IDs (uppercase) as keys and User objects as values.
     */
    public static HashMap<String, User> USER_DATA = new HashMap<>();

    /**
     * HashMap to store camp data with UUIDs as keys and Camp objects as values.
     */
    public static HashMap<UUID, Camp> CAMP_DATA = new HashMap<>();

    /**
     * HashMap to store suggestion data with UUIDs as keys and Suggestion objects as values.
     */
    public static HashMap<UUID, Suggestion> SUGGESTION_DATA = new HashMap<>();

    /**
     * HashMap to store enquiry data with UUIDs as keys and Enquiry objects as values.
     */
    public static HashMap<UUID, Enquiry> ENQUIRY_DATA = new HashMap<>();

    // FOR TESTING
    public static final String STAFF_LIST_FILEPATH = "src/main/resources/staff_list.csv";
    public static final String STUDENT_LIST_FILEPATH = "src/main/resources/student_list.csv";

    public static final String filepath = "./src/main/java/database/data/";

    /**
     * Constructor for the Database class. Initializes the database by loading data from serialized files.
     */
    public Database(){
        loadFromDatabase();
    }

    /**
     * Loads data from serialized files into the corresponding HashMaps.
     */
    @SuppressWarnings("unchecked")
    public void loadFromDatabase(){
        // IF THE USER/CAMP OBJECTS ARE CHANGED, YOU WILL PROBABLY NEED THIS FUNCTION TO RELOAD THE DATABASE
//        processCSV(USER_DATA, STAFF_LIST_FILEPATH, false);
//        processCSV(USER_DATA, STUDENT_LIST_FILEPATH, true);
//
        USER_DATA = (HashMap<String, User>) deserializeObject("Users.dat");
        CAMP_DATA = (HashMap<UUID, Camp>) deserializeObject("Camps.dat");
        ENQUIRY_DATA = (HashMap<UUID, Enquiry>) deserializeObject("Enquiries.dat");
        SUGGESTION_DATA = (HashMap<UUID, Suggestion>) deserializeObject("Suggestions.dat");
    }

    /**
     * Saves data from HashMaps to serialized files, ensuring persistent data integrity.
     */
    public void saveToDatabase(){
        serializeObject("Users.dat", USER_DATA);
        serializeObject("Camps.dat", CAMP_DATA);
        serializeObject("Suggestions.dat", SUGGESTION_DATA);
        serializeObject("Enquiries.dat", ENQUIRY_DATA);
    }

    /**
     * Serializes an object and saves it to a specified file.
     *
     * @param fileName The name of the file to which the object is serialized.
     * @param object   The object to be serialized and saved.
     */
    public static void serializeObject(String fileName, Serializable object) {
        fileName = filepath + fileName;
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(object);
            System.out.println("Object serialized and saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes an object from a specified file.
     *
     * @param fileName The name of the file from which the object is deserialized.
     * @return The deserialized object.
     */
    public static Object deserializeObject(String fileName) {
        fileName = filepath + fileName;
        Object object = null;

        try (FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn)) {
            object = in.readObject();
            System.out.println("Object deserialized from " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * Processes CSV files for temporary data loading during testing.
     *
     * @param usersData The HashMap to which user data is added.
     * @param filePath  The path of the CSV file to be processed.
     * @param isStudent A boolean indicating whether the processed data is for students or staff.
     */
    private static void processCSV(HashMap<String, User> usersData, String filePath, boolean isStudent){
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            br.readLine(); // ignore header

            while((line = br.readLine()) != null){
                String[] data = line.split(",");
                String name = data[0].trim();
                String email = data[1].trim();
                String faculty = data[2].trim();

                String userID = email.substring(0, email.indexOf("@")); // set user id from email
                UserGroup userGroup = UserGroup.valueOf(faculty.toUpperCase());

                if(isStudent){
                    Student student = new Student(name, email, userGroup);
                    usersData.put(userID, student);
                }
                else{
                    Staff staff = new Staff(name, email, userGroup);
                    usersData.put(userID, staff);
                }
            }
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
