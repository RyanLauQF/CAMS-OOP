package com.cmas.filter;

import com.cmas.controller.CampManager;
import com.cmas.helper.UserIO;
import com.cmas.model.Camp;

import java.time.LocalDate;
import java.util.*;


/**
 * Utility class used to filter camps based on end date
 *
 * @author Markus Lim
 * @version 1.0
 * @since 2023-11-23
 */
public class DateEndFilter implements IFilter{

    /**
     * The end date used as the reference for filtering camps.
     */
    private final LocalDate date;

    /**
     * Constructor for start date filter. Prompts user for date response to be used as reference for filtering.
     */
    public DateEndFilter() {
        System.out.print("Enter end date filter: ");
        this.date = UserIO.getDateResponse();
    }

    /**
     * Filters a collection of camps based on their end dates, retaining only those with end dates before
     * the specified reference date.
     *
     * @param camps A set of UUIDs representing camps to be filtered.
     * @return A hashmap containing camps filtered by end date
     */
    @Override
    public HashMap<UUID, Camp> filterBy(Set<UUID> camps) {
        HashMap<UUID, Camp> op = new HashMap<>();
        for (UUID campID: camps) {
            Camp curCamp = CampManager.getCamp(campID);
            if (curCamp.getEndDate().isBefore(this.date)) {
                op.put(campID, curCamp);
            }
        }
        return op;
    }
}
