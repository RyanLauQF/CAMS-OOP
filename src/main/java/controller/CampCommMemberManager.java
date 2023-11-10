package controller;

import model.CampCommMember;

import java.util.Set;
import java.util.UUID;

public class CampCommMemberManager {
    public static UUID getCampID(CampCommMember student) {
        return student.getCommCampID();
    }
    public static Set<UUID> getCCMSuggestions(CampCommMember student) {
        return student.getCampSuggestions();
    }
    public static void addSuggestiontoCCM(UUID suggestionUID, CampCommMember student) {
        student.submitCampSuggestion(suggestionUID);
    }
    public static void removeSuggestionfromCCM(CampCommMember student, UUID suggestionID) {
        student.deleteCampSuggestion(suggestionID);
    }
    public static void addPoint(CampCommMember student) {
        student.addPoint();
    }
    public static int getPoints(CampCommMember student) {
        return student.getPoints();
    }
}