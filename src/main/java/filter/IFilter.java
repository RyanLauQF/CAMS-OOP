package filter;

import model.Camp;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * Filter interface for students to implement
 * Students can implement a concrete class of the filter
 *
 * @author Markus Lim
 * @version 1.0
 * @since 2023-11-18
 */
public interface IFilter {
    public HashMap<UUID, Camp> filterBy(Set<UUID> camps);
}
