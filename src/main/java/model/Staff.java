package model;

import controller.CampManager;
import controller.UserManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


/**
 * Model class representing a staff member, inheriting properties from the User class.
 * Staff members have the ability to create and manage camps and generate various reports for camp-related activities.
 *
 * @author Ryan Lau, Seung Yeon, Markus Lim, Tong Ying, Shao Chong
 * @version 1.0
 * @since 2023-11-14
 */
public class Staff extends User {

    /**
     * Set of UUIDs for each camp created by the staff member.
     */
    private final Set<UUID> campIDs;

    /**
     * Constructs a new Staff object with the name, email, and user group).
     *
     * @param name    The name of the staff.
     * @param email   The email of the staff.
     * @param faculty The user group for which the staff member belongs.
     */
    //constructor
    public Staff(String name, String email, UserGroup faculty){
        //change faculty to UserGroup?
        super(name, email, faculty);
        this.campIDs = new HashSet<>();
    }
    /**
     * Checks if staff has created camp.
     *
     * @param campID The UUID of the camp to check.
     * @return true if the staff member has created the camp, false otherwise.
     */
    public boolean hasCreatedCamp(UUID campID){
        return campIDs.contains(campID);
    }
    /**
     * Registers a new camp created by the staff member.
     * Adds campID to Set<UUID> for tracking.
     *
     * @param campID The UUID of the camp to register.
     */
    public void registerNewCamp(UUID campID) {
        campIDs.add(campID);
    }

    /**
     * Gets the set of UUIDs representing the camps created by the staff member.
     *
     * @return The set of camp UUIDs.
     */
    public Set<UUID> getCampIDs() {
        return campIDs;
    }

    /**
     * Generates a report (.txt format) based on the specified choice.
     * Implemented filters include Attendees, Camp Committee Members
     *
     * @param choice The choice for which the report is generated (1 for attendees, 2 for committee members).
     */
    public void generateReport(int choice) {
        // For each camp that the staff has created, print a view for it
        if (campIDs.size() == 0) {
            System.out.println("Staff has no camp created");
            return;
        }
        BufferedWriter writer = null;
        String x = choice == 1 ? "Attendees" : "Camp Committee Members";
        try {

            System.out.println("Generating Report for " + x + "...\n");

            writer = new BufferedWriter(new FileWriter("OverallReport.txt"));
            writer.write("============================================== " + "\n");
            writer.write("===== OVERALL CAMP REPORT FOR " + x + " =====\n");
            writer.write("============================================== " + "\n\n");

            for (UUID campid : campIDs) {
                Camp curCamp = CampManager.getCamp(campid);
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
                        if (curCamp.getRegisteredAttendees().isEmpty()) {
                            writer.write("No current Attendees \n");
                            writer.write("============================================== " + "\n\n");
                            break;
                        }
                        writer.write("List of all registered attendees: " + "\n");
                        int count = 0;
                        for (String studentId : curCamp.getRegisteredAttendees()){
                            //how to get the list of all students and check here?
                            count++;
                            User user = UserManager.getUser(studentId);
                            writer.write(count + ": " + user.getName() + '\n');
                            writer.write("============================================== " + "\n\n");
                        }
                        break;
                    case 2:
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
    /**
     * Generates a performance report (points) for camp committee members associated with the camps created by the staff member.
     */
    public void generatePerformanceReport() {
        if (campIDs.size() == 0) {
            System.out.println("Staff has no camp created");
            return;
        }
        BufferedWriter writer = null;
        try{
          
            System.out.println("Generating Performance Report for Camp Committee Members...\n");

            writer = new BufferedWriter(new FileWriter("PerformanceReport.txt"));
            writer.write("================================================ " + "\n");
            writer.write(" PERFORMANCE REPORT FOR CAMP COMMITTEE MEMBERS " + "\n");
            writer.write("================================================ " + "\n\n");
            for (UUID campid : campIDs) {
                writer.write("Committee Members: ");
                Camp curCamp = CampManager.getCamp(campid);
                writer.write("Camp Name: " + curCamp.getName() + "\n");
                writer.write("Camp Description: " + curCamp.getDescription() + "\n");
                writer.write("============================================== " + "\n");

                if (curCamp.getRegisteredCommMembers().size() == 0) {
                    writer.write("No current Committee Members\n");
                    writer.write("============================================== " + "\n\n");
                    return;
                }
                int count = 0;
                writer.write("List of All Committee Members\n");
                for (String studentId : curCamp.getRegisteredCommMembers()) {
                    count++;
                    CampCommMember user = (CampCommMember) UserManager.getUser(studentId);
                    writer.write(count + ": " + user.getName()+ ": " + user.getPoints() + '\n');
                }
                writer.write("============================================== " + "\n\n");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
