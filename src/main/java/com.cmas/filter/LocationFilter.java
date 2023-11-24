package com.cmas.filter;

import com.cmas.controller.CampManager;
import com.cmas.helper.UserIO;
import com.cmas.model.Camp;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


/**
 * Utility class used to filter camps based on location
 *
 * @author Markus Lim
 * @version 1.0
 * @since 2023-11-23
 */
public class LocationFilter implements IFilter{

    /**
     * The location string used as the reference for filtering camps.
     */
    private final String userInput;

    /**
     * Constructor for location filter. Prompts user for location response to be used as reference for filtering.
     */
    public LocationFilter() {
        System.out.print("Enter location filter: ");
        this.userInput = UserIO.getStringResponse();
    }

    /**
     * Filters a collection of camps based on location bvy matching strings.
     *
     * @param camps A set of UUIDs representing camps to be filtered.
     * @return A hashmap containing camps filtered by location
     */
    @Override
    public HashMap<UUID, Camp> filterBy(Set<UUID> camps) {
        HashMap<UUID, Camp> op = new HashMap<>();
        for (UUID campID: camps) {
            Camp curCamp = CampManager.getCamp(campID);
            if (Objects.equals(curCamp.getLocation(), this.userInput)) {
                op.put(campID, curCamp);
            }
        }
        return op;
    }
}