package filter;

import controller.CampManager;
import helper.UserIO;
import model.Camp;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Staff name filter for students
 *
 * @author Markus Lim
 * @version 1.0
 * @since 2023-11-18
 */
public class StaffFilter implements IFilter{
    private final String staffName;

    public StaffFilter() {
        System.out.print("Enter staff name: ");
        this.staffName = UserIO.getStringResponse();
    }
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