package filter;

import controller.CampManager;
import helper.UserIO;
import model.Camp;

import java.time.LocalDate;
import java.util.*;

/**
 * End date filter for students
 *
 * @author Markus Lim
 * @version 1.0
 * @since 2023-11-18
 */
public class DateEndFilter implements IFilter{
    private final LocalDate date;

    public DateEndFilter() {
        System.out.print("Enter end date filter: ");
        this.date = UserIO.getDateResponse();
    }
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
