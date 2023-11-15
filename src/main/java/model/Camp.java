package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
    private final Set<UUID> enquiryID;
    private final Set<UUID> suggestionID;

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
        this.enquiryID = new HashSet<>();
        this.suggestionID = new HashSet<>();
    }

    // ----- GENERATE REPORT -----

    public void printCampDetails() {
        System.out.println("----- CAMP DETAILS -----");
        System.out.println("Name: " + name);
        System.out.println("Date: " + startDate.toString() + " - " + endDate.toString());
        System.out.println("Registration closing date: " + closingDate.toString());
        System.out.println("User Group: " + userGroup);
        System.out.println("Location: " + location);
        System.out.println("Remaining Slots: " + getRemainingSlots());
        System.out.println("Number of Committee Members: " + registeredCommMemID.size());
        System.out.println("Description: " + description);
        System.out.println("Staff in charge: " + staffInCharge.getName() + " | " + staffInCharge.getFaculty());
        System.out.println("-----------------------");
    }

    public void generateReport() {
        printCampDetails();
    }

    // to discuss on filters
    public void generateReport(String filter) {
        switch (filter) {
            case "attendee": {

            }
            case "camp committee": {

            }
            default:
                throw new IllegalStateException("Unexpected value: " + filter);
        }
    }

    // ----- UNIMPLEMENTED METHODS -----
    public void generatePerformanceReport() throws Exception {
        throw new Exception("generatePerformanceReport has no implementation");
    }

    // =========================== GETTER AND SETTER FUNCTIONS =========================== //
    public int getRemainingSlots() {
        return totalSlots - registeredAttendeeID.size() - registeredCommMemID.size();
    }
    public Set<String> getRegisteredAttendees() {
        return registeredAttendeeID;
    }

    public boolean hasRegistered(String userID){
        return registeredAttendeeID.contains(userID) || registeredCommMemID.contains(userID);
    }
    public void addAttendee(String attendeeID){
        registeredAttendeeID.add(attendeeID);
    }

    public void removeAttendee(String attendeeID){
        registeredAttendeeID.remove(attendeeID);
    }

    public Set<String> getRegisteredCommMembers() {
        return registeredCommMemID;
    }

    public void addCommMember(String commMemberID){
        registeredCommMemID.add(commMemberID);
    }

    public void removeCommMember(String commMemberID){
        registeredCommMemID.remove(commMemberID);
    }

    public Set<UUID> getEnquiryID() {
        return enquiryID;
    }

    public Set<UUID> getSuggestionID() {
        return suggestionID;
    }

    public void addEnquiry(UUID enquiryUID) {
        enquiryID.add(enquiryUID);
    }

    public void removeEnquiry(UUID enquiryUID) {
        enquiryID.remove(enquiryUID);
    }

    public void addSuggestion(UUID suggestionUID) {
        suggestionID.add(suggestionUID);
    }

    public void removeSuggestion(UUID suggestionUID) {
        suggestionID.remove(suggestionUID);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotalSlots() { return totalSlots; }
    public void setTotalSlots(int totalSlots) { this.totalSlots = totalSlots; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Staff getStaffInCharge() {
        return staffInCharge;
    }

    public void setStaffInCharge(Staff staffInCharge) {
        this.staffInCharge = staffInCharge;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
