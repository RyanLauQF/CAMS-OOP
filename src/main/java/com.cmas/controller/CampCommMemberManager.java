package com.cmas.controller;

import com.cmas.model.CampCommMember;

import java.util.Set;
import java.util.UUID;


/**
 * Controller class that manages Camp Committee Member related operations such as getting Camp ID that he is in charge of,
 * suggestions updates
 * and points system updates. This class interacts with the underlying database model objects to store and retrieve
 * Camp Committee Member information based on view inputs.
 *
 * @author Seung Yeon
 * @version 1.0
 * @since 2023-11-16
 */
public class CampCommMemberManager {

    /**
     * Gets the Camp ID that a Camp Committee Member is in charge of.
     *
     * @param student The student who is a Camp Committee Member.
     * @return The UUID representing the Camp ID of the Committee Member.
     */
    public static UUID getCampID(CampCommMember student) {
        return student.getCommCampID();
    }

    /**
     * Retrieves a set of suggestions associated with a Camp Committee Member.
     *
     * @param student The Camp Committee Member for whom suggestions are retrieved.
     * @return A set of UUIDs representing the suggestions made by the Committee Member.
     */
    public static Set<UUID> getCCMSuggestions(CampCommMember student) {
        return student.getCampSuggestions();
    }

    /**
     * Adds a suggestion to the list of suggestions made by a Camp Committee Member.
     *
     * @param suggestionUID The UUID of the suggestion to be added.
     * @param student       The Camp Committee Member submitting the suggestion.
     */
    public static void addSuggestiontoCCM(UUID suggestionUID, CampCommMember student) {
        student.submitCampSuggestion(suggestionUID);
    }

    /**
     * Removes a suggestion from the list of suggestions made by a Camp Committee Member.
     *
     * @param student      The Camp Committee Member from whom the suggestion is removed.
     * @param suggestionID The UUID of the suggestion to be removed.
     */
    public static void removeSuggestionfromCCM(CampCommMember student, UUID suggestionID) {
        student.deleteCampSuggestion(suggestionID);
    }

    /**
     * Adds a point to the Camp Committee Member's point system.
     *
     * @param student The Camp Committee Member to whom a point is added.
     */
    public static void addPoint(CampCommMember student) {
        student.addPoint();
    }

    /**
     * Retrieves the current points of a Camp Committee Member.
     *
     * @param student The Camp Committee Member whose points are retrieved.
     * @return The total points accumulated by the Committee Member.
     */
    public static int getPoints(CampCommMember student) {
        return student.getPoints();
    }
}