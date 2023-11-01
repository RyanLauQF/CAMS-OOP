package view;

import helper.UserIO;
import model.Student;
import model.User;


public class StudentView {
    public static void renderView(Student student){

        System.out.println("\nLogged in as Student");

        while(true){
            try{
                System.out.println("========================================================");
                System.out.println("1) Implement function");
                System.out.println("2) Logout\n");
                System.out.print("Select an action: ");

                int choice = UserIO.getSelection(1, 2);

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
