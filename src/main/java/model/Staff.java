package model;

import controller.CampManager;
import database.Database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Staff extends User {

    //attributes
    private final Set<UUID> campIDs;

    //constructor
    public Staff(String name, String email, UserGroup faculty){
        //change faculty to UserGroup?
        super(name, email, faculty);
        this.campIDs = new HashSet<>();
    }

    public boolean hasCreatedCamp(UUID campID){
        return campIDs.contains(campID);
    }
    public void registerNewCamp(UUID campID) {
        campIDs.add(campID);
    }
    public Set<UUID> getCampIDs() {
        return campIDs;
    }

    public void generateReport(int choice) {
        // For each camp that the staff has created, print a view for it
        BufferedWriter writer = null;
        try {
            if (campIDs.size() == 0) {
                System.out.println("Staff has no camp created");
                return;
            }
            writer = new BufferedWriter(new FileWriter("OverallReport.txt"));
            for (UUID campid : campIDs) {
                Camp curCamp = CampManager.getCamp(campid);
                writer.write(curCamp.getName() + "\n");
                writer.write(curCamp.getDescription() + "\n");
                writer.write(curCamp.getStartDate().toString() + "\n");
                writer.write(curCamp.getEndDate().toString() + "\n");
                writer.write(curCamp.getTotalSlots() + "\n");
                writer.write(curCamp.getUserGroup() + "\n");
                writer.write(curCamp.getStaffInCharge() + "\n");
                switch (choice) {
                    case 1:
                        writer.write("List of all registered attendees: " + "\n");
                        for (String studentId : curCamp.getRegisteredAttendees()){
                            //how to get the list of all students and check here?
                            User user = Database.USER_DATA.get(studentId);
                            writer.write(user.getName() + '\n');
                        }
                        break;
                    case 2:
                        writer.write("List of all registered committee members: " + "\n");
                        for (String studentId : curCamp.getRegisteredCommMembers()){
                            //how to get the list of all students and check here?
                            User user = Database.USER_DATA.get(studentId);
                            writer.write(user.getName() + '\n');
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

    public void generatePerformanceReport() {
        BufferedWriter writer = null;
        try{
            if (campIDs.size() == 0) {
                System.out.println("Staff has no camp created");
                return;
            }
            writer = new BufferedWriter(new FileWriter("performanceReport.txt"));
            for (UUID campid : campIDs) {
                Camp curCamp = CampManager.getCamp(campid);
                writer.write(curCamp.getName() + "\n");
                writer.write(curCamp.getDescription() + "\n");
                for (String studentId : curCamp.getRegisteredCommMembers()) {
                    CampCommMember user = (CampCommMember) Database.USER_DATA.get(studentId);
                    writer.write(user.getName()+ ": " + user.getPassword() + '\n');
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
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
