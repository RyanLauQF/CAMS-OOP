package com.cmas.app;
import com.cmas.*;

import java.util.Scanner;

public class CAMsApp {
    public static final String STAFF_LIST_FILEPATH = "src/main/resources/staff_list.csv";
    public static final String STUDENT_LIST_FILEPATH = "src/main/resources/student_list.csv";

    /**
     * Main function to run Camp Application and Management System (CAMs)
     */
    public static void main(String[] args){

        // preprocess
        UserManager userManager = new UserManager(STAFF_LIST_FILEPATH, STUDENT_LIST_FILEPATH);
        CampManager campManager = new CampManager();

        Scanner sc = new Scanner(System.in);

        while(true){
            // system login
            String userID, password;

            System.out.println("\nWelcome to Camp Application and Management System (CAMs)");
            System.out.println("========================================================");
            System.out.println("1) Login");
            System.out.println("2) Quit\n");
            System.out.print("Select an action: ");

            while(!sc.hasNextInt()){
                System.out.println("Invalid Choice!");
                sc.nextLine();
                System.out.print("Select an action: ");
            }
            int choice = sc.nextInt();
            if (choice == 2) break; // quit program

            System.out.print("Enter your User ID: ");
            userID = sc.next();

            System.out.print("Enter your Password: ");
            password = sc.next();

            // check if user exists and credentials are valid
            if(!userManager.containsUser(userID)){
                System.out.println("Invalid User!");
                continue;
            }

            if(!userManager.validateUser(userID, password)){
                System.out.println("Invalid Password!");
                continue;
            }

            User user = userManager.getUser(userID);

            // valid user
            if(user instanceof Student){
                // student display
                Student student = (Student) user;
                studentDisplay(sc, student, userManager, campManager);
            }
            else if(user instanceof Staff){
                // staff display
                Staff staff = (Staff) user;
                staffDisplay(sc, staff, userManager, campManager);
            }
            else {
                // camp comm member
                CampCommMember commMember = (CampCommMember) user;
                commMemberDisplay(sc, commMember, userManager, campManager);
            }
        }

        sc.close();
    }

    private static void staffDisplay(Scanner sc, Staff staff, UserManager userManager, CampManager campManager){
        System.out.println("\nLogged in as Staff");

        while(true){
            try{
                System.out.println("========================================================");
                System.out.println("1) Implement function");
                System.out.println("2) Logout\n");
                System.out.print("Select an action: ");

                while(!sc.hasNextInt()){
                    System.out.println("Invalid Choice!");
                    sc.nextLine();
                    System.out.print("Select an action: ");
                }
                int choice = sc.nextInt();

                switch (choice){
                    case 1:
                        // do smth
                        System.out.println("Not implemented");
                        break;
                    case 2:
                        System.out.println("Logging Out...");
                        return;
                    default:
                        // do smth
                        break;
                }
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }

    private static void studentDisplay(Scanner sc, Student student, UserManager userManager, CampManager campManager){
        System.out.println("\nLogged in as Student");

        while(true){
            try{
                System.out.println("========================================================");
                System.out.println("1) Implement function");
                System.out.println("2) Logout\n");
                System.out.print("Select an action: ");

                while(!sc.hasNextInt()){
                    System.out.println("Invalid Choice!");
                    sc.nextLine();
                    System.out.print("Select an action: ");
                }
                int choice = sc.nextInt();

                switch (choice){
                    case 1:
                        // do smth
                        System.out.println("Not implemented");
                        break;
                    case 2:
                        System.out.println("Logging Out...");
                        return;
                    default:
                        // do smth
                        break;
                }
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }

    private static void commMemberDisplay(Scanner sc, CampCommMember commMember, UserManager userManager, CampManager campManager){
        System.out.println("\nLogged in as Camp Committee Member");

        while(true){
            try{
                System.out.println("========================================================");
                System.out.println("1) Implement function");
                System.out.println("2) Logout\n");
                System.out.print("Select an action: ");

                while(!sc.hasNextInt()){
                    System.out.println("Invalid Choice!");
                    sc.nextLine();
                    System.out.print("Select an action: ");
                }
                int choice = sc.nextInt();

                switch (choice){
                    case 1:
                        // do smth
                        System.out.println("Not implemented");
                        break;
                    case 2:
                        System.out.println("Logging Out...");
                        return;
                    default:
                        // do smth
                        break;
                }
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }
}
