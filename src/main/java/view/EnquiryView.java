package view;

import controller.CampCommMemberManager;
import controller.CampManager;
import controller.EnquiryManager;
import helper.UserIO;
import model.*;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class EnquiryView {
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
                System.out.println(e.toString());
            }
        }
    }

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

    public static void viewEnquiryView(UUID campUID){
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
                System.out.print("No camp enquiries.");
                return;
            }
            System.out.println("Choose action:");
            System.out.println("1) Reply enquiries");
            System.out.println("2) Exit");
            int select = UserIO.getSelection(1, 2);
            if (select == 1){
                replyEnquiryView(campUID);
            }
            else {
                return;
            }
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public static void replyEnquiryView(UUID campUID){
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

        if (count == 0){
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
    }

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
            System.out.println("2) Exit");
            int select = UserIO.getSelection(1, 2);
            if (select == 1){
                replyEnquiryView(campUID, student);
            }
            else {
                return;
            }
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

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

        if (count == 0){
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
}
