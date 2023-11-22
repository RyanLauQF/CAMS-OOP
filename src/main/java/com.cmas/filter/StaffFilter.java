package com.cmas.filter;

import com.cmas.helper.UserIO;
import com.cmas.model.Camp;
import com.cmas.controller.CampManager;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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