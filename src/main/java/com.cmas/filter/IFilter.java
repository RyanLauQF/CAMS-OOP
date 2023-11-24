package com.cmas.filter;

import com.cmas.model.Camp;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;


/**
 * Interface for filtering report generation by staff or camp committee members
 *
 * @author Markus Lim
 * @version 1.0
 * @since 2023-11-23
 */
public interface IFilter {

    /**
     * Filters a collection of camp objects based on the provided set of unique identifiers (UUIDs).
     * Returns a hashmap containing the filtered camps.
     *
     * @param camps A set of unique identifiers (UUIDs) used for filtering the camps.
     * @return A hashmap containing the filtered camps, with UUIDs as keys and corresponding camps as value
     */
    HashMap<UUID, Camp> filterBy(Set<UUID> camps);
}
