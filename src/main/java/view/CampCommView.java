package view;

import controller.CampManager;

import java.util.UUID;
import helper.UserIO;
import model.CampCommMember;
import model.Staff;

import java.util.UUID;

/**
 * View class for rendering the home menu and managing interactions for Camp Committee Members.
 * Provides standard student view options such as to view available camps, register for camps,
 * view registered camps and view enquiries, change password and logout.
 * <p>
 * Additional options under camp committee member menu including: view suggestions, view all enquiries, view points,
 * generate camp reports, generate enquiry reports.
 *
 * @author Ryan Lau
 * @version 1.0
 * @since 2023-11-14
 */
public class CampCommView {
    public static void renderView(CampCommMember student) {
        while (true) {
            try {
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
//                System.out.println("9) Generate enquiry report");
                System.out.println("------------------------------------------------------\n");

                System.out.println("9) Change Password");
                System.out.println("10) Logout");
                System.out.println("=========================================================\n");

                System.out.print("Select an action: ");
                int choice = UserIO.getSelection(1, 11);

                switch (choice) {
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
                    case 6: {
                        UUID campID = student.getCommCampID();
                        EnquiryView.ccmViewEnquiryView(campID, student);
                        break;
                    }
                    case 7:
                        System.out.println("You have: " + student.getPoints() + " point(s)");
                        break;
                    case 8: {
                        generateReportView(student);
                        break;
                    }
                    case 9:
                        AppView.changePasswordView(student);
                        break;
                    case 10:
                        System.out.println("Logging Out...");
                        return;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void generateReportView(CampCommMember student) {
        UUID campID = student.getCommCampID();
        try {
            System.out.println("======================= SELECT FILTERS =======================");
            System.out.println("0) No Filters");
            System.out.println("1) Attendee");
            System.out.println("2) Camp Committee Member");
            System.out.println("3) Quit");
            System.out.println("===========================================================\n");

            System.out.print("Select an action: ");

            int choice = UserIO.getSelection(0, 3);
            if (choice == 4) {
                System.out.println("Quitting report generation...");
                return;
            }
            CampManager.generateReport(campID, choice);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
