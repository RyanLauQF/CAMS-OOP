package com.cmas.view;

import com.cmas.controller.CampCommMemberManager;
import com.cmas.controller.CampManager;
import com.cmas.controller.EnquiryManager;
import com.cmas.helper.ConsoleColours;
import com.cmas.helper.UserIO;
import com.cmas.model.*;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class CCMEnquiryView extends StudentEnquiryView implements GenerateEnquiryReportView {

    /**
     * Renders view for camp committee members to reply to camp-specific enquiries and update enquiry status.
     *
     * @param campUID The UUID of camp for which the enquiries are being replied to.
     * @param ccm    The Camp Committee Member who is replying to the enquiries.
     */
    public static void viewEnquiryView(UUID campUID, CampCommMember ccm) {
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
                    replyEnquiryView(campUID, ccm);
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
     * Renders view for camp committee members to reply to a specific enquiry and updates the enquiry status.
     * Additionally, adds points to the Camp Committee Member for replying to the enquiry.
     *
     * @param campUID The UUID of camp for which the enquiry is being replied to.
     * @param replier The Camp Committee Member who is replying to the enquiry.
     */
    public static void replyEnquiryView (UUID campUID, CampCommMember replier) {
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

        CampCommMemberManager.addPoint(replier);

    }

}



