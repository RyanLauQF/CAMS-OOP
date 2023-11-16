package view;

import controller.CampCommMemberManager;
import controller.CampManager;
import controller.EnquiryManager;
import helper.UserIO;
import model.*;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * View class for rendering the enquiry menu and managing interactions related to camp enquiries.
 * Provides views for students to view, create, edit, and delete their enquiries.
 * Additionally, it contains views for Camp Committee Members to view, reply to, and generate reports for camp enquiries.
 *
 * @author Tong Ying
 * @version 1.0
 * @since 2023-11-14
 */
public class EnquiryView {

    /**
     * Renders the enquiry menu for students and manages user selections to render various views.
     *
     * @param student The Student for whom the enquiry menu is rendered.
     */
    public static void studentEnquiryView(Student student){
        while(true){
            try{
                System.out.println("======================= ENQUIRY MENU =======================");
                System.out.println("1) Show all submitted enquiries");
                System.out.println("2) Create new enquiry");
                System.out.println("3) Edit enquiry");
                System.out.println("4) Delete Enquiry");
                System.out.println("5) Exit enquiry menu");
                System.out.println("=========================================================\n");

                System.out.print("Select an action: ");

                int choice = UserIO.getSelection(1, 5);

                switch (choice){
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
                    case 5:
                        System.out.println("Exiting enquiry menu...");
                        return;
                    default:
                        break;
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Renders all the enquiries submitted by the student
     *
     * @param student The student whose enquiries are being displayed.
     */
    public static void showAllSubmittedEnquiry(Student student){
        HashMap<UUID, Enquiry> allEnquiries = EnquiryManager.getAllEnquiries();
        Set<UUID> enquiryKeys = student.getSubmittedEnquiries();

        // maps choice to UID of camp in hashmap
        HashMap<Integer, UUID> enquirySelection = new HashMap<>();

        // prints all enquiry student has created
        System.out.println("\n=========================================================\n");

        int count = 0;
        for(UUID key : enquiryKeys){
            count++;
            System.out.println("Enquiry Number: " + count);
            System.out.println(key);
            allEnquiries.get(key).printEnquiryDetails();
            System.out.println("\n-----------------------\n");
            enquirySelection.put(count, key);
        }

        System.out.println("\n=========================================================\n");
    }

    /**
     * Renders view for student to create a new enquiry for a selected camp.
     *
     * @param student The student creating the enquiry.
     */
    public static void createEnquiryView(Student student){
        HashMap<UUID, Camp> filteredCamps = CampManager.getCampInFaculty(student.getFaculty());
        HashMap<Integer, UUID> selection = new HashMap<>();

        int count = 1;
        for(UUID key : filteredCamps.keySet()){
            System.out.println("Camp: " + count);
            Camp camp = CampManager.getCamp(key);
            camp.printCampDetails();
            selection.put(count, key);
            count++;
        }
        System.out.print("Select camp you would like to submit enquiry for: ");
        int selected = UserIO.getSelection(1, count);
        UUID selectedCampID = selection.get(selected);

        try{
            System.out.println("Enter your query: ");
            String query = UserIO.getStringResponse();
            Enquiry enquiry = new Enquiry(query, student, selectedCampID);
            EnquiryManager.addNewEnquiry(enquiry, student);
        }
        catch (Exception e){
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
    public static void editEnquiryView(Student student){
        HashMap<UUID, Enquiry> allEnquiries = EnquiryManager.getAllEnquiries();
        Set<UUID> enquiryKeys = student.getSubmittedEnquiries();

        // maps choice to UID of camp in hashmap
        HashMap<Integer, UUID> enquirySelection = new HashMap<>();

        // prints all enquiry student has created
        System.out.println("\n=========================================================\n");

        int count = 0;
        for(UUID key : enquiryKeys){
            if (allEnquiries.get(key).getIsProcessed()) {
                continue;
            }
            count++;
            System.out.println("Enquiry Number: " + count);
            System.out.println(key);
            allEnquiries.get(key).printEnquiryDetails();
            System.out.println("\n-----------------------\n");
            enquirySelection.put(count, key);
        }
        System.out.println("\n=========================================================\n");

        if (count == 0){
            System.out.println("No enquiries to be edited.");
            return;
        }

        System.out.println("Select enquiry to edit: ");
        int choice = UserIO.getSelection(1, count);
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
    public static void deleteEnquiryView(Student student){
        HashMap<UUID, Enquiry> allEnquiries = EnquiryManager.getAllEnquiries();
        Set<UUID> enquiryKeys = student.getSubmittedEnquiries();

        // maps choice to UID of camp in hashmap
        HashMap<Integer, UUID> enquirySelection = new HashMap<>();

        // prints all enquiry student has created
        System.out.println("\n=========================================================\n");

        int count = 0;
        for(UUID key : enquiryKeys){
            if (allEnquiries.get(key).getIsProcessed()) {
                continue;
            }
            count++;
            System.out.println("Enquiry Number: " + count);
            System.out.println(key);
            allEnquiries.get(key).printEnquiryDetails();
            System.out.println("\n-----------------------\n");
            enquirySelection.put(count, key);
        }
        System.out.println("\n=========================================================\n");

        if (count == 0){
            System.out.println("No enquiries to delete.");
            return;
        }

        System.out.println("Select enquiry to delete: ");
        int choice = UserIO.getSelection(1, count);
        UUID enquiryUID = enquirySelection.get(choice);
        EnquiryManager.deleteEnquiry(enquiryUID, student);
    }

    /**
     * Renders all enquiries for a specific camp, allowing the user to choose an action such as replying to enquiries.
     *
     * @param campUID The UUID of the camp for which enquiries are being displayed.
     */
    public static void viewEnquiryView(UUID campUID){
        try{
            Camp camp = CampManager.getCamp(campUID);
            Set<UUID> enquiryKeys = camp.getEnquiryID();
            HashMap<Integer, UUID> enquirySelection = new HashMap<>();

            if(enquiryKeys.isEmpty()){
                System.out.println("There are no enquiries for this camp.\n");
                return;
            }

            // prints all enquiries for this camp
            System.out.println("======================= ENQUIRIES =======================");
            int count = 1;
            for(UUID key : enquiryKeys){
                System.out.println("Enquiry No.: " + count);
                EnquiryManager.getEnquiry(key).printEnquiryDetails();
                System.out.println("\n-----------------------\n");
                enquirySelection.put(count, key);
                count++;
            }

            System.out.println("\n=========================================================\n");
            System.out.println("Choose action:");
            System.out.println("1) Reply enquiries");
            System.out.println("2) Print enquiry report");
            System.out.println("3) Exit");
            int select = UserIO.getSelection(1, 2);

            switch (select) {
                case 1:
                    replyEnquiryView(campUID);
                    break;
                case 2:
                    generateEnquiryReportView(campUID);
                    break;
                case 3:
                    return;
                default:
                    break;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Renders view for users to reply to enquiries for a specific camp and update the status of the replied enquiries.
     *
     * @param campUID The UUID of the camp for which enquiries are being replied to.
     */
    public static void replyEnquiryView(HashMap<Integer, UUID> enquirySelection){
        System.out.print("Select enquiry to reply to: ");
        int choice = UserIO.getSelection(1, enquirySelection.size());
        UUID enquiryUID = enquirySelection.get(choice);
        System.out.print("Enter reply: ");
        String reply = UserIO.getStringResponse();
        EnquiryManager.updateEnquiryReply(reply, enquiryUID);
        EnquiryManager.updateEnquiryStatus(enquiryUID);
    }

    /**
     * Renders view for camp committee members to reply to enquiries and update enquiry status.
     *
     * @param campUID The UUID of camp for which the enquiries are being replied to.
     * @param student The Camp Committee Member who is replying to the enquiries.
     */
    public static void ccmViewEnquiryView(UUID campUID, CampCommMember student){
        //show all enquiries of chosen camp
        try{
            Camp camp = CampManager.getCamp(campUID);
            System.out.println("======================= ENQUIRIES =======================");
            Set<UUID> enquiryKeys = camp.getEnquiryID();
            HashMap<UUID, Enquiry> allEnquiries = EnquiryManager.getAllEnquiries();
            // maps choice to UID of camp in hashmap
            HashMap<Integer, UUID> enquirySelection = new HashMap<>();
            // prints all enquiries for this camp

            int count = 0;
            for(UUID key : enquiryKeys){
                count++;
                System.out.println("Enquiry Number: " + count);
                System.out.println(key);
                allEnquiries.get(key).printEnquiryDetails();
                System.out.println("\n-----------------------\n");
                enquirySelection.put(count, key);
            }
            System.out.println("\n=========================================================\n");

            if (count == 0){
                System.out.println("No camp enquiries.");
                return;
            }
            System.out.println("Choose action:");
            System.out.println("1) Reply enquiries");
            System.out.println("2) Print enquiry report");
            System.out.println("3) Exit");
            int select = UserIO.getSelection(1, 2);
            switch (select) {
                case 1:
                    replyEnquiryView(campUID);
                    break;
                case 2:
                    generateEnquiryReportView(campUID);
                    break;
                case 3:
                    return;
                default:
                    break;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Renders view for camp committee members to reply to a specific enquiry and updates the enquiry status.
     * Additionally, adds points to the Camp Committee Member for replying to the enquiry.
     *
     * @param campUID The UUID of camp for which the enquiry is being replied to.
     * @param student The Camp Committee Member who is replying to the enquiry.
     */
    public static void replyEnquiryView(UUID campUID, CampCommMember student){
        Camp camp = CampManager.getCamp(campUID);
        System.out.println("======================= ENQUIRIES: TO BE REPLIED =======================");
        Set<UUID> enquiryKeys = camp.getEnquiryID();
        HashMap<UUID, Enquiry> allEnquiries = EnquiryManager.getAllEnquiries();
        // maps choice to UID of camp in hashmap
        HashMap<Integer, UUID> enquirySelection = new HashMap<>();
        int count = 0;
        for(UUID key : enquiryKeys){
            if (allEnquiries.get(key).getIsProcessed()) {
                continue;
            }
            count++;
            System.out.println("Enquiry Number: " + count);
            System.out.println(key);
            allEnquiries.get(key).printEnquiryDetails();
            System.out.println("\n-----------------------\n");
            enquirySelection.put(count, key);
        }
        System.out.println("\n=========================================================\n");

        if (count == 0) {
            System.out.println("No camp enquiries to be replied.");
            return;
        }
        System.out.print("Select enquiry to reply to: ");
        int choice = UserIO.getSelection(1, count);
        UUID enquiryUID = enquirySelection.get(choice);
        System.out.print("Enter reply: ");
        String reply = UserIO.getStringResponse();
        EnquiryManager.updateEnquiryReply(reply, enquiryUID);
        EnquiryManager.updateEnquiryStatus(enquiryUID);
        CampCommMemberManager.addPoint(student);
    }
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

                switch (choice){
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
