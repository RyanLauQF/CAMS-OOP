package com.cmas.filter;

import com.cmas.model.Camp;
import com.cmas.controller.CampManager;
import com.cmas.helper.UserIO;

import java.time.LocalDate;
import java.util.*;


/**
 * Utility class used to filter camps based on start date
 *
 * @author Markus Lim
 * @version 1.0
 * @since 2023-11-23
 */
public class DateStartFilter implements IFilter{

    /**
     * The start date used as the reference for filtering camps.
     */
    private final LocalDate date;

    /**
     * Constructor for start date filter. Prompts user for date response to be used as reference for filtering.
     */
    public DateStartFilter() {
        System.out.print("Enter start date filter: ");
        this.date = UserIO.getDateResponse();
    }

    /**
     * Filters a collection of camps based on their start dates, retaining only those with start dates before
     * the specified reference date.
     *
     * @param camps A set of UUIDs representing camps to be filtered.
     * @return A hashmap containing camps filtered by start date
     */
    @Override
    public HashMap<UUID, Camp> filterBy(Set<UUID> camps) {
        HashMap<UUID, Camp> op = new HashMap<>();
        for (UUID campID: camps) {
            Camp curCamp = CampManager.getCamp(campID);
            if (curCamp.getStartDate().isBefore(this.date)) {
                op.put(campID, curCamp);
            }
        }
        return op;
    }
}
