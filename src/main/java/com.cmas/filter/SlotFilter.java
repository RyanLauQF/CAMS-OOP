package com.cmas.filter;

import com.cmas.helper.UserIO;
import com.cmas.model.Camp;
import com.cmas.controller.CampManager;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class SlotFilter implements IFilter{
    private final int totalFilter;

    public SlotFilter() {
        System.out.print("Enter slot filter: ");
        this.totalFilter = UserIO.getIntResponse();
    }
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