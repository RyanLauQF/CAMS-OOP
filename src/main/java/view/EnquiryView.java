package view;

import controller.*;
import helper.ConsoleColours;
import helper.UserIO;
import model.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


/**
 * View class for rendering the enquiry menu and managing interactions related to camp enquiries.
 * Provides views for students to view, create, edit, and delete their enquiries.
 * Additionally, it contains views for Camp Committee Members to view, reply to, and generate reports for camp enquiries.
 *
 * @author Tong Ying, Shao Chong, Markus Lim
 * @version 1.0
 * @since 2023-11-18
 */
public class EnquiryView {

    /**
     * Renders the enquiry menu for students and manages user selections to render various views.
     *
     * @param student The Student for whom the enquiry menu is rendered.
     */
    public static void studentEnquiryView(Student student) {
        while (true) {
            try {
                System.out.println("\n===================== ENQUIRY MENU ======================");
                System.out.print(ConsoleColours.BLUE);
                System.out.println("1) Show all submitted enquiries");
                System.out.println("2) Create new enquiry");
                System.out.println("3) Edit enquiry");
                System.out.println("4) Delete Enquiry");
                System.out.println("5) Exit enquiry menu");
                System.out.print(ConsoleColours.RESET);
                System.out.println("=========================================================");

                System.out.print("\nSelect an action: ");

                int choice = UserIO.getSelection(1, 5);

                switch (choice) {
                    case 1:
                        showAllSubmittedEnquiry(student);
                        break;
                    case 2:
                        createEnquiryView(student);
                        break;
                    case 3:
                        editEnquiryView(student);
                        break;
                    case 4:
                        deleteEnquiryView(student);
                        break;
                    case 5:
                        System.out.println("\nExiting enquiry menu...");
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
     * Renders all the enquiries submitted by the student
     *
     * @param student The student whose enquiries are being displayed.
     */
    public static void showAllSubmittedEnquiry(Student student) {
        HashMap<UUID, Enquiry> allEnquiries = EnquiryManager.getAllEnquiries();
        Set<UUID> enquiryKeys = student.getSubmittedEnquiries();

        if (enquiryKeys.isEmpty()) {
            System.out.println(ConsoleColours.YELLOW + "\nThere are no available enquiries!" + ConsoleColours.RESET);
            return;
        }
        // maps choice to UID of camp in hashmap
        HashMap<Integer, UUID> enquirySelection = new HashMap<>();

        // prints all enquiry student has created
        System.out.println("\n=========================================================");
        System.out.println("---------------------------------------------------------");
        int count = 0;
        for (UUID key : enquiryKeys) {
            count++;
            System.out.println(ConsoleColours.BLUE + "Enquiry No.: " + count + ConsoleColours.RESET);
            EnquiryManager.printEnquiryDetails(key);
            System.out.println("---------------------------------------------------------");
            enquirySelection.put(count, key);
        }

        System.out.println("=========================================================\n");
    }

    /**
     * Renders view for student to create a new enquiry for a selected camp.
     *
     * @param student The student creating the enquiry.
     */
    public static void createEnquiryView(Student student) {
        HashMap<UUID, Camp> filteredCamps = CampManager.getCampInFaculty(student.getFaculty());

        if (filteredCamps.isEmpty()) {
            System.out.println(ConsoleColours.YELLOW + "\nThere are no available camps!" + ConsoleColours.RESET);
            return;
        }

        HashMap<Integer, UUID> selection = new HashMap<>();

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
        System.out.print("\nSelect camp you would like to submit enquiry for (0 to exit): ");
        int selected = UserIO.getSelection(0, count);
        if (selected == 0) return;
        UUID selectedCampID = selection.get(selected);

        try {
            System.out.println("Enter your query: ");
            String query = UserIO.getStringResponse();
            Enquiry enquiry = new Enquiry(query, student, selectedCampID);
            EnquiryManager.addNewEnquiry(enquiry, student);
        } catch (Exception e) {
            System.out.println("Error! " + e.getMessage());
            return;
        }
    }

    /**
     * Renders view for student to edit one of their submitted enquiries that have not been
     * processed by the camp committee.
     *
     * @param student The student editing the enquiry.
     */
    public static void editEnquiryView(Student student) {
        HashMap<UUID, Enquiry> allEnquiries = EnquiryManager.getAllEnquiries();
        Set<UUID> enquiryKeys = student.getSubmittedEnquiries();

        if (enquiryKeys.isEmpty() || enquiryKeys.stream().noneMatch(key -> !EnquiryManager.getEnquiry(key).getIsProcessed())) {
            System.out.println(ConsoleColours.YELLOW + "\nThere are no editable enquiries!" + ConsoleColours.RESET);
            return;
        }

        // maps choice to UID of camp in hashmap
        HashMap<Integer, UUID> enquirySelection = new HashMap<>();

        // prints all enquiry student has created
        System.out.println("\n=========================================================");

        int count = 0;
        System.out.println("---------------------------------------------------------");
        for (UUID key : enquiryKeys) {
            if (allEnquiries.get(key).getIsProcessed()) {
                continue;
            }
            count++;
            System.out.println(ConsoleColours.BLUE + "Enquiry No.: " + count + ConsoleColours.RESET);
            EnquiryManager.printEnquiryDetails(key);
            System.out.println("---------------------------------------------------------");
            enquirySelection.put(count, key);
        }
        System.out.println("=========================================================\n");

        System.out.println("Select enquiry to edit (0 to exit): ");
        int choice = UserIO.getSelection(0, count);
        if (choice == 0) return;
        UUID enquiryUID = enquirySelection.get(choice);
        System.out.println("Enter new enquiry: ");
        String query = UserIO.getStringResponse();
        EnquiryManager.updateEnquiryQuery(query, enquiryUID);
    }

    /**
     * Renders view for student to delete one of their submitted enquiries that have
     * not been processed by the camp committee.
     *
     * @param student The student deleting the enquiry.
     */
    public static void deleteEnquiryView(Student student) {
        HashMap<UUID, Enquiry> allEnquiries = EnquiryManager.getAllEnquiries();
        Set<UUID> enquiryKeys = student.getSubmittedEnquiries();

        if (enquiryKeys.isEmpty() || enquiryKeys.stream().noneMatch(key -> !EnquiryManager.getEnquiry(key).getIsProcessed())) {
            System.out.println(ConsoleColours.YELLOW + "\nThere are no available enquiries!" + ConsoleColours.RESET);
            return;
        }
        // maps choice to UID of camp in hashmap
        HashMap<Integer, UUID> enquirySelection = new HashMap<>();

        // prints all enquiry student has created
        System.out.println("\n=========================================================\n");

        int count = 0;
        for (UUID key : enquiryKeys) {
            if (allEnquiries.get(key).getIsProcessed()) {
                continue;
            }
            count++;
            System.out.println(ConsoleColours.BLUE + "Enquiry No.: " + count + ConsoleColours.RESET);
            EnquiryManager.printEnquiryDetails(key);
            System.out.println("\n-----------------------\n");
            enquirySelection.put(count, key);
        }
        System.out.println("\n=========================================================\n");

        System.out.println("Select enquiry to delete (0 to exit): ");
        int choice = UserIO.getSelection(0, count);
        if (choice == 0) return;
        UUID enquiryUID = enquirySelection.get(choice);
        EnquiryManager.deleteEnquiry(enquiryUID, student);
    }
    /**
     * Displays the view for showing all enquiries for camps managed by a specific Staff member.
     *
     * @param staff The Staff object representing the staff member.
     */
    public static void showAllEnquiryStaffView(Staff staff) {
        Set<UUID> camps = StaffManager.getAllCamps(staff);
        Set<UUID> campEnquiries = new HashSet<>();

        if (camps.isEmpty()) {
            System.out.println("You currently have no camps tagged to you!");
            return;
        }

        // get all suggestions
        for (UUID key : camps) {
            Set<UUID> enquiries = CampManager.getCampEnquiries(key);
            campEnquiries.addAll(enquiries);
        }

        if (campEnquiries.isEmpty()) {
            System.out.println("There are currently no enquiries for your camps.\n");
            return;
        }

        // prints all enquiries for camps staff is in charge of
        System.out.println("\n=========================================================");

        int count = 0;
        System.out.println("--------------------------------");
        for (UUID key : campEnquiries) {
            count++;
            System.out.println(ConsoleColours.BLUE + "Enquiry No.: " + count + ConsoleColours.RESET);
            EnquiryManager.printEnquiryDetails(key);
            System.out.println("--------------------------------");
        }

        System.out.println("=========================================================\n");
    }
    /**
     * Renders view for camp committee members to reply to enquiries and update enquiry status.
     *
     * @param campUID The UUID of camp for which the enquiries are being replied to.
     * @param user    The Camp Committee Member who is replying to the enquiries.
     */
    public static void viewEnquiryView(UUID campUID, User user) {
        //show all enquiries of chosen camp
        try {
            Camp camp = CampManager.getCamp(campUID);
            Set<UUID> enquiryKeys = camp.getEnquiryID();
            if (enquiryKeys.isEmpty()) {
                System.out.println(ConsoleColours.YELLOW + "\nThere are no available enquiries!" + ConsoleColours.RESET);
                return;
            }
            System.out.println("======================= ENQUIRIES =======================");
            HashMap<UUID, Enquiry> allEnquiries = EnquiryManager.getAllEnquiries();
            // maps choice to UID of camp in hashmap
            HashMap<Integer, UUID> enquirySelection = new HashMap<>();
            // prints all enquiries for this camp
            System.out.println("-----------------------");
            int count = 0;
            for (UUID key : enquiryKeys) {
                count++;
                System.out.println(ConsoleColours.BLUE + "Enquiry No.: " + count + ConsoleColours.RESET);
                EnquiryManager.printEnquiryDetails(key);
                System.out.println("-----------------------");
                enquirySelection.put(count, key);
            }
            System.out.println("=========================================================\n");

            System.out.println("Choose action:");
            System.out.println("1) Reply enquiries");
            System.out.println("2) Print enquiry report");
            System.out.println("3) Exit");
            int select = UserIO.getSelection(1, 3);
            switch (select) {
                case 1:
                    replyEnquiryView(campUID, user);
                    break;
                case 2:
                    generateEnquiryReportView(campUID);
                    break;
                case 3:
                    return;
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Renders view for camp committee members to reply to a specific enquiry and updates the enquiry status.
     * Additionally, adds points to the Camp Committee Member for replying to the enquiry.
     *
     * @param campUID The UUID of camp for which the enquiry is being replied to.
     * @param replier The Camp Committee Member who is replying to the enquiry.
     */
    public static void replyEnquiryView(UUID campUID, User replier) {
        Camp camp = CampManager.getCamp(campUID);
        Set<UUID> enquiryKeys = camp.getEnquiryID();
        if (enquiryKeys.isEmpty()) {
            System.out.println(ConsoleColours.YELLOW + "\nThere are no available enquiries!" + ConsoleColours.RESET);
            return;
        }
        System.out.println("\n======================= ENQUIRIES: TO BE REPLIED =======================");
        HashMap<UUID, Enquiry> allEnquiries = EnquiryManager.getAllEnquiries();
        // maps choice to UID of camp in hashmap
        HashMap<Integer, UUID> enquirySelection = new HashMap<>();
        int count = 0;
        System.out.println("-----------------------");

        for (UUID key : enquiryKeys) {
            if (allEnquiries.get(key).getIsProcessed()) {
                continue;
            }
            count++;
            System.out.println(ConsoleColours.BLUE + "Enquiry No.: " + count + ConsoleColours.RESET);
            EnquiryManager.printEnquiryDetails(key);
            System.out.println("-----------------------");
            enquirySelection.put(count, key);
        }
        System.out.println("=========================================================\n");

        System.out.print("Select enquiry to reply to (0 to exit): ");
        int choice = UserIO.getSelection(0, count);
        if (choice == 0) return;
        UUID enquiryUID = enquirySelection.get(choice);
        System.out.print("Enter reply: ");
        String reply = UserIO.getStringResponse();
        EnquiryManager.updateEnquiryReply(reply, enquiryUID, replier);
        EnquiryManager.updateEnquiryStatus(enquiryUID);
        if (replier instanceof CampCommMember) {
            CampCommMember ccm = (CampCommMember) replier;
            CampCommMemberManager.addPoint(ccm);
        }
    }

    /**
     * Displays menu for users to select filter when generating enquiry report. Users can choose to view all
     * enquiries, pending enquiries (those that have yet to be replied) or enquiries that have already been replied.
     *
     * @param campUID The UUID of camp for which the enquiry report is to be generated
     */
    public static void generateEnquiryReportView(UUID campUID) {
        //for each camp -> show few details -> show the enquiries
        Camp camp = CampManager.getCamp(campUID);
        while (true) {
            try {
                System.out.println("======================= SELECT FILTERS =======================");
                System.out.println("1) All Enquiries");
                System.out.println("2) Enquiries to be replied");
                System.out.println("3) Replied Enquiries");
                System.out.println("4) Quit");
                System.out.println("==============================================================\n");

                System.out.print("Select an action: ");

                int choice = UserIO.getSelection(1, 4);

                switch (choice) {
                    case 1:
                        System.out.println("Generating All Enquiries\n");
                        Enquiry.generateEnquiryReport(camp, 1);
                        return;
                    case 2:
                        System.out.println("Generating Enquiries To Be Replied\n");
                        Enquiry.generateEnquiryReport(camp, 2);
                        return;
                    case 3:
                        System.out.println("Generating Replied Enquiries\n");
                        Enquiry.generateEnquiryReport(camp, 3);
                        return;
                    case 4:
                        System.out.println("Cancelling Report...\n");
                        return;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
