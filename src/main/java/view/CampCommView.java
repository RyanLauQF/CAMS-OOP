package view;

import controller.CampManager;
import controller.SuggestionManager;
import java.util.UUID;
import helper.UserIO;
import model.CampCommMember;

public class CampCommView {
    public static void renderView(CampCommMember student){
        while(true){
            try{
                System.out.println("======================= HOME MENU =======================");
                System.out.println("1) View available camps");
                System.out.println("2) Register for camp");
                System.out.println("3) View registered camps");
                System.out.println("4) View your enquiries");

                System.out.println("\n------------- CAMP COMMITTEE MEMBER MENU -------------");
                System.out.println("Camp Committee Member of: " + CampManager.getCamp(student.getCommCampID()).getName());
                System.out.println("5) View your suggestions");
                System.out.println("6) View all enquiries for camp");
                System.out.println("7) View your points");
                System.out.println("8) Generate camp report");
                System.out.println("9) Generate enquiry report");
                System.out.println("------------------------------------------------------\n");

                System.out.println("10) Change Password");
                System.out.println("11) Logout");
                System.out.println("=========================================================\n");

                System.out.print("Select an action: ");
                int choice = UserIO.getSelection(1, 11);

                switch (choice){
                    case 1:
                        StudentView.availableCampsView(student);
                        break;
                    case 2:
                        StudentView.registerCampView(student);
                        break;
                    case 3:
                        StudentView.registeredCampsView(student);
                        break;
                    case 4:
                        EnquiryView.studentEnquiryView(student);
                        break;
                    case 5:
                        SuggestionView.ccmSuggestionView(student);
                        break;
                    case 6:
                        UUID campID = student.getCommCampID();
                        EnquiryView.ccmViewEnquiryView(campID, student);
                        break;
                    case 7:
                        System.out.println("You have: " + student.getPoints() + " point(s)");
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        AppView.changePasswordView(student);
                        break;
                    case 11:
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
