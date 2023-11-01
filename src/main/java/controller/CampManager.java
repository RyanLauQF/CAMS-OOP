package controller;

import database.Database;
import model.Camp;
import model.Staff;
import model.Student;
import model.UserGroup;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;

public class CampManager {

    private static final HashMap<UUID, Camp> campsData = Database.CAMP_DATA;

    public static void addNewCamp(Camp camp, Staff staff){
        // generate a unique key for each camp to be stored in database
        UUID uid = UUID.randomUUID();
        while(campsData.containsKey(uid)){
            uid = UUID.randomUUID();
        }

        campsData.put(uid, camp);
        StaffManager.addCampToStaff(uid, staff);
    }

    // ================== CAMP REGISTRATION ================== //
    public static void registerStudent(Student student, UUID campID) throws Exception {
        Camp camp = getCamp(campID);
        String studentID = student.getUserID();

        if(camp.getRemainingSlots() <= 0){
            throw new Exception("Camp is full! Unable to Register.");
        }
        else if(camp.hasRegistered(studentID)){
            throw new Exception("You have already registered in this camp!");
        }

        student.registerForCamp(campID);
        camp.addAttendee(studentID);
    }

    public static void registerCommMember(Student student, UUID campID) throws Exception {
        Camp camp = getCamp(campID);
        String studentID = student.getUserID();

        if(camp.getRemainingSlots() <= 0){
            throw new Exception("Camp is full! Unable to Register.");
        }
        else if(camp.getRegisteredCommMembers().size() >= 10){
            throw new Exception("Camp committee is full");
        }
        else if(camp.hasRegistered(studentID)){
            throw new Exception("You have already registered in this camp!");
        }

        student.registerForCamp(campID);
        student.setCampCommitteeMember(true);
        student.setCommCampID(campID);
        camp.addCommMember(studentID);
    }

    public static void withdrawStudent(Student student, UUID campID) throws Exception {
        Camp camp = getCamp(campID);
        String studentID = student.getUserID();

        if(!camp.hasRegistered(studentID)){
            throw new Exception("You are not registered");
        }
        else if(student.isCampCommitteeMember()){
            throw new Exception("Camp Committee Members cannot withdraw");
        }
        else{
            camp.removeAttendee(studentID);
            // TODO: CACHE STUDENT ID IN CAMP, TO PREVENT THEM FROM REJOINING
        }
    }

    // ================== GETTER FUNCTIONS ================== //
    public static HashMap<UUID, Camp> getAllCamps(){
        return campsData;
    }

    public static Camp getCamp(UUID campUID){
        return campsData.get(campUID);
    }

    public static HashMap<UUID, Camp> getCampInFaculty(UserGroup faculty){
        HashMap<UUID, Camp> filteredCamps = new HashMap<>();
        for(UUID key : getAllCamps().keySet()){
            Camp camp = getCamp(key);
            if(camp.getUserGroup() == faculty){
                filteredCamps.put(key, camp);
            }
        }
        return filteredCamps;
    }

    // ================== FUNCTIONS TO EDIT CAMP DETAILS ================== //
    public static void updateCampName(UUID campUID, String name){
        Camp camp = getCamp(campUID);
        camp.setName(name);
    }

    public static void updateStartDate(UUID campUID, LocalDate startDate){
        Camp camp = getCamp(campUID);
        camp.setStartDate(startDate);
    }

    public static void updateEndDate(UUID campUID, LocalDate endDate){
        Camp camp = getCamp(campUID);
        camp.setEndDate(endDate);
    }

    public static void updateClosingDate(UUID campUID, LocalDate closeDate){
        Camp camp = getCamp(campUID);
        camp.setClosingDate(closeDate);
    }

    public static void updateStudentGroup(UUID campUID, UserGroup group){
        Camp camp = getCamp(campUID);
        camp.setUserGroup(group);
    }

    public static void updateLocation(UUID campUID, String location){
        Camp camp = getCamp(campUID);
        camp.setLocation(location);
    }

    public static void updateNumSlots(UUID campUID, int totalSlots){
        Camp camp = getCamp(campUID);
        camp.setTotalSlots(totalSlots);
    }

    public static void updateDescription(UUID campUID, String description){
        Camp camp = getCamp(campUID);
        camp.setDescription(description);
    }
}
