package com.cmas.controller;

import com.cmas.database.Database;
import com.cmas.model.Camp;
import com.cmas.model.CampCommMember;
import com.cmas.model.Suggestion;

import java.util.HashMap;
import java.util.UUID;


/**
 * Controller class that manages operations related to suggestions, including adding, updating, and removing suggestions.
 * Additionally, it tracks that acceptance status for the suggestion and also add points for those with suggestions accepted.
 * This class interacts with the database to store and retrieve suggestion data.
 *
 * @author Seung Yeon, Tong Ying, Ryan Lau, Shao Chong, Markus Lim
 * @version 1.0
 * @since 2023-11-14
 */
public class SuggestionManager {

    /**
     * HashMap storing suggestion data, where the keys are UUIDs and values are suggestions.
     */
    private static final HashMap<UUID, Suggestion> suggestionData = Database.getSuggestionData();

    /**
     * Retrieves a suggestion based on the provided UUID.
     *
     * @param suggestionUID The UUID of the suggestion to retrieve.
     * @return The suggestion associated with the UUID.
     */
    public static Suggestion getSuggestion(UUID suggestionUID) {
        return suggestionData.get(suggestionUID);
    }

    /**
     * Adds a new suggestion and generate a new UUID for the suggestion.
     * Tags the suggestion with the provided Camp Committee Member (student).
     *
     * @param suggestion The suggestion to be added.
     * @param student The Camp Committee Member (student) adding the suggestion.
     */
    public static void addSuggestion(Suggestion suggestion, CampCommMember student) {
        UUID uid = UUID.randomUUID();
        while (suggestionData.containsKey(uid)) {
            uid = UUID.randomUUID();
        }
        suggestionData.put(uid, suggestion);
        CampCommMemberManager.addSuggestiontoCCM(uid, student);
        CampManager.updateSuggestion(suggestion.getCampID(), uid);
    }

    /**
     * Updates the content of a suggestion based on the provided suggestion UUID.
     *
     * @param suggestion The new content of the suggestion.
     * @param suggestionUID The UUID of the suggestion to update.
     */
    public static void updateSuggestion(String suggestion, UUID suggestionUID) {
        getSuggestion(suggestionUID).setSuggestion(suggestion);
    }

    /**
     * Removes a suggestion from the system based on the provided suggestion UUID.
     * Also removes the association from the Camp Committee Member.
     *
     * @param suggestionUID The UUID of the suggestion to remove.
     * @param student       The Camp Committee Member (student) associated with the suggestion.
     */
    public static void removeSuggestion(UUID suggestionUID, CampCommMember student) {
        Suggestion suggestion = getSuggestion(suggestionUID);
        CampManager.removeSuggestionfromCamp(suggestion.getCampID(), suggestionUID);
        CampCommMemberManager.removeSuggestionfromCCM(student, suggestionUID);
        suggestionData.remove(suggestionUID);
    }

    /**
     * Sets the acceptance status of a suggestion.
     * Additionally, awards points to the creator of the suggestion if it is accepted.
     *
     * @param suggestionUID The UUID of the suggestion to update.
     * @param status The status indicating whether the suggestion is accepted.
     */
    public static void setStatus(UUID suggestionUID, boolean status) {
        Suggestion suggestion = getSuggestion(suggestionUID);
        suggestion.setViewed(true);
        suggestion.setAccepted(status);
        if (status) {
            suggestion.getCreatedBy().addPoint();
        }
    }

    /**
     * Prints the details of a suggestion.
     *
     * @param suggestionUID The UID of the suggestion to be printed.
     */
    public static void printSuggestionDetails(UUID suggestionUID) {
        Suggestion suggestion = getSuggestion(suggestionUID);
        Camp camp = CampManager.getCamp(suggestion.getCampID());
        System.out.println("Camp Name: " + camp.getName());
        suggestion.printSuggestionDetails();
    }
}

