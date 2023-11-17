package controller;

import database.Database;
import model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;
import java.util.Set;

public class CampManager {

    private static final HashMap<UUID, Camp> campsData = Database.CAMP_DATA;

    public static void addNewCamp(Camp camp, Staff staff) {
        // generate a unique key for each camp to be stored in database
        UUID uid = UUID.randomUUID();
        while (campsData.containsKey(uid)) {
            uid = UUID.randomUUID();
        }

        campsData.put(uid, camp);
        StaffManager.addCampToStaff(uid, staff);
    }

    // ================== CAMP REGISTRATION ================== //
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
    public static HashMap<UUID, Camp> getAllCamps() {
        return campsData;
    }

    public static Camp getCamp(UUID campUID) {
        return campsData.get(campUID);
    }

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

    public static Set<UUID> getCampSuggestions(UUID campUID) {
        Camp camp = getCamp(campUID);
        return camp.getSuggestionID();
    }

    // ================== FUNCTIONS TO EDIT CAMP DETAILS ================== //
    public static void updateCampName(UUID campUID, String name) {
        Camp camp = getCamp(campUID);
        camp.setName(name);
    }

    public static void updateStartDate(UUID campUID, LocalDate startDate) {
        Camp camp = getCamp(campUID);
        camp.setStartDate(startDate);
    }

    public static void updateEndDate(UUID campUID, LocalDate endDate) {
        Camp camp = getCamp(campUID);
        camp.setEndDate(endDate);
    }

    public static void updateClosingDate(UUID campUID, LocalDate closeDate) {
        Camp camp = getCamp(campUID);
        camp.setClosingDate(closeDate);
    }

    public static void updateStudentGroup(UUID campUID, UserGroup group) {
        Camp camp = getCamp(campUID);
        camp.setUserGroup(group);
    }

    public static void updateLocation(UUID campUID, String location) {
        Camp camp = getCamp(campUID);
        camp.setLocation(location);
    }

    public static void updateNumSlots(UUID campUID, int totalSlots) {
        Camp camp = getCamp(campUID);
        camp.setTotalSlots(totalSlots);
    }

    public static void updateDescription(UUID campUID, String description) {
        Camp camp = getCamp(campUID);
        camp.setDescription(description);
    }

    public static void updateVisibility(UUID campUID) {
        Camp camp = getCamp(campUID);
        boolean visible = camp.isVisible();
        camp.setVisible(!visible);
    }

    public static void updateEnquiry(UUID campUID, UUID enquiryUID) {
        Camp camp = getCamp(campUID);
        camp.addEnquiry(enquiryUID);
    }

    public static void deleteEnquiry(UUID campUID, UUID enquiryUID) {
        Camp camp = getCamp(campUID);
        camp.removeEnquiry(enquiryUID);
    }

    public static void updateSuggestion(UUID campUID, UUID suggestionUID) {
        Camp camp = getCamp(campUID);
        camp.addSuggestion(suggestionUID);
    }

    public static void removeSuggestionfromCamp(UUID campUID, UUID suggestionUID) {
        Camp camp = getCamp(campUID);
        camp.removeSuggestion(suggestionUID);
    }

    // ================== UTILITY FUNCTIONS ================== //

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

    public static void generateReport(UUID campID, int filter) throws IOException {
        Camp camp = getCamp((campID));
        BufferedWriter writer = new BufferedWriter(new FileWriter("OverallReport.txt"));
        writer.write("============================================== " + "\n");
        writer.write("===== OVERALL CAMP REPORT FOR " + "Attendees" + " =====\n");
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
