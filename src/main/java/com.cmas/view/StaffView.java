package com.cmas.view;

import com.cmas.helper.ConsoleColours;
import com.cmas.helper.UserIO;
import com.cmas.model.Camp;
import com.cmas.model.Staff;
import com.cmas.model.User;
import com.cmas.model.UserGroup;
import com.cmas.controller.CampManager;
import com.cmas.controller.UserManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
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
public class StaffView {

    /**
     * Renders the main menu for Camp Committee Members and handles user interactions.
     *
     * @param staffID The unique identifier for the staff member.
     */
    public static void renderView(String staffID) {

        while (true) {
            User user = UserManager.getUser(staffID);
            Staff staff = (Staff) user;
            try {
                System.out.println("\n=========================================================");
                System.out.println("Logged in as " + ConsoleColours.BLUE + staff.getName() + " (" + staffID + ")" + ", " + staff.getFaculty() + ConsoleColours.RESET);
                System.out.println("=========================================================");
                System.out.println("======================= HOME MENU =======================");
                System.out.print(ConsoleColours.BLUE);
                System.out.println("1) View All Camps");
                System.out.println("2) Select Camp");
                System.out.println("3) Create new Camp");
                System.out.println("4) View all enquiries");
                System.out.println("5) View all Suggestions");
                System.out.println("6) Accept/Reject Suggestions");
                System.out.println("7) Generate Camp Report");
                System.out.println("8) Generate Performance Report");
                System.out.println("9) Change Password");
                System.out.println("10) Logout");
                System.out.print(ConsoleColours.RESET);
                System.out.println("=========================================================");
                System.out.print("\nSelect an action: ");

                int choice = UserIO.getSelection(1, 10);

                switch (choice) {
                    case 1:
                        allCampView();
                        break;
                    case 2:
                        staffCampView(staff);
                        break;
                    case 3:
                        createCampView(staff);
                        break;
                    case 4:
                        StaffEnquiryView.showAllEnquiryStaffView(staff);
                        break;
                    case 5:
                        allSuggestionsView(staff);
                        break;
                    case 6:
                        processSuggestionView(staff);
                        break;
                    case 7:
                        generateReportView(staff);
                        break;
                    case 8:
                        generatePerformanceReportView(staff);
                        break;
                    case 9:
                        AppView.changePasswordView(staff);
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
     * Displays details of all available camps.
     */
    public static void allCampView() {
        HashMap<UUID, Camp> allCamps = CampManager.getAllCamps();

        if (allCamps.isEmpty()) {
            System.out.println(ConsoleColours.YELLOW + "\nThere are no available camps!" + ConsoleColours.RESET);
            return;
        }

        System.out.println("\n=========================================================\n");

        int count = 0;
        for (UUID key : allCamps.keySet()) {
            count++;
            System.out.println(ConsoleColours.BLUE + "Camp No.: " + count + ConsoleColours.RESET);
            allCamps.get(key).printCampDetails();
        }

        System.out.println("\n=========================================================\n");
    }

    /**
     * Displays details of camps created by the staff member and allows the selection of a specific camp.
     *
     * @param staff The staff member whose camps are to be displayed.
     */
    public static void staffCampView(Staff staff) {

        Set<UUID> campKeys = staff.getCampIDs();
        HashMap<UUID, Camp> allCamps = CampManager.getAllCamps();

        if (allCamps.isEmpty()) {
            System.out.println(ConsoleColours.YELLOW + "\nThere are no available camps!" + ConsoleColours.RESET);
            return;
        }

        // maps choice to UID of camp in hashmap
        HashMap<Integer, UUID> campSelection = new HashMap<>();

        // prints all camp staff has created
        System.out.println("\n=========================================================\n");

        int count = 0;
        for (UUID key : campKeys) {
            count++;
            System.out.println(ConsoleColours.BLUE + "Camp No.: " + count + ConsoleColours.RESET);
            allCamps.get(key).printCampDetails();
            campSelection.put(count, key);
        }

        System.out.println("\n=========================================================\n");
        if (count == 0) {
            return;
        }
        System.out.print("Select a camp (0 to return to previous menu): ");
        int choice = UserIO.getSelection(0, count);
        if (choice == 0) {
            return;
        }
        UUID campUID = campSelection.get(choice);

        selectCampView(staff, campUID);
    }

    /**
     * Displays options for a specific camp selected by the staff member.
     *
     * @param staff   The staff member.
     * @param campUID The unique identifier of the selected camp.
     */
    public static void selectCampView(Staff staff, UUID campUID) {
        while (true) {
            try {
                System.out.println("\n======================= SELECT MENU =======================");
                System.out.println("Camp Name: " + CampManager.getCamp(campUID).getName());
                System.out.println("1) Edit Camp Details");
                System.out.println("2) View Registered Attendees");
                System.out.println("3) View Enquiries For This Camp");
                System.out.println("4) View Suggestions For This Camp");
                System.out.println("5) Delete Camp");
                System.out.println("6) Cancel Selection");
                System.out.println("===========================================================\n");

                System.out.print("Select an action: ");

                int choice = UserIO.getSelection(1, 6);

                switch (choice) {
                    case 1:
                        CampEditView.editCampView(campUID);
                        break;
                    case 2:
                        allAttendeesView(campUID);
                        break;
                    case 3:
                        StaffEnquiryView.viewEnquiryView(campUID, staff);
                        break;
                    case 4:
                        SuggestionView.getSuggestionsForCampView(campUID);
                        break;
                    case 5:
                        deleteCampView(campUID, staff);
                        return;
                    case 6:
                        System.out.println("Cancelling Selection...\n");
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
     * Displays the list of attendees for a specific camp.
     *
     * @param campUID The unique identifier of the camp.
     */
    public static void allAttendeesView(UUID campUID) {
        Camp camp = CampManager.getCamp(campUID);
        System.out.println("===========================================================");
        System.out.println("Attendees:");
        if (camp.getRegisteredAttendees().isEmpty()) {
            System.out.println(ConsoleColours.YELLOW + "NO ATTENDEES" + ConsoleColours.RESET);
        } else {

            camp.getRegisteredAttendees().forEach(id -> System.out.println(UserManager.getUser(id).getName()));
        }
        System.out.println("Camp Committee Members:");
        if (camp.getRegisteredCommMembers().isEmpty()) {
            System.out.println(ConsoleColours.YELLOW + "NO CAMP COMMITTEE MEMBERS" + ConsoleColours.RESET);
        } else {

            camp.getRegisteredCommMembers().forEach(id -> System.out.println(UserManager.getUser(id).getName()));
        }
        System.out.println("===========================================================");
    }

    /**
     * Prompts the staff member for confirmation and deletes the selected camp.
     *
     * @param campUID The unique identifier of the camp to be deleted.
     */
    public static void deleteCampView(UUID campUID, Staff staff) {
        Camp camp = CampManager.getCamp(campUID);
        if (camp.getRegisteredCommMembers().isEmpty() && camp.getRegisteredAttendees().isEmpty() ){
            System.out.println("Are you sure you want to delete this camp?");
            System.out.print("Enter 'YES' to confirm deletion: ");
            String confirmation = UserIO.getStringResponse();
            if (confirmation.equals("YES")) {
                CampManager.deleteCamp(campUID, staff);
                System.out.println("Camp successfully deleted.");
                return;
            }
        }
        else{
            System.out.println("You have attendees in the camp already. Camp cannot be deleted.");
            return;
        }
        System.out.println("Deletion Unsuccessful. Try again");
    }

    /**
     * Displays the form for creating a new camp and adds the camp to the system.
     *
     * @param staff The staff member creating the camp.
     */
    public static void createCampView(Staff staff) {
        System.out.print("Enter the name of camp: ");
        String name = UserIO.getStringResponse();

        System.out.print("Enter the start date of the camp(yyyy-MM-dd): ");
        LocalDate startDate = UserIO.getDateResponse();

        LocalDate endDate;
        while (true) {
            System.out.print("Enter the end date of the camp(yyyy-MM-dd): ");
            endDate = UserIO.getDateResponse();
            if (!endDate.isAfter(startDate)) {
                System.out.print(ConsoleColours.RED + "End date has to be after the start date!\n" + ConsoleColours.RESET);
                continue;
            }
            break;
        }

        LocalDate closingDate;
        while (true) {
            System.out.print("Enter the registration closing date of the camp(yyyy-MM-dd): ");
            closingDate = UserIO.getDateResponse();
            if (!closingDate.isBefore(startDate)) {
                System.out.print(ConsoleColours.RED + "Registration has to close before the start of the camp!\n" + ConsoleColours.RESET);
                continue;
            }
            break;
        }

        List<UserGroup> UserGroupList = new ArrayList<UserGroup>(Arrays.asList(UserGroup.values()));
        System.out.print(ConsoleColours.BLUE);
        for (int i = 0; i < UserGroupList.size(); i++) {
            System.out.printf("%-3s %-7s", (i + 1) + ")", UserGroupList.get(i));
            if ((i + 1) % 4 == 0) {
                System.out.print("\n");
            }
        }
        System.out.print("\n" + ConsoleColours.RESET);
        System.out.print("Enter group that camp is open to (1 - " + UserGroupList.size() + "): ");
        int choice = UserIO.getSelection(1, UserGroupList.size());
        UserGroup userGroup = UserGroupList.get(choice - 1);

        System.out.print("Enter the location of camp: ");
        String location = UserIO.getStringResponse();

        int totalSlots;
        while (true) {
            System.out.print("Enter the number of slots: ");
            totalSlots = UserIO.getIntResponse();
            if (totalSlots < 1) {
                System.out.print(ConsoleColours.RED + "Minimum number of slots is 1!\n" + ConsoleColours.RESET);
                continue;
            }
            break;
        }

        System.out.print("Enter the description of your camp: ");
        String description = UserIO.getStringResponse();

        System.out.print("Enter the visibility of your camp (Y/N): ");
        boolean isVisible = UserIO.getBoolResponse();

        Camp newCamp = new Camp(name, startDate, endDate, closingDate, userGroup, location, totalSlots, description, staff, isVisible);
        CampManager.addNewCamp(newCamp, staff);

        System.out.print(ConsoleColours.GREEN + "\n" + "Camp has been successfully created!\n" + ConsoleColours.RESET);
    }

    /**
     * Displays options for generating various types of reports.
     *
     * @param staff The staff member generating the reports.
     */
    public static void generateReportView(Staff staff) {
        while (true) {
            try {
                System.out.println("======================= SELECT FILTERS =======================");
                System.out.println("1) Attendee");
                System.out.println("2) Camp Committee Member");
                System.out.println("3) ....");
                System.out.println("4) Quit");
                System.out.println("===========================================================\n");

                System.out.print("Select an action: ");

                int choice = UserIO.getSelection(1, 4);

                switch (choice) {
                    case 1:
                        staff.generateReport(1);
                        return;
                    case 2:
                        staff.generateReport(2);
                        return;
                    case 3:
                        System.out.println("Generating Report for Location");
                        staff.generateReport(3);
                        return;
                    case 4:
                        System.out.println("Cancelling Report...");
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
     * Displays the camp performance report for the staff member.
     *
     * @param staff The staff member generating the performance report.
     */
    public static void generatePerformanceReportView(Staff staff) {
        //prints the camp that they wanna see, print the points of the camp committee member
        staff.generatePerformanceReport();
    }

    /**
     * Displays all suggestions for the staff member.
     *
     * @param staff The staff member viewing suggestions.
     */
    public static void allSuggestionsView(Staff staff) {
        SuggestionView.showStaffSuggestionView(staff);
    }

    /**
     * Allows the staff member to approve or reject suggestions.
     *
     * @param staff The staff member processing suggestions.
     */
    public static void processSuggestionView(Staff staff) {
        SuggestionView.approveSuggestionView(staff);
    }
}
