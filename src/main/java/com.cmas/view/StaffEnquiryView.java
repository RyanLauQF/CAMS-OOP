package com.cmas.view;

import com.cmas.controller.CampManager;
import com.cmas.controller.EnquiryManager;
import com.cmas.controller.StaffManager;
import com.cmas.helper.ConsoleColours;
import com.cmas.helper.UserIO;
import com.cmas.model.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


/**
 * The StaffEnquiryView class provides methods for rendering views related to staff-enquiry interactions
 * and implementing the GenerateEnquiryReportView interface.
 *
 * @author Tong Ying, Shao Chong
 * @version 1.0
 * @since 2023-11-18
 */
public class StaffEnquiryView implements GenerateEnquiryReportView {

    /**
     * Displays the view for showing all enquiries for all camps managed by a specific Staff member.
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
     * Renders view for staff members to reply to camp-specific enquiries and update enquiry status.
     *
     * @param campUID The UUID of camp for which the enquiries are being replied to.
     * @param staff    The Staff Member who is replying to the enquiries.
     */
    public static void viewEnquiryView(UUID campUID, Staff staff) {
        //show all enquiries of chosen camp
        try {
            Camp camp = CampManager.getCamp(campUID);
            Set<UUID> enquiryKeys = camp.getEnquiryID();
            if (enquiryKeys.isEmpty()) {
                System.out.println(ConsoleColours.YELLOW + "\nThere are no available enquiries!" + ConsoleColours.RESET);
                return;
            }
            System.out.println("======================= ENQUIRIES =======================");

            // prints all enquiries for this camp
            System.out.println("-----------------------");
            int count = 0;
            for (UUID key : enquiryKeys) {
                count++;
                System.out.println(ConsoleColours.BLUE + "Enquiry No.: " + count + ConsoleColours.RESET);
                EnquiryManager.printEnquiryDetails(key);
                System.out.println("-----------------------");
            }
            System.out.println("=========================================================\n");

            System.out.println("Choose action:");
            System.out.println("1) Reply enquiries");
            System.out.println("2) Print enquiry report");
            System.out.println("3) Exit");
            int select = UserIO.getSelection(1, 3);
            switch (select) {
                case 1:
                    replyEnquiryView(campUID, staff);
                    break;
                case 2:
                    GenerateEnquiryReportView.generateEnquiryReportView(campUID);
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
     * Renders view for staff members to reply to a specific enquiry and updates the enquiry status.
     *
     * @param campUID The UUID of camp for which the enquiry is being replied to.
     * @param replier The Staff Member who is replying to the enquiry.
     */
    public static void replyEnquiryView (UUID campUID, Staff replier) {
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
    }

}
