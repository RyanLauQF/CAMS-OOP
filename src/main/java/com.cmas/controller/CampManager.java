package com.cmas.controller;

import com.cmas.database.Database;
import com.cmas.filter.IFilter;
import com.cmas.model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;
import java.util.Set;


/**
 * Controller class that manages all operations related to camps, including camp registration and withdrawal for
 * both students and comm members, updating camp details, and generating reports.
 *
 * @author Ryan Lau, Shao Chong, Markus Lim, Tong Ying, Seung Yeon
 * @version 1.0
 * @since 2023-11-14
 */
public class CampManager {

    /**
     * HashMap that stores camp data, where the keys are UUIDs and values are camps.
     */
    private static final HashMap<UUID, Camp> campsData = Database.CAMP_DATA;

    /**
     * Adds a new camp to the system, generating a unique UUID for the camp.
     *Tags the created camp with the provided staff.
     *
     * @param camp  The camp to be added.
     * @param staff The staff in charge of the camp.
     */
    public static void addNewCamp(Camp camp, Staff staff) {
        // generate a unique key for each camp to be stored in database
        UUID uid = UUID.randomUUID();
        while (campsData.containsKey(uid)) {
            uid = UUID.randomUUID();
        }

        campsData.put(uid, camp);
        StaffManager.addCampToStaff(uid, staff);
    }

    public static void deleteCamp(UUID campUID, Staff staff){
        campsData.remove(campUID);
        StaffManager.removeCampFromStaff(campUID, staff);
    }

    // ================== CAMP REGISTRATION ================== //

    /**
     * Registers a student for a camp.
     * Checks for registration deadlines, camp availability, withdrawal status.
     *
     * @param student The student to register.
     * @param campID  The UUID of the camp to register for.
     * @throws Exception If registration fails due to specific conditions.
     */
    public static void registerStudent(Student student, UUID campID) throws Exception {
        Camp camp = getCamp(campID);
        String studentID = student.getUserID();
        if (camp.getBlacklist().contains(studentID)) {
            throw new Exception("You withdrew from this camp previously. You may not register for this camp again.");
        } else if (camp.hasRegistered(studentID)) {
            throw new Exception("You have already registered in this camp!");
        } else if (camp.getClosingDate().isBefore(LocalDate.now())) {
            throw new Exception("Registration for this camp has closed!");
        } else if (camp.getRemainingSlots() <= 0) {
            throw new Exception("Camp is full! Unable to Register.");
        } else if (hasClashingDates(student, campID)) {
            throw new Exception(("Camp date clashes with another registered camp!"));
        }

        student.registerForCamp(campID);
        camp.addAttendee(studentID);
    }

    /**
     * Registers a student as a camp committee member.
     * Checks if current student is already a committee member and the current camp availability
     *
     * @param student The student to register as a committee member.
     * @param campID  The UUID of the camp to register for.
     * @throws Exception If registration fails due to specific conditions.
     */
    public static void registerCommMember(Student student, UUID campID) throws Exception {

        Camp camp = getCamp(campID);
        String studentID = student.getUserID();
        if (camp.getBlacklist().contains(studentID)) {
            throw new Exception("You withdrew from this camp previously. You may not register for this camp again.");
        } else if (camp.hasRegistered(studentID)) {
            throw new Exception("You have already registered in this camp!");
        } else if (camp.getClosingDate().isBefore(LocalDate.now())) {
            throw new Exception("Registration for this camp has closed!");
        } else if (student.isCampCommitteeMember()) {
            throw new Exception("You are already involved in another camp committee. You can only be in the camp committee for one camp.");
        } else if (camp.getRemainingSlots() <= 0) {
            throw new Exception("Camp is full! Unable to Register.");
        } else if (camp.getRegisteredCommMembers().size() >= 10) {
            throw new Exception("Camp committee is full");
        } else if (hasClashingDates(student, campID)) {
            throw new Exception(("Camp date clashes with another registered camp!"));
        }

        student.registerForCamp(campID);
        UserManager.registerStudentAsCommMember(student, campID);
        camp.addCommMember(studentID);
    }

    /**
     * Withdraws a student from a camp.
     *
     * @param student The student to withdraw from the camp.
     * @param campID  The UUID of the camp from which the student is withdrawing.
     * @throws Exception If withdrawal fails due to specific conditions.
     */
    public static void withdrawStudent(Student student, UUID campID) throws Exception {
        Camp camp = getCamp(campID);
        String studentID = student.getUserID();

        if (!camp.hasRegistered(studentID)) {
            throw new Exception("You are not registered");
        } else if (camp.getRegisteredCommMembers().contains(studentID)) {
            throw new Exception("Camp Committee Members cannot withdraw");
        } else {
            camp.removeAttendee(studentID);
            camp.blacklist(studentID);
            student.withdrawFromCamp(campID);
        }
    }

    // ================== GETTER FUNCTIONS ================== //

    /**
     * Retrieves a HashMap containing all camps.
     *
     * @return A HashMap of camps, where keys are UUIDs and values are camps.
     */
    public static HashMap<UUID, Camp> getAllCamps() {
        return campsData;
    }


    /**
     * Retrieves a specific camp based on the provided UUID.
     *
     * @param campUID The UUID of the camp to retrieve.
     * @return The camp associated with the provided UUID.
     */
    public static Camp getCamp(UUID campUID) {
        return campsData.get(campUID);
    }

    /**
     * Retrieves all camps in faculty based on the provided faculty.
     *
     * @param camps The UUID of the set of camps.
     * @param filter The Filter that the user want to implement
     * @return A HashMap of all camps in faculty, where keys are UUIDs and values are Camps.
     */
    public static HashMap<UUID, Camp> getFilteredCamps(Set<UUID> camps, IFilter filter) {
        return filter.filterBy(camps);
    }

    /**
     * Retrieves all camps in faculty based on the provided faculty.
     *
     * @param faculty The UserGroup of the camp to retrieve.
     * @return A HashMap of all camps in faculty, where keys are UUIDs and values are Camps.
     */
    public static HashMap<UUID, Camp> getCampInFaculty(UserGroup faculty) {
        HashMap<UUID, Camp> filteredCamps = new HashMap<>();
        for (UUID key : getAllCamps().keySet()) {
            Camp camp = getCamp(key);
            if ((camp.getUserGroup() == faculty || camp.getUserGroup() == UserGroup.NTU) && camp.isVisible()) {
                filteredCamps.put(key, camp);
            }
        }
        return filteredCamps;
    }

    public static Set<UUID> getCampEnquiries(UUID campUID) {
        Camp camp = getCamp(campUID);
        return camp.getEnquiryID();
    }
    /**
     * Retrieves all suggestions associated with the provided campID.
     *
     * @param campUID The UUID of the camp to retrieve suggestions.
     * @return A Set of UUIDs of Suggestions.
     */
    public static Set<UUID> getCampSuggestions(UUID campUID) {
        Camp camp = getCamp(campUID);
        return camp.getSuggestionID();
    }

    // ================== FUNCTIONS TO EDIT CAMP DETAILS ================== //
    /**
     * Updates the camp name for camp based on the provided UUID with the provided string.
     *
     * @param campUID The UUID of the camp.
     * @param name The string to update.
     */
    public static void updateCampName(UUID campUID, String name) {
        Camp camp = getCamp(campUID);
        camp.setName(name);
    }

    /**
     * Updates the start date for camp based on the provided UUID with the provided startDate.
     *
     * @param campUID The UUID of the camp.
     * @param startDate The LocalDate to update.
     */
    public static void updateStartDate(UUID campUID, LocalDate startDate) {
        Camp camp = getCamp(campUID);
        camp.setStartDate(startDate);
    }

    /**
     * Updates the end date for camp based on the provided UUID with the provided endDate.
     *
     * @param campUID The UUID of the camp.
     * @param endDate The LocalDate to update.
     */
    public static void updateEndDate(UUID campUID, LocalDate endDate) {
        Camp camp = getCamp(campUID);
        camp.setEndDate(endDate);
    }

    /**
     * Updates the closing registration date for camp based on the provided UUID with the provided closeDate.
     *
     * @param campUID The UUID of the camp.
     * @param closeDate The LocalDate to update.
     */
    public static void updateClosingDate(UUID campUID, LocalDate closeDate) {
        Camp camp = getCamp(campUID);
        camp.setClosingDate(closeDate);
    }

    /**
     * Updates the user group (faculty) for camp based on the provided UUID with the provided group.
     *
     * @param campUID The UUID of the camp.
     * @param group The LocalDate to update.
     */
    public static void updateStudentGroup(UUID campUID, UserGroup group) {
        Camp camp = getCamp(campUID);
        camp.setUserGroup(group);
    }

    /**
     * Updates the location for camp based on the provided UUID with the provided location.
     *
     * @param campUID  The UUID of the camp to update.
     * @param location The new location for the camp.
     */
    public static void updateLocation(UUID campUID, String location) {
        Camp camp = getCamp(campUID);
        camp.setLocation(location);
    }

    /**
     * Updates the number of slots for camp based on the provided UUID with the provided total slots.
     *
     * @param campUID    The UUID of the camp to update.
     * @param totalSlots The new total number of slots for the camp.
     */
    public static void updateNumSlots(UUID campUID, int totalSlots) {
        Camp camp = getCamp(campUID);
        camp.setTotalSlots(totalSlots);
    }

    /**
     * Updates the description for camp based on the provided UUID with the provided description.
     *
     * @param campUID     The UUID of the camp to update.
     * @param description The new description for the camp.
     */
    public static void updateDescription(UUID campUID, String description) {
        Camp camp = getCamp(campUID);
        camp.setDescription(description);
    }
    /**
     * Updates the visibility for camp based on the provided UUID.
     *
     * @param campUID     The UUID of the camp to update.
     */
    public static void updateVisibility(UUID campUID) {
        Camp camp = getCamp(campUID);
        boolean visible = camp.isVisible();
        camp.setVisible(!visible);
    }

    /**
     * Updates an enquiry for camp based on the provided UUID and the enquiryUID.
     *
     * @param campUID    The UUID of the camp to update.
     * @param enquiryUID The UUID of the enquiry to add to the camp.
     */
    public static void updateEnquiry(UUID campUID, UUID enquiryUID) {
        Camp camp = getCamp(campUID);
        camp.addEnquiry(enquiryUID);
    }

    /**
     * Deletes an enquiry for camp based on the provided UUID and the enquiryUID.
     *
     * @param campUID    The UUID of the camp to update.
     * @param enquiryUID The UUID of the enquiry to remove from the camp.
     */
    public static void deleteEnquiry(UUID campUID, UUID enquiryUID) {
        Camp camp = getCamp(campUID);
        camp.removeEnquiry(enquiryUID);
    }

    /**
     * Updates a suggestion for camp based on the provided UUID and the suggestionUID.
     *
     * @param campUID       The UUID of the camp to update.
     * @param suggestionUID The UUID of the suggestion to add to the camp.
     */
    public static void updateSuggestion(UUID campUID, UUID suggestionUID) {
        Camp camp = getCamp(campUID);
        camp.addSuggestion(suggestionUID);
    }

    /**
     * Removes a suggestion for camp based on the provided UUID and the suggestionUID.
     *
     * @param campUID       The UUID of the camp to update.
     * @param suggestionUID The UUID of the suggestion to remove from the camp.
     */
    public static void removeSuggestionfromCamp(UUID campUID, UUID suggestionUID) {
        Camp camp = getCamp(campUID);
        camp.removeSuggestion(suggestionUID);
    }

    // ================== UTILITY FUNCTIONS ================== //

    /**
     * Checks if date clashes between a new camp and existing camps registered by a student, preventing student from registering with camps that have clashing dates.
     *
     * @param student The student for whom to check date clashes.
     * @param campUID The UUID of the new camp.
     * @return True if there is a date clash, false otherwise.
     */
    public static boolean hasClashingDates(Student student, UUID campUID) {
        Set<UUID> campKeys = student.getRegisteredCamps();
        Camp newCamp = CampManager.getCamp(campUID);
        for (UUID key : campKeys) {
            Camp currentCamp = CampManager.getCamp(key);
            if (currentCamp.getStartDate().isBefore(newCamp.getEndDate().plusDays(1)) && currentCamp.getEndDate().isAfter(newCamp.getEndDate()) // end of new overlaps
                    || currentCamp.getEndDate().plusDays(1).isAfter(newCamp.getStartDate()) && currentCamp.getEndDate().isBefore(newCamp.getEndDate()) // start of new overlaps
            ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates a report for a specific camp based on the specified filter.
     * The report includes details such as camp name, description, start date, end date, total slots, user group, and staff in charge.
     * Specified filters include filtering based on student roles (Attendees / Camp Comm Members)
     *
     * @param campID The UUID of the camp for which to generate the report.
     * @param filter The filter indicating which information to include in the report (0 for all, 1 for attendees, 2 for committee members).
     * @throws IOException If an I/O error occurs while writing the report to a file.
     */
    public static void generateReport(UUID campID, int filter) throws IOException {
        Camp camp = getCamp((campID));
        BufferedWriter writer = new BufferedWriter(new FileWriter("OverallReport.txt"));
        writer.write("============================================== " + "\n");
        writer.write("===== OVERALL CAMP REPORT FOR " + camp.getName() + " =====\n");
        writer.write("============================================== " + "\n\n");
        writer.write("Camp Name: " + camp.getName() + "\n");
        writer.write("Camp Description: " + camp.getDescription() + "\n");
        writer.write("Camp Start Date: " + camp.getStartDate().toString() + "\n");
        writer.write("Camp End Date: " + camp.getEndDate().toString() + "\n");
        writer.write("Total Camp Slots: " + camp.getTotalSlots() + "\n");
        writer.write("Camp User Group: " + camp.getUserGroup() + "\n");
        writer.write("Camp Staff in Charge: " + camp.getStaffInCharge().getName() + "\n");
        writer.write("============================================== " + "\n");
        if (filter == 0 || filter == 1) {
            writer.write("============================================== " + "\n");
            writer.write("List of all registered attendees: " + "\n");
            if (camp.getRegisteredAttendees().isEmpty()) {
                writer.write("No current Attendees \n");
            } else {
                int count = 0;
                for (String studentId : camp.getRegisteredAttendees()) {
                    //how to get the list of all students and check here?
                    count++;
                    User user = UserManager.getUser(studentId);
                    writer.write(count + ": " + user.getName() + '\n');
                }
            }
            writer.write("============================================== " + "\n");
        }
        if (filter == 0 || filter == 2) {
            writer.write("============================================== " + "\n");
            writer.write("List of all registered committee members: " + "\n");
            if (camp.getRegisteredCommMembers().isEmpty()) {
                writer.write("No current Committee Members \n");
            } else {

                int count = 0;
                for (String studentId : camp.getRegisteredCommMembers()) {
                    //how to get the list of all students and check here?
                    count++;
                    User user = UserManager.getUser(studentId);
                    writer.write(user.getName() + '\n');
                }
            }
            writer.write("============================================== " + "\n\n");
        }
        writer.close();
    }

    // ----- UNIMPLEMENTED METHODS -----
    public static void generatePerformanceReport() throws Exception {
        throw new Exception("generatePerformanceReport has no implementation");
    }
}
