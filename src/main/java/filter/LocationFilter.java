package filter;

import controller.CampManager;
import helper.UserIO;
import model.Camp;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Location filter for students
 *
 * @author Markus Lim
 * @version 1.0
 * @since 2023-11-18
 */
public class LocationFilter implements IFilter{
    private final String userInput;

    public LocationFilter() {
        System.out.print("Enter location filter: ");
        this.userInput = UserIO.getStringResponse();
    }
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