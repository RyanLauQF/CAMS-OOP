package com.cmas.view;

import com.cmas.controller.CampManager;
import com.cmas.helper.UserIO;
import com.cmas.model.*;

import java.util.UUID;

/**
 * The GenerateEnquiryReportView interface provides a method to display a menu for users to select filters
 * when generating an enquiry report. Users can choose to view all enquiries, pending enquiries
 * (those that have yet to be replied), or enquiries that have already been replied.
 */
public interface GenerateEnquiryReportView {

    /**
     * Displays menu for users to select filter when generating enquiry report. Users can choose to view all
     * enquiries, pending enquiries (those that have yet to be replied) or enquiries that have already been replied.
     *
     * @param campUID The UUID of camp for which the enquiry report is to be generated
     */
    static void generateEnquiryReportView(UUID campUID) {
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
