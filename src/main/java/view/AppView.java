package view;

import controller.UserManager;
import model.CampCommMember;
import model.Staff;
import model.Student;
import model.User;

import java.util.Scanner;

public class AppView {
    public static void renderView(){
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
            if(user instanceof Student){
                // student display
                Student student = (Student) user;
                StudentView.renderView(student);
            }
            else if(user instanceof Staff){
                // staff display
                Staff staff = (Staff) user;
                StaffView.renderView(staff);
            }
            else {
                // camp comm member
                CampCommMember commMember = (CampCommMember) user;
                CommMemView.renderView(commMember);
            }
        }

        sc.close();
    }
}
