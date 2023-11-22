package com.cmas.filter;

import com.cmas.model.Camp;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public interface IFilter {
    public HashMap<UUID, Camp> filterBy(Set<UUID> camps);
}
