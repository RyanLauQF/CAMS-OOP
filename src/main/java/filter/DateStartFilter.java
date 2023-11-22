package filter;

import controller.CampManager;
import helper.UserIO;
import model.Camp;

import java.time.LocalDate;
import java.util.*;

/**
 * Start date filter for students
 *
 * @author Markus Lim
 * @version 1.0
 * @since 2023-11-18
 */
public class DateStartFilter implements IFilter{
    private final LocalDate date;

    public DateStartFilter() {
        System.out.print("Enter start date filter: ");
        this.date = UserIO.getDateResponse();
    }
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
