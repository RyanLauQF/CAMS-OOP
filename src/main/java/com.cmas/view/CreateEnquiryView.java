package com.cmas.view;

import com.cmas.controller.CampManager;
import com.cmas.controller.EnquiryManager;
import com.cmas.helper.ConsoleColours;
import com.cmas.helper.UserIO;
import com.cmas.model.Camp;
import com.cmas.model.Enquiry;
import com.cmas.model.Student;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;


/**
 * The CreateEnquiryView interface provides methods for rendering views related to student-enquiry interactions.
 *
 * @author Tong Ying
 * @version 1.0
 * @since 2023-11-18
 */
public interface CreateEnquiryView{
    /**
     * Renders all the enquiries submitted by the student
     *
     * @param student The student whose enquiries are being displayed.
     */
    static void showAllSubmittedEnquiry(Student student) {

        Set<UUID> enquiryKeys = student.getSubmittedEnquiries();

        if (enquiryKeys.isEmpty()) {
            System.out.println(ConsoleColours.YELLOW + "\nThere are no available enquiries!" + ConsoleColours.RESET);
            return;
        }

        // prints all enquiry student has created
        System.out.println("\n=========================================================");
        System.out.println("---------------------------------------------------------");
        int count = 0;
        for (UUID key : enquiryKeys) {
            count++;
            System.out.println(ConsoleColours.BLUE + "Enquiry No.: " + count + ConsoleColours.RESET);
            EnquiryManager.printEnquiryDetails(key);
            System.out.println("---------------------------------------------------------");
        }

        System.out.println("=========================================================\n");
    }

    /**
     * Renders view for student to create a new enquiry for a selected camp.
     *
     * @param student The student creating the enquiry.
     */
    static void createEnquiryView(Student student) {
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
    static void editEnquiryView(Student student) {
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
    static void deleteEnquiryView(Student student) {
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
}
