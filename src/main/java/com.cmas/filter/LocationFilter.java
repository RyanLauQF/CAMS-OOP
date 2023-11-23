package com.cmas.filter;

import com.cmas.controller.CampManager;
import com.cmas.helper.UserIO;
import com.cmas.model.Camp;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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