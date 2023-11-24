package com.cmas.filter;

import com.cmas.helper.UserIO;
import com.cmas.model.Camp;
import com.cmas.controller.CampManager;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


/**
 * Utility class used to filter camps based on staff name
 *
 * @author Markus Lim
 * @version 1.0
 * @since 2023-11-23
 */
public class StaffFilter implements IFilter{

    /**
     * The staff name used as the reference for filtering camps.
     */
    private final String staffName;

    /**
     * Constructor for staff filter. Prompts user for staff name to be used as reference for filtering.
     */
    public StaffFilter() {
        System.out.print("Enter staff name: ");
        this.staffName = UserIO.getStringResponse();
    }

    /**
     * Filters a collection of camps based on name of staff who created it.
     *
     * @param camps A set of UUIDs representing camps to be filtered.
     * @return A hashmap containing camps filtered by staff name
     */
    @Override
    public HashMap<UUID, Camp> filterBy(Set<UUID> camps) {
        HashMap<UUID, Camp> op = new HashMap<>();
        for (UUID campID: camps) {
            Camp curCamp = CampManager.getCamp(campID);
            if (Objects.equals(curCamp.getStaffInCharge().getName(), this.staffName)) {
                op.put(campID, curCamp);
            }
        }
        return op;
    }
}