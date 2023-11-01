package controller;

import database.Database;
import model.Camp;
import model.Staff;

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

    public static HashMap<UUID, Camp> getAllCamps(){
        return campsData;
    }
}
