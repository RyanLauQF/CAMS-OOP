package controller;

import model.Staff;

import java.util.UUID;

public class StaffManager {
    public static void addCampToStaff(UUID campID, Staff staff){
        staff.registerNewCamp(campID);
    }
}
