package controller;

import database.Database;
import model.Student;
import model.User;

import java.util.HashMap;

public class UserManager {
    private final HashMap<String, User> usersData;

    public UserManager(){
        usersData = Database.USER_DATA;
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
