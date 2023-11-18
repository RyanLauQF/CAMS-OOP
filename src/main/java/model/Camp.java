package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


/**
 * Model class representing a camp and manages camp-related operations.
 *
 * @author Shao Chong
 * @version 1.0
 * @since 2023-11-18
 */
public class Camp implements Serializable {
    // Camp Information
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate closingDate;
    private UserGroup userGroup;
    private String location;
    private int totalSlots;
    private String description;
    private Staff staffInCharge;
    private boolean isVisible;

    private final Set<String> registeredAttendeeID;
    private final Set<String> registeredCommMemID;
    private final Set<String> withdrawnID;
    private final Set<UUID> enquiryID;
    private final Set<UUID> suggestionID;

    /**
     * Constructs a Camp object with the specified parameters.
     *
     * @param name         The name of the camp.
     * @param startDate    The start date of the camp.
     * @param endDate      The end date of the camp.
     * @param closingDate  The closing date for camp registration.
     * @param userGroup    The user group associated with the camp.
     * @param location     The location of the camp.
     * @param totalSlots   The total number of available slots in the camp.
     * @param description  The description of the camp.
     * @param staffInCharge The staff member in charge of the camp.
     * @param isVisible    Indicates whether the camp is visible.
     */
    public Camp(String name, LocalDate startDate, LocalDate endDate, LocalDate closingDate, UserGroup userGroup, String location, int totalSlots, String description, Staff staffInCharge, boolean isVisible) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.closingDate = closingDate;
        this.userGroup = userGroup;
        this.location = location;
        this.totalSlots = totalSlots;
        this.description = description;
        this.staffInCharge = staffInCharge;
        this.isVisible = isVisible;
        this.registeredAttendeeID = new HashSet<>();
        this.registeredCommMemID = new HashSet<>();
        this.withdrawnID = new HashSet<>();
        this.enquiryID = new HashSet<>();
        this.suggestionID = new HashSet<>();
    }

    // ----- GENERATE REPORT -----
    /**
     * Prints the details of the camp to the console.
     */
    public void printCampDetails() {
        System.out.println("---------------------------------------------------------");
        System.out.println("|                     CAMP DETAILS                      |");
        System.out.println("---------------------------------------------------------");
        System.out.printf("| %-26s | %-24s |\n", "Name:", name);
        System.out.printf("| %-26s | %-24s |\n", "Start Date:", startDate.toString());
        System.out.printf("| %-26s | %-24s |\n", "End Date:", endDate.toString());
        System.out.printf("| %-26s | %-24s |\n", "Registration Closing Date:", closingDate.toString());
        System.out.printf("| %-26s | %-24s |\n", "User Group:", userGroup);
        System.out.printf("| %-26s | %-24s |\n", "Location:", location);
        System.out.printf("| %-26s | %-24s |\n", "Remaining Slots:", getRemainingSlots());
        System.out.printf("| %-26s | %-24s |\n", "Committee Members:", registeredCommMemID.size());
        System.out.printf("| %-26s | %-24s |\n", "Description:", description);
        System.out.printf("| %-26s | %-24s |\n", "Staff in Charge:", staffInCharge.getName() + " (" + staffInCharge.getFaculty() + ") ");
        System.out.printf("| %-26s | %-24s |\n", "Visibility:", isVisible() ? "Visible" : "Not visible");
        System.out.println("---------------------------------------------------------");
    }
    /**
     * Generates a report for the camp, including its details.
     */
    public void generateReport() {
        printCampDetails();
    }

    // to discuss on filters

    // =========================== GETTER AND SETTER FUNCTIONS =========================== //
    /**
     * Gets the remaining available slots in the camp.
     *
     * @return The remaining available slots.
     */
    public int getRemainingSlots() {
        return totalSlots - registeredAttendeeID.size() - registeredCommMemID.size();
    }

    /**
     * Gets the set of registered attendee IDs.
     *
     * @return The set of registered attendee IDs.
     */
    public Set<String> getRegisteredAttendees() {
        return registeredAttendeeID;
    }

    /**
     * Checks if a user with the given ID has registered for the camp.
     *
     * @param userID The ID of the user to check.
     * @return true if the user has registered, false otherwise.
     */
    public boolean hasRegistered(String userID) {
        return registeredAttendeeID.contains(userID) || registeredCommMemID.contains(userID);
    }

    /**
     * Adds an attendee to the set of registered attendees.
     *
     * @param attendeeID The ID of the attendee to be added.
     */
    public void addAttendee(String attendeeID) {
        registeredAttendeeID.add(attendeeID);
    }

    /**
     * Removes an attendee from the set of registered attendees.
     *
     * @param attendeeID The ID of the attendee to be removed.
     */
    public void removeAttendee(String attendeeID) {
        registeredAttendeeID.remove(attendeeID);
    }

    /**
     * Gets the set of registered committee member IDs.
     *
     * @return The set of registered committee member IDs.
     */
    public Set<String> getRegisteredCommMembers() {
        return registeredCommMemID;
    }

    /**
     * Adds a committee member to the set of registered committee members.
     *
     * @param commMemberID The ID of the committee member to be added.
     */
    public void addCommMember(String commMemberID) {
        registeredCommMemID.add(commMemberID);
    }

    /**
     * Removes a committee member from the set of registered committee members.
     *
     * @param commMemberID The ID of the committee member to be removed.
     */
    public void removeCommMember(String commMemberID) {
        registeredCommMemID.remove(commMemberID);
    }

    /**
     * Gets the set of Enquiry IDs associated with the camp.
     *
     * @return The set of Enquiry IDs.
     */
    public Set<UUID> getEnquiryID() {
        return enquiryID;
    }

    /**
     * Gets the set of Suggestion IDs associated with the camp.
     *
     * @return The set of Suggestion IDs.
     */
    public Set<UUID> getSuggestionID() {
        return suggestionID;
    }

    /**
     * Adds an Enquiry ID to the set of Enquiry IDs associated with the camp.
     *
     * @param enquiryUID The Enquiry ID to be added.
     */
    public void addEnquiry(UUID enquiryUID) {
        enquiryID.add(enquiryUID);
    }

    /**
     * Removes an Enquiry ID from the set of Enquiry IDs associated with the camp.
     *
     * @param enquiryUID The Enquiry ID to be removed.
     */
    public void removeEnquiry(UUID enquiryUID) {
        enquiryID.remove(enquiryUID);
    }

    /**
     * Adds a Suggestion ID to the set of Suggestion IDs associated with the camp.
     *
     * @param suggestionUID The Suggestion ID to be added.
     */
    public void addSuggestion(UUID suggestionUID) {
        suggestionID.add(suggestionUID);
    }

    /**
     * Removes a Suggestion ID from the set of Suggestion IDs associated with the camp.
     *
     * @param suggestionUID The Suggestion ID to be removed.
     */
    public void removeSuggestion(UUID suggestionUID) {
        suggestionID.remove(suggestionUID);
    }
    /**
     * Gets the name of the camp.
     *
     * @return The name of the camp.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the camp.
     *
     * @param name The new name for the camp.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the start date of the camp.
     *
     * @return The start date of the camp.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the camp.
     *
     * @param startDate The new start date for the camp.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the camp.
     *
     * @return The end date of the camp.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the camp.
     *
     * @param endDate The new end date for the camp.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the closing date for camp registration.
     *
     * @return The closing date for camp registration.
     */
    public LocalDate getClosingDate() {
        return closingDate;
    }

    /**
     * Sets the closing date for camp registration.
     *
     * @param closingDate The new closing date for camp registration.
     */
    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    /**
     * Gets the user group associated with the camp.
     *
     * @return The user group associated with the camp.
     */
    public UserGroup getUserGroup() {
        return userGroup;
    }

    /**
     * Sets the user group associated with the camp.
     *
     * @param userGroup The new user group for the camp.
     */
    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    /**
     * Gets the location of the camp.
     *
     * @return The location of the camp.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the camp.
     *
     * @param location The new location for the camp.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the total number of available slots in the camp.
     *
     * @return The total number of available slots in the camp.
     */
    public int getTotalSlots() {
        return totalSlots;
    }

    /**
     * Sets the total number of available slots in the camp.
     *
     * @param totalSlots The new total number of available slots for the camp.
     */
    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    /**
     * Gets the description of the camp.
     *
     * @return The description of the camp.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the camp.
     *
     * @param description The new description for the camp.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the staff member in charge of the camp.
     *
     * @return The staff member in charge of the camp.
     */
    public Staff getStaffInCharge() {
        return staffInCharge;
    }

    /**
     * Sets the staff member in charge of the camp.
     *
     * @param staffInCharge The new staff member in charge for the camp.
     */
    public void setStaffInCharge(Staff staffInCharge) {
        this.staffInCharge = staffInCharge;
    }

    /**
     * Checks if the camp is visible.
     *
     * @return true if the camp is visible, false otherwise.
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Sets the visibility status of the camp.
     *
     * @param visible The new visibility status for the camp.
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    /**
     * Gets the set of withdrawn student IDs (blacklist).
     *
     * @return The set of withdrawn student IDs.
     */
    public Set<String> getBlacklist() {
        return withdrawnID;
    }

    /**
     * Adds a student ID to the blacklist.
     *
     * @param studentID The student ID to be added to the blacklist.
     */
    public void blacklist(String studentID) {
        withdrawnID.add(studentID);
    }
}
