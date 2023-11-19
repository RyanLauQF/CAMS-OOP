package database;

import model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
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

    /**
     * Filepath to data folder containing ".dat" files
     */
    public static final String filepath = "./src/main/java/database/data/";

    // FOR TESTING
    public static final String STAFF_LIST_FILEPATH = "src/main/resources/staff_list.csv";
    public static final String STUDENT_LIST_FILEPATH = "src/main/resources/student_list.csv";

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

        USER_DATA = (HashMap<String, User>) deserializeObject("Users.txt");
        CAMP_DATA = (HashMap<UUID, Camp>) deserializeObject("Camps.txt");
        ENQUIRY_DATA = (HashMap<UUID, Enquiry>) deserializeObject("Enquiries.txt");
        SUGGESTION_DATA = (HashMap<UUID, Suggestion>) deserializeObject("Suggestions.txt");
    }

    /**
     * Saves data from HashMaps to serialized files, ensuring persistent data integrity.
     */
    public void saveToDatabase(){
        serializeObject("Users.txt", USER_DATA);
        serializeObject("Camps.txt", CAMP_DATA);
        serializeObject("Suggestions.txt", SUGGESTION_DATA);
        serializeObject("Enquiries.txt", ENQUIRY_DATA);
    }

    /**
     * Serializes an object into a base-64 string and saves it to a specified text file.
     *
     * @param fileName The name of the file to which the object is serialized.
     * @param object   The object to be serialized and saved.
     */
    public static void serializeObject(String fileName, Serializable object) {
        String filePath = filepath + fileName;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(baos)) {
            out.writeObject(object);
            out.flush();

            // convert the byte array to Base64-encoded string
            byte[] binary = baos.toByteArray();
            String base64String = Base64.getEncoder().encodeToString(binary);

            // save the Base64-encoded string to a txt file
            Files.write(Paths.get(filePath), base64String.getBytes());

            System.out.println("Object serialized and saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes an object from a base-64 string in specified text file.
     *
     * @param fileName The name of the file from which the object is deserialized.
     * @return The deserialized object.
     */
    public static Object deserializeObject(String fileName) {
        fileName = filepath + fileName;
        Object object = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));
             ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.getDecoder().decode(reader.readLine()));
             ObjectInputStream in = new ObjectInputStream(byteArrayInputStream)) {

            // Deserialize the object from the byte array
            object = in.readObject();

            System.out.println("Object deserialized from " + fileName);
            return object;

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
            System.out.println(e.getMessage());
        }
    }
}
