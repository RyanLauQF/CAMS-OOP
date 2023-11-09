package controller;

import model.Staff;

import java.util.UUID;
import java.util.Set;


public class StaffManager {
    public static void addCampToStaff(UUID campID, Staff staff){
        staff.registerNewCamp(campID);
    }
    public static Set<UUID> getAllCamps(Staff staff) {
        return staff.getCampIDs();
    }
}
