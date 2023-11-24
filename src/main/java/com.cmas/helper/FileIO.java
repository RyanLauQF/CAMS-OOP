package com.cmas.helper;

import com.cmas.model.Staff;
import com.cmas.model.Student;
import com.cmas.model.User;
import com.cmas.model.UserGroup;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;


/**
 * Utility class for file serialisation operations. Provides methods for serialising/de-serialising files from encoded
 * binary txt files and to process csv files to load default user data.
 *
 * @author Ryan Lau, Shao Chong
 * @version 1.0
 * @since 2023-11-18
 */
public class FileIO {

    /**
     * Filepath to data folder containing ".txt" files
     */
    private static final String filepath = "./src/main/java/com.cmas/database/data/";

    /**
     * Filepath to csv file containing all staff details
     */
    private static final String STAFF_LIST_FILEPATH = "src/main/resources/staff_list.csv";

    /**
     * Filepath to csv file containing all student details
     */
    private static final String STUDENT_LIST_FILEPATH = "src/main/resources/student_list.csv";

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
    public static Object deserializeObject(String fileName) throws Exception {
        fileName = filepath + fileName;
        Object object = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));
             ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.getDecoder().decode(reader.readLine()));
             ObjectInputStream in = new ObjectInputStream(byteArrayInputStream)) {

            // deserialize the object from the byte array
            object = in.readObject();

            System.out.println("Object deserialized from " + fileName);
            return object;
        }
    }

    /**
     * Processes CSV files for temporary data loading during testing.
     *
     * @param usersData The HashMap to which user data is added.
     * @param filePath  The path of the CSV file to be processed.
     * @param isStudent A boolean indicating whether the processed data is for students or staff.
     */
    public static void processCSV(HashMap<String, User> usersData, String filePath, boolean isStudent) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // ignore header

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0].trim();
                String email = data[1].trim();
                String faculty = data[2].trim();

                String userID = email.substring(0, email.indexOf("@")); // set user id from email
                UserGroup userGroup = UserGroup.valueOf(faculty.toUpperCase());

                if (isStudent) {
                    Student student = new Student(name, email, userGroup);
                    usersData.put(userID, student);
                } else {
                    Staff staff = new Staff(name, email, userGroup);
                    usersData.put(userID, staff);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Populates user data with default user data from CSV and resets database.
     *
     * @param usersData The HashMap to which user data is added.
     */
    public static void loadDefaultUserData(HashMap<String, User> usersData){
        processCSV(usersData, STAFF_LIST_FILEPATH, false);
        processCSV(usersData, STUDENT_LIST_FILEPATH, true);
    }
}
