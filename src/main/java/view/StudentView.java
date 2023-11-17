package view;

import controller.CampManager;
import controller.UserManager;
import helper.ConsoleColours;
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

        while (true) {
            User user = UserManager.getUser(studentID);
            if (user instanceof CampCommMember) {
                // go to camp committee member view
                CampCommView.renderView((CampCommMember) user);
                return;
            } else {
                Student student = (Student) user;
                try {
                    System.out.println("\n=========================================================");
                    System.out.println("Logged in as " + ConsoleColours.BLUE + student.getName() + " (" + studentID + ")" + ", " + student.getFaculty() + ConsoleColours.RESET);
                    System.out.println("=========================================================");
                    System.out.println("======================= HOME MENU =======================");
                    System.out.print(ConsoleColours.BLUE);
                    System.out.println("1) View all available camps within faculty");
                    System.out.println("2) Register for camp");
                    System.out.println("3) View registered camps");
                    System.out.println("4) Open Enquiry Menu");
                    System.out.println("5) Change password");
                    System.out.println("6) Logout");
                    System.out.print(ConsoleColours.RESET);
                    System.out.println("=========================================================");

                    System.out.print("\nSelect an action: ");
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
                            System.out.println("\nLogging Out...");
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
            System.out.println(ConsoleColours.YELLOW + "\nThere are no available camps" + ConsoleColours.RESET);
            return;
        }

        int count = 1;
        System.out.println("\n.........................................................");
        for (UUID key : filteredCamps.keySet()) {
            System.out.println(ConsoleColours.BLUE + "Camp No.: " + count + ConsoleColours.RESET);
            Camp camp = CampManager.getCamp(key);
            camp.printCampDetails();
            System.out.println(".........................................................");
            count++;
        }
    }

    public static void registerCampView(Student student) {
        HashMap<UUID, Camp> filteredCamps = CampManager.getCampInFaculty(student.getFaculty());
        HashMap<Integer, UUID> selection = new HashMap<>();

        if (filteredCamps.isEmpty()) {
            System.out.println(ConsoleColours.YELLOW + "\nThere are no available camps!" + ConsoleColours.RESET);
            return;
        }

        int count = 1;
        System.out.println("\n.........................................................");
        for (UUID key : filteredCamps.keySet()) {
            System.out.println(ConsoleColours.BLUE + "Camp No.: " + count + ConsoleColours.RESET);
            Camp camp = CampManager.getCamp(key);
            camp.printCampDetails();
            selection.put(count, key);
            System.out.println(".........................................................");
            count++;
        }
        System.out.print("\nSelect the camp you would like to register for (0 to exit): ");
        int selected = UserIO.getSelection(0, count);
        if (selected == 0) return;
        UUID selectedCampID = selection.get(selected);

        while (true) {
            try {
                System.out.println("\nRegister as a: ");
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
                        System.out.println("\nCancelling Registration...");
                        return;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println(ConsoleColours.RED + "\nError! " + e.getMessage() + ConsoleColours.RESET);
                return;
            }
        }
    }

    public static void registeredCampsView(Student student) {
        Set<UUID> campKeys = student.getRegisteredCamps();
        HashMap<UUID, Camp> allCamps = CampManager.getAllCamps();

        // handle case where there are no registered camps
        if (campKeys.isEmpty()) {
            System.out.println(ConsoleColours.YELLOW + "\nYou have not registered for any camps" + ConsoleColours.RESET);
            return;
        }

        // maps choice to UID of camp in hashmap
        HashMap<Integer, UUID> campSelection = new HashMap<>();

        // prints all camp student is registered
        System.out.println("\n=========================================================");

        int count = 1;
        System.out.println(".........................................................");
        for (UUID key : campKeys) {
            System.out.println(ConsoleColours.BLUE + "Camp No.: " + count + ConsoleColours.RESET);
            System.out.println("Your role: " + (CampManager.getCamp(key).getRegisteredAttendees().contains(student.getUserID()) ? "Attendee" : "Camp Committee Member"));
            allCamps.get(key).printCampDetails();
            campSelection.put(count, key);
            count++;
            System.out.println(".........................................................");
        }

        System.out.println("=========================================================\n");

        System.out.print("Select a camp (0 to exit): ");
        int choice = UserIO.getSelection(0, count);
        if (choice == 0) {
            return;
        }
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
                        System.out.println("\nQuitting...");
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
