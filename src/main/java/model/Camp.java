package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Camp {
    // Camp Information
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate closingDate;
    private UserGroup userGroup;
    private String location;
    private int totalSlots;
    private int campCommSlots;
    private String description;
    private Staff staffInCharge;

    private ArrayList<Student> registeredStudents;
    private boolean isVisible;
    private ArrayList<Enquiry> enquiries;
    private ArrayList<Suggestion> suggestions;

    public Camp(String name, LocalDate startDate, LocalDate endDate, LocalDate closingDate, UserGroup userGroup, String location, int totalSlots, int campCommSlots, String description, Staff staffInCharge, boolean isVisible) throws Exception {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.closingDate = closingDate;
        this.userGroup = userGroup;
        this.location = location;
        if (campCommSlots > 10) {
            throw new Exception("Maximum of 10 Camp Committee Slots");
        }
        this.totalSlots = totalSlots;
        this.campCommSlots = campCommSlots;
        this.description = description;
        this.staffInCharge = staffInCharge;
        this.isVisible = isVisible;
        this.registeredStudents = new ArrayList<Student>();
        this.enquiries = new ArrayList<Enquiry>();
        this.suggestions = new ArrayList<Suggestion>();
    }

    public ArrayList<Student> getRegisteredStudents() {
        return registeredStudents;
    }

    /**
     * @param student student to be enrolled
     * @param role    "attendee" or "camp committee"
     * @throws Exception if camp is full, or if camp committee is full
     */
    public void registerStudent(Student student, StudentRole role) throws Exception {
        if (registeredStudents.size() == totalSlots) {
            throw new Exception("Camp is fully registered");
        }
        if (role == StudentRole.CampComm) {
            if (registeredStudents.stream().filter(s -> s instanceof CampCommMember).count() == campCommSlots) {
                throw new Exception("Camp committee is full");
            }
            // should we downcast? I think there are attributes of camp comm members that students don't have (points etc.), it is possible to use a constructor instead
            // do we need to return a camp comm member? not sure if down-casting the REFERENCE affects the actual object.
            CampCommMember campCommMember = (CampCommMember) student;
            registeredStudents.add(campCommMember);
        } else if (role == StudentRole.Attendee) {
            registeredStudents.add(student);
        }
        // can abstract to an enum to manage roles
        throw new Exception("Invalid role");
    }

    public void withdrawStudent(Student student) throws Exception {
        if (!registeredStudents.contains((student))) {
            throw new Exception(("Student is not registered"));
        }
        // should this validation occur here?
        if (student instanceof CampCommMember) {
            throw new Exception(("Camp Committee Members cannot withdraw"));
        }
        registeredStudents.remove(student);
    }

    public int getRemainingSlots() {
        return totalSlots - registeredStudents.size();
    }

    public void addEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
    }

    public void addSuggestion(Suggestion suggestion) {
        suggestions.add(suggestion);
    }

    // ----- GENERATE REPORT -----

    public void printCampDetails() {
        System.out.println("----- CAMP DETAILS -----");
        System.out.println("Name: " + name);
        System.out.println("Date: " + startDate.toString() + " - " + endDate.toString());
        System.out.println("Registration closing date: " + closingDate.toString());
        System.out.println("User Group: " + userGroup);
        System.out.println("Location: " + location);
        System.out.println("Total Slots: " + totalSlots);
        System.out.println("Camp Committee Slots: " + campCommSlots);
        System.out.println("Description: " + description);
        System.out.println("Staff in charge: " + "***** TO IMPLEMENT *****");
    }

    public void printAttendees() {
        System.out.println("----- ATTENDEE LIST -----");
        for (Student attendee :
                registeredStudents) {

        }
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

    // ----- MISC. GETTERS AND SETTERS -----

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

    public int getCampCommSlots() {
        return campCommSlots;
    }

    public void setCampCommSlots(int campCommSlots) {
        this.campCommSlots = campCommSlots;
    }

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

    public ArrayList<Enquiry> getEnquiries() {
        return enquiries;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public ArrayList<Suggestion> getSuggestions() {
        return suggestions;
    }
}
