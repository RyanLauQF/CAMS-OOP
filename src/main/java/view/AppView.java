package view;

import controller.UserManager;
import helper.UserIO;
import model.Staff;
import model.User;


public class AppView {
    public static void renderView(){
        while(true){
            // system login
            String userID, password;

            System.out.println("\nWelcome to Camp Application and Management System (CAMs)");
            System.out.println("========================================================");
            System.out.println("1) Login");
            System.out.println("2) Quit\n");
            System.out.print("Select an action: ");

            int choice = UserIO.getSelection(1, 2);
            if (choice == 2) {
                // quit program
                System.out.println("Exiting CAMs. See you again!");
                return;
            }

            System.out.print("Enter your User ID: ");
            userID = UserIO.getStringResponse();

            System.out.print("Enter your Password: ");
            password = UserIO.getStringResponse();

            // check if user exists and credentials are valid
            if(!UserManager.containsUser(userID)){
                System.out.println("Invalid User!");
                continue;
            }

            if(!UserManager.validateUser(userID, password)){
                System.out.println("Invalid Password!");
                continue;
            }

            User user = UserManager.getUser(userID);

            // valid user
            if(user instanceof Staff){
                // staff display
                StaffView.renderView(userID);
            }
            else{
                // student display
                StudentView.renderView(userID);
            }
        }
    }

    public static void changePasswordView(User user) throws Exception {
        System.out.print("Enter new password: ");
        String newPassword = UserIO.getStringResponse();

        // TODO: first time login needs a reset password flow
        // TODO: password checking? can throw exceptions if u want to
        UserManager.updatePassword(user, newPassword);
    }
}