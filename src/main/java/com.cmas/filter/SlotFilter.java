package com.cmas.filter;

import com.cmas.helper.UserIO;
import com.cmas.model.Camp;
import com.cmas.controller.CampManager;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;


/**
 * Utility class used to filter camps based on location
 *
 * @author Markus Lim
 * @version 1.0
 * @since 2023-11-23
 */
public class SlotFilter implements IFilter{

    /**
     * The number of slots used as the reference for filtering camps.
     */
    private final int totalFilter;

    /**
     * Constructor for slot count filter. Prompts user for numbers of slots to be used as reference for filtering.
     */
    public SlotFilter() {
        System.out.print("Enter slot filter: ");
        this.totalFilter = UserIO.getIntResponse();
    }

    /**
     * Filters a collection of camps based on number of slots, retaining only with slots greater
     * than or equal to user input.
     *
     * @param camps A set of UUIDs representing camps to be filtered.
     * @return A hashmap containing camps filtered by number of slots
     */
    @Override
    public HashMap<UUID, Camp> filterBy(Set<UUID> camps) {
        HashMap<UUID, Camp> op = new HashMap<>();
        for (UUID campID: camps) {
            Camp curCamp = CampManager.getCamp(campID);
            if (curCamp.getRemainingSlots() >= this.totalFilter) {
                op.put(campID, curCamp);
            }
        }
        return op;
    }
}