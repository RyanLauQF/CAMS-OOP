package view;

import controller.CampManager;
import controller.UserManager;
import helper.ConsoleColours;
import helper.UserIO;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class StaffView {
    public static void renderView(String staffID) {

        while (true) {
            User user = UserManager.getUser(staffID);
            Staff staff = (Staff) user;
            try {
                System.out.println("\nLOGGED IN AS " + "ID: " + staffID + ", NAME: " + staff.getName() + ", FACULTY: " + staff.getFaculty());
                System.out.println("======================= HOME MENU =======================");
                System.out.println("1) View All Camps");
                System.out.println("2) Select Camp");
                System.out.println("3) Create new Camp");
                System.out.println("4) View all Suggestions");
                System.out.println("5) Accept/Reject Suggestions");
                System.out.println("6) Generate Camp Report");
                System.out.println("7) Generate Performance Report");
                System.out.println("8) Change Password");
                System.out.println("9) Logout");
                System.out.println("=========================================================\n");

                System.out.print("Select an action: ");

                int choice = UserIO.getSelection(1, 9);

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
                        allSuggestionsView(staff);
                        break;
                    case 5:
                        processSuggestionView(staff);
                        break;
                    case 6:
                        generateReportView(staff);
                        break;
                    case 7:
                        generatePerformanceReportView(staff);
                        break;
                    case 8:
                        AppView.changePasswordView(staff);
                        break;
                    case 9:
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
            System.out.println("Camp No.: " + count);
            allCamps.get(key).printCampDetails();
        }

        System.out.println("\n=========================================================\n");
    }

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
            System.out.println("Camp No.: " + count);
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


    public static void selectCampView(Staff staff, UUID campUID) {
        while (true) {
            try {
                System.out.println("======================= SELECT MENU =======================");
                System.out.println("1) Edit Camp Details");
                System.out.println("2) View Registered Attendees");
                System.out.println("3) View Enquiries");
                System.out.println("4) View Suggestions");
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
                        EnquiryView.viewEnquiryView(campUID);
                        break;
                    case 4:
                        allSuggestionsView(staff);
                        break;
                    case 5:
                        deleteCampView(campUID);
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

    public static void allAttendeesView(UUID campUID) {
        System.out.println("print all attendees in the selected camp");
    }

    public static void deleteCampView(UUID campUID) {
        System.out.println("Are you sure you want to delete this camp?");
        System.out.print("Enter 'YES' to confirm deletion: ");
        String confirmation = UserIO.getStringResponse();
        if (confirmation.equals("YES")) {
            // TODO: DELETE CAMP FROM DB
            return;
        }
        System.out.println("Deletion Unsuccessful. Try again");
    }

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

    public static void generateReportView(Staff staff) {  //if i want to allow them to search by name, location, faculty????
        while (true) {
            try {
                System.out.println("======================= SELECT FILTERS =======================");
                System.out.println("1) Attendee");
                System.out.println("2) Camp Committee Member");
                System.out.println("3) ....");
                System.out.println("4) Quit");
                System.out.println("===========================================================\n");

                System.out.print("Select an action: ");

                int choice = UserIO.getSelection(1, 3);

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


    public static void generatePerformanceReportView(Staff staff) {
        //prints the camp that they wanna see, print the points of the camp committee member
        staff.generatePerformanceReport();
    }

    public static void allSuggestionsView(Staff staff) {
        SuggestionView.showStaffSuggestionView(staff);
    }

    public static void processSuggestionView(Staff staff) {
        SuggestionView.approveSuggestionView(staff);
    }
}
