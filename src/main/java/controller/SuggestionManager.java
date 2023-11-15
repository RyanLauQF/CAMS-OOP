package controller;

import database.Database;
import model.Enquiry;
import model.Suggestion;
import model.CampCommMember;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class SuggestionManager {
    private static final HashMap<UUID, Suggestion> suggestionData = Database.SUGGESTION_DATA;

    public static Suggestion getSuggestion(UUID suggestionUID) {
        return suggestionData.get(suggestionUID);
    }

    public static void addSuggestion(Suggestion suggestion, CampCommMember student) {
        UUID uid = UUID.randomUUID();
        while (suggestionData.containsKey(uid)) {
            uid = UUID.randomUUID();
        }
        suggestionData.put(uid, suggestion);
        CampCommMemberManager.addSuggestiontoCCM(uid, student);
        CampManager.updateSuggestion(suggestion.getCampID(), uid);
    }

    public static void updateSuggestion(String suggestion, UUID suggestionUID) {
        getSuggestion(suggestionUID).setSuggestion(suggestion);
    }

    public static void removeSuggestion(UUID suggestionUID, CampCommMember student) {
        Suggestion suggestion = getSuggestion(suggestionUID);
        CampManager.removeSuggestionfromCamp(suggestion.getCampID(), suggestionUID);
        CampCommMemberManager.removeSuggestionfromCCM(student, suggestionUID);
        suggestionData.remove(suggestionUID);
    }

    public static void setStatus(UUID suggestionUID, boolean status) {
        Suggestion suggestion = getSuggestion(suggestionUID);
        suggestion.setViewed(true);
        suggestion.setAccepted(status);
        if (status) {
            suggestion.getCreatedBy().addPoint();
        }
    }
}

