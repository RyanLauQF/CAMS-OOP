package com.cmas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class UserManager {
    private final HashMap<String, User> usersData;

    public UserManager(String staffFilePath, String studentFilePath){
        usersData = new HashMap<>();

        // read csv and build database containing all users
        processCSV(usersData, studentFilePath, true);
        processCSV(usersData, staffFilePath, false);
    }

    public static void processCSV(HashMap<String, User> usersData, String filePath, boolean isStudent){
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

    public void showUsers(){
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

    public boolean containsUser(String userID){
        return usersData.containsKey(userID);
    }

    public User getUser(String userID){
        return usersData.get(userID);
    }

    public boolean validateUser(String userID, String password){
        User user = getUser(userID);
        return user.getPassword().equals(password);
    }

//    public static void main(String[] args){
//        UserManager mng = new UserManager("src/main/resources/staff_list.csv", "src/main/resources/student_list.csv");
//        mng.showUsers();
//    }
}
