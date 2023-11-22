package com.cmas.view;

import com.cmas.helper.ConsoleColours;
import com.cmas.helper.UserIO;
import com.cmas.model.CampCommMember;
import com.cmas.controller.CampManager;

import java.util.UUID;


/**
 * View class for rendering the home menu and managing interactions for Camp Committee Members.
 * Provides standard student view options such as to view available camps, register for camps,
 * view registered camps and view enquiries, change password and logout.
 *
 * Additional options under camp committee member menu including: view suggestions, view all enquiries, view points,
 * generate camp reports, generate enquiry reports.
 *
 * @author Shao Chong, Ryan Lau
 * @version 1.0
 * @since 2023-11-18
 */
public class CampCommView {

    /**
     * Renders the home interface for a Camp Committee Member, allowing interaction through a menu
     * with various options related to their role.
     *
     * @param student The CampCommMember object representing the logged-in committee member.
     */
    public static void renderView(CampCommMember student) {
        while (true) {
            try {
                System.out.println("\n=========================================================");
                System.out.println("Logged in as " + ConsoleColours.BLUE + student.getName() + " (" + student.getUserID() + ")" + ", " + student.getFaculty() + ConsoleColours.RESET);
                System.out.println("=========================================================");
                System.out.println("\n======================= HOME MENU =======================");
                System.out.print(ConsoleColours.BLUE);
                System.out.println("1) View available camps");
                System.out.println("2) Register for camp");
                System.out.println("3) View registered camps");
                System.out.println("4) Open Enquiry Menu");
                System.out.print(ConsoleColours.RESET);
                System.out.println("\n-------------- CAMP COMMITTEE MEMBER MENU ---------------");
                System.out.println("Camp Committee Member for: " + ConsoleColours.BLUE + CampManager.getCamp(student.getCommCampID()).getName() + ConsoleColours.RESET);
                System.out.print(ConsoleColours.BLUE);
                System.out.println("5) Open Suggestions Menu");
                System.out.println("6) View all enquiries for camp");
                System.out.println("7) View your points");
                System.out.println("8) Generate camp report");
//                System.out.println("9) Generate enquiry report");
                System.out.print(ConsoleColours.RESET);
                System.out.println("---------------------------------------------------------\n");
                System.out.print(ConsoleColours.BLUE);
                System.out.println("9) Change Password");
                System.out.println("10) Logout");
                System.out.print(ConsoleColours.RESET);
                System.out.println("=========================================================");

                System.out.print("\nSelect an action: ");
                int choice = UserIO.getSelection(1, 10);

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
                        EnquiryView.viewEnquiryView(campID, student);
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
                        System.out.println("\nLogging Out...");
                        return;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Displays the camp report generation view, allowing the committee member to select filters for
     * generating a report.
     *
     * @param student The CampCommMember object representing the logged-in committee member.
     */
    public static void generateReportView(CampCommMember student) {
        UUID campID = student.getCommCampID();
        try {
            System.out.println("======================= SELECT FILTERS =======================");
            System.out.println("1) No Filters");
            System.out.println("2) Attendee");
            System.out.println("3) Camp Committee Member");
            System.out.println("4) Quit");
            System.out.println("===========================================================\n");

            System.out.print("Select an action: ");

            int choice = UserIO.getSelection(1, 4);
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
