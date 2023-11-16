package view;

import controller.CampManager;
import controller.UserManager;
import helper.UserIO;
import model.Camp;
import model.CampCommMember;
import model.Student;
import model.User;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class StudentView {
    public static void renderView(String studentID) {
        System.out.println("\nLogged in as " + studentID);

        while (true) {
            User user = UserManager.getUser(studentID);
            if (user instanceof CampCommMember) {
                // go to camp committee member view
                CampCommView.renderView((CampCommMember) user);
                return;
            } else {
                Student student = (Student) user;
                try {
                    System.out.println("======================= HOME MENU =======================");
                    System.out.println("1) View all available camps within faculty");
                    System.out.println("2) Register for camp");
                    System.out.println("3) View registered camps");
                    System.out.println("4) View your enquiries");
                    System.out.println("5) Change password");
                    System.out.println("6) Logout");
                    System.out.println("=========================================================\n");

                    System.out.print("Select an action: ");
                    int choice = UserIO.getSelection(1, 7);

                    switch (choice) {
                        case 1:
                            availableCampsView(student);
                            break;
                        case 2:
                            registerCampView(student);
                            break;
                        case 3:
                            registeredCampsView(student);
                            break;
                        case 4:
                            EnquiryView.studentEnquiryView(student);
                            break;
                        case 5:
                            AppView.changePasswordView(student);
                            break;
                        case 6:
                            System.out.println("Logging Out...");
                            return;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        }
    }

    public static void availableCampsView(Student student) {
        HashMap<UUID, Camp> filteredCamps = CampManager.getCampInFaculty(student.getFaculty());

        if (filteredCamps.isEmpty()) {
            System.out.println("\nNo camps available");
            System.out.println("\nBringing you back to the home menu...\n");
            return;
        }

        // TODO: make this look better
        int count = 1;
        for (UUID key : filteredCamps.keySet()) {
            System.out.println("Camp: " + count);
            Camp camp = CampManager.getCamp(key);
            camp.printCampDetails();
            System.out.println("\n---------------------------------------------------------\n");
            count++;
        }
    }

    public static void registerCampView(Student student) {
        HashMap<UUID, Camp> filteredCamps = CampManager.getCampInFaculty(student.getFaculty());
        HashMap<Integer, UUID> selection = new HashMap<>();

        if (filteredCamps.isEmpty()) {
            System.out.println("\nNo camps available");
            System.out.println("\nBringing you back to the home menu...\n");
            return;
        }

        int count = 1;
        for (UUID key : filteredCamps.keySet()) {
            System.out.println("Camp: " + count);
            Camp camp = CampManager.getCamp(key);
            camp.printCampDetails();
            selection.put(count, key);
            count++;
        }
        System.out.print("Select camp you will like to register for: ");
        int selected = UserIO.getSelection(1, count);
        UUID selectedCampID = selection.get(selected);

        while (true) {
            try {
                System.out.println("Would you like to register as a:");
                System.out.println("1) Attendee");
                System.out.println("2) Camp Committee Member");
                System.out.println("3) Cancel Registration\n");

                System.out.print("Enter selection: ");
                int choice = UserIO.getSelection(1, 3);

                switch (choice) {
                    case 1:
                        CampManager.registerStudent(student, selectedCampID);
                        return;
                    case 2:
                        CampManager.registerCommMember(student, selectedCampID);
                        return;
                    case 3:
                        System.out.println("Cancelling Registration...");
                        return;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage());
                return;
            }
        }
    }

    public static void registeredCampsView(Student student) {
        Set<UUID> campKeys = student.getRegisteredCamps();
        HashMap<UUID, Camp> allCamps = CampManager.getAllCamps();

        // handle case where there are no registered camps
        if (campKeys.isEmpty()) {
            System.out.println("\nYou have not registered for any camps");
            System.out.println("\nBringing you back to the home menu...\n");
            return;
        }

        // maps choice to UID of camp in hashmap
        HashMap<Integer, UUID> campSelection = new HashMap<>();

        // prints all camp student is registered
        System.out.println("\n=========================================================\n");

        int count = 1;
        for (UUID key : campKeys) {
            System.out.println("Camp Number: " + count);
            System.out.println(key);
            allCamps.get(key).printCampDetails();
            campSelection.put(count, key);
            count++;
        }

        System.out.println("\n=========================================================\n");

        System.out.print("Select a camp: ");
        int choice = UserIO.getSelection(1, count);

        UUID campUID = campSelection.get(choice);

        while (true) {
            try {
                System.out.println("Select action:");
                System.out.println("1) Withdraw from camp");
                System.out.println("2) Quit\n");

                System.out.print("Enter selection: ");
                int selection = UserIO.getSelection(1, 2);

                switch (selection) {
                    case 1:
                        CampManager.withdrawStudent(student, campUID);
                        return;
                    case 2:
                        System.out.println("Quitting...");
                        return;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage());
                return;
            }
        }
    }
}
