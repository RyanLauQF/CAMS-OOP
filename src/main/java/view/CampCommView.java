package view;

import controller.CampManager;
import helper.UserIO;
import model.Student;

public class CampCommView {
    public static void renderView(Student student){
        while(true){
            try{
                System.out.println("======================= HOME MENU =======================");
                System.out.println("1) View available camps");
                System.out.println("2) Register for camp");
                System.out.println("3) View registered camps");
                System.out.println("4) View all enquiries");
                System.out.println("5) View enquiry reply");

                System.out.println("\n------------- CAMP COMMITTEE MEMBER MENU -------------");
                System.out.println("Camp Committee Member of: " + CampManager.getCamp(student.getCommCampID()).getName());
                System.out.println("6) View all suggestions made");
                System.out.println("7) Submit suggestion to update camp details");
                System.out.println("8) Edit Suggestions");
                System.out.println("9) View all enquiries");
                System.out.println("10) Reply to enquiry");
                System.out.println("11) Generate camp report");
                System.out.println("12) Generate enquiry report");
                System.out.println("------------------------------------------------------\n");

                System.out.println("13) Change Password");
                System.out.println("14) Logout");
                System.out.println("=========================================================\n");

                System.out.print("Select an action: ");
                int choice = UserIO.getSelection(1, 14);

                switch (choice){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        System.out.println("Logging Out...");
                        return;
                    default:
                        break;
                }
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
        }

    }
}
