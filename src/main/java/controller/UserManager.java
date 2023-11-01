package controller;

import database.Database;
import model.CampCommMember;
import model.Student;
import model.User;

import java.util.HashMap;
import java.util.UUID;

public class UserManager {

    private static final HashMap<String, User> usersData = Database.USER_DATA;

    public static void registerStudentAsCommMember(Student student, UUID campID){
        CampCommMember campCommMember = new CampCommMember(student, campID);
        addUser(campCommMember.getUserID(), campCommMember); // overwrites key of original student
    }

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

    public static void updatePassword(User user, String password){
        user.setPassword(password);
    }

    public static boolean containsUser(String userID){
        return usersData.containsKey(userID);
    }

    public static User getUser(String userID){
        return usersData.get(userID);
    }

    public static boolean validateUser(String userID, String password){
        User user = getUser(userID);
        return user.getPassword().equals(password);
    }

    public static void addUser(String userID, User user){
        usersData.put(userID, user);
    }
    public static void removeUser(String userID){
        usersData.remove(userID);
    }
}
