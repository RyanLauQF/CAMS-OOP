package model;


import controller.CampManager;
import controller.UserManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CampCommMember extends Student {
    private final Set<UUID> campSuggestions;
    private UUID commCampID;

    private int points;

    public CampCommMember(Student student, UUID campID){
        // deep copy student class
        super(student.getName(), student.getEmail(), student.getFaculty());
        this.setPassword(student.getPassword());
        this.setCampCommitteeMember(true);
        this.getRegisteredCamps().addAll(student.getRegisteredCamps());
        this.getSubmittedEnquiries().addAll(student.getSubmittedEnquiries());

        // variables unique to camp committee members
        this.campSuggestions = new HashSet<>();
        this.commCampID = campID;
        this.points = 0;
    }

    public Set<UUID> getCampSuggestions(){
        return campSuggestions;
    }

    public void setCommCampID(UUID commCampID) {
        this.commCampID = commCampID;
    }

    public UUID getCommCampID() {
        return commCampID;
    }
    public void submitCampSuggestion(UUID suggestionID) {
        campSuggestions.add(suggestionID);
    }
    public void deleteCampSuggestion(UUID suggestionID){
        campSuggestions.remove(suggestionID);
    }
    public int getPoints() {return points;}
    public void addPoint() {this.points++;}
    public void generateReport(int choice) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("CampCommReport.txt"));
            writer.write("============================================== " + "\n");
            writer.write("     OVERALL CAMP REPORT FOR COMM MEMBERS    " + "\n");
            writer.write("============================================== " + "\n\n");
            Camp curCamp = CampManager.getCamp(commCampID);
            writer.write("Camp Name: " + curCamp.getName() + "\n");
            writer.write("Camp Description: " + curCamp.getDescription() + "\n");
            writer.write("Camp Start Date: " + curCamp.getStartDate().toString() + "\n");
            writer.write("Camp End Date: " + curCamp.getEndDate().toString() + "\n");
            writer.write("Total Camp Slots: " + curCamp.getTotalSlots() + "\n");
            writer.write("Camp User Group: " + curCamp.getUserGroup() + "\n");
            writer.write("Camp Staff in Charge: " + curCamp.getStaffInCharge().getName() + "\n");
            writer.write("============================================== " + "\n");

            switch (choice) {
                case 1:
                    //how can i minimise the code repetition here?
                    if (curCamp.getRegisteredAttendees().isEmpty()) {
                        writer.write("No current Attendees \n");
                        writer.write("----------------------------------------------" + "\n\n");
                        break;
                    }
                    writer.write("List of all registered attendees: " + "\n");
                    int count = 0;
                    for (String studentId : curCamp.getRegisteredAttendees()){
                        //how to get the list of all students and check here?
                        count++;
                        User user = UserManager.getUser(studentId);
                        writer.write(count + ": " + user.getName() + '\n');
                        writer.write("---------------------------------------------- " + "\n");
                    }
                    if (curCamp.getRegisteredCommMembers().isEmpty()) {
                        writer.write("No current Committee Members \n");
                        writer.write("============================================== " + "\n\n");
                        break;
                    }
                    writer.write("List of all registered committee members: " + "\n");
                    count = 0;
                    for (String studentId : curCamp.getRegisteredCommMembers()){
                        //how to get the list of all students and check here?
                        count++;
                        User user = UserManager.getUser(studentId);
                        writer.write(user.getName() + '\n');
                        writer.write("============================================== " + "\n");
                    }
                    break;

                case 2:
                    if (curCamp.getRegisteredAttendees().isEmpty()) {
                        writer.write("No current Attendees \n");
                        writer.write("============================================== " + "\n\n");
                        break;
                    }
                    writer.write("List of all registered attendees: " + "\n");
                    count = 0;
                    for (String studentId : curCamp.getRegisteredAttendees()){
                        //how to get the list of all students and check here?
                        count++;
                        User user = UserManager.getUser(studentId);
                        writer.write(count + ": " + user.getName() + '\n');
                        writer.write("============================================== " + "\n\n");
                    }
                    break;
                case 3:
                    if (curCamp.getRegisteredCommMembers().isEmpty()) {
                        writer.write("No current Committee Members \n");
                        writer.write("============================================== " + "\n\n");
                        break;
                    }
                    writer.write("List of all registered committee members: " + "\n");
                    count = 0;
                    for (String studentId : curCamp.getRegisteredCommMembers()){
                        //how to get the list of all students and check here?
                        count++;
                        User user = UserManager.getUser(studentId);
                        writer.write(user.getName() + '\n');
                        writer.write("============================================== " + "\n");
                    }
                    break;
                default:
                    break;
            }

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
}
