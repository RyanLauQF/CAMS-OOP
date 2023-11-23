package com.cmas.view;

import com.cmas.helper.ConsoleColours;
import com.cmas.helper.UserIO;
import com.cmas.model.Staff;
import com.cmas.model.User;
import com.cmas.controller.UserManager;

import java.util.HashMap;


/**
 * View class for rendering the main application interface.
 * Handles user login, authentication, password reset, and navigation to specific views based on user roles.
 *
 * @author Ryan Lau, Shao Chong
 * @version 1.0
 * @since 2023-11-18
 */
public class AppView {

    /**
     * Renders the main login interface.
     * Allows users to log in, quit the program, and navigates to specific views based on their roles upon login.
     */
    public static void renderView() {
        int count = 0;
        HashMap<String, Integer> tmp = new HashMap<>();
        while (true) {
            // system login
            String userID, password;

            System.out.println("\nWelcome to Camp Application and Management System (CAMs)");
            System.out.println("========================================================");
            System.out.print(ConsoleColours.BLUE);
            System.out.println("1) Login");
            System.out.println("2) Quit");
            System.out.print(ConsoleColours.RESET);
            System.out.print("\nSelect an action: ");

            int choice = UserIO.getSelection(1, 2);
            if (choice == 2) {
                // quit program
                System.out.println("\nExiting CAMs. See you again!\n");
                return;
            }

            while (true) {
                System.out.print("Enter your User ID: ");
                userID = UserIO.getStringResponse();

                System.out.print("Enter your Password: ");
                password = UserIO.getStringResponse();

                // check if user exists and credentials are valid
                if (!UserManager.containsUser(userID)) {
                    System.out.println(ConsoleColours.RED + "Invalid User!" + ConsoleColours.RESET);
                    continue;
                }

                if (!UserManager.validateUser(userID, password)) {
                    tmp.put(userID, tmp.getOrDefault(userID, 0) + 1);
                    if (tmp.get(userID) == 3) break;
                    System.out.println(ConsoleColours.RED + "Invalid Password!" + ConsoleColours.RESET);
                    System.out.println(ConsoleColours.RED + "You have " + (3 - tmp.get(userID)) + " tries left" + ConsoleColours.RESET);
                    continue;
                }

                User user = UserManager.getUser(userID);

                // valid user
                if (user instanceof Staff) {
                    // staff display
                    StaffView.renderView(userID);
                } else {
                    // student display
                    StudentView.renderView(userID);
                }
            }
        }
    }

    /**
     * Renders view for changing user's password.
     *
     * @param user The User object for whom the password is to be changed.
     * @throws Exception If an error occurs during the password change process.
     */
    public static void changePasswordView(User user) throws Exception {
        System.out.print("Enter new password: ");
        String newPassword = UserIO.getStringResponse();

        // TODO: first time login needs a reset password flow
        // TODO: password checking? can throw exceptions if u want to
        UserManager.updatePassword(user, newPassword);

        System.out.println("Successfully changed password!\n");
    }
}
