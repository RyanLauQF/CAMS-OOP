package com.cmas.controller;

import com.cmas.model.Staff;

import java.util.UUID;
import java.util.Set;


/**
 * Controller class that manages staff objects. It provides methods to interact
 * with and manipulate staff-related information such as getting camps and registering new camps
 *
 * @author Ryan Lau, Seung Yeon
 * @version 1.0
 * @since 2023-11-18
 */
public class StaffManager {

    /**
     * Adds a new camp to the specified staff member.
     *
     * @param campID The UUID of the camp to be added.
     * @param staff The Staff object to which the camp will be added.
     */
    public static void addCampToStaff(UUID campID, Staff staff){
        staff.registerNewCamp(campID);
    }

    public static void removeCampFromStaff(UUID campID, Staff staff){staff.removeCamp(campID);}
    /**
     * Retrieves a set of UUIDs representing all camps associated with the specified staff member.
     *
     * @param staff The Staff object for which to retrieve camp IDs.
     * @return A Set of UUIDs representing all camps associated with the specified staff member.
     */
    public static Set<UUID> getAllCamps(Staff staff) {
        return staff.getCampIDs();
    }
}
