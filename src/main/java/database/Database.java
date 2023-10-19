package database;

import model.Camp;
import model.Staff;
import model.Student;
import model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Database {
    public static HashMap<Integer, Camp> CAMP_DATA = new HashMap<>();

    public static HashMap<String, User> USER_DATA = new HashMap<>();

    // to be removed
    public static final String STAFF_LIST_FILEPATH = "src/main/resources/staff_list.csv";
    public static final String STUDENT_LIST_FILEPATH = "src/main/resources/student_list.csv";

    public Database(){
        processCSV(USER_DATA, STUDENT_LIST_FILEPATH, true);
        processCSV(USER_DATA, STAFF_LIST_FILEPATH, false);
    }

    /**
       TODO: Need to implement a method to read / write hashmaps to ".dat" files for persistent storage
     */
    private static void readSerialized(){

    }

    private static void writeSerialized(){

    }

    // placeholder class to temporarily read from csv files
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

                if(isStudent){
                    Student student = new Student(name, email, faculty);
                    usersData.put(userID, student);
                }
                else{
                    Staff staff = new Staff(name, email, faculty);
                    usersData.put(userID, staff);
                }
            }
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

}
