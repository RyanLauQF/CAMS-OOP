package model;

import java.io.Serializable;
import java.util.UUID;

public class Suggestion implements Serializable {
    private String suggestion;

    private final CampCommMember createdBy;

    private final UUID campID;

    private boolean isViewed;
    private boolean isAccepted;
    private SuggestionType type;

    public Suggestion(String suggestion, CampCommMember createdBy, UUID campID, SuggestionType type) {
        this.suggestion = suggestion;
        this.createdBy = createdBy;
        this.campID = campID;
        this.isViewed = false;
        this.isAccepted = false;
        this.type = type;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void printSuggestionDetails() {
        System.out.println("Suggestion: " + suggestion);
        System.out.println("Created by: " + createdBy.getName());
        System.out.println("Type: " + type);
        System.out.println("Status: " + (isViewed ? (isAccepted ? "Accepted" : "Rejected") : "Pending"));
    }

    public UUID getCampID() {
        return campID;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public CampCommMember getCreatedBy() {
        return createdBy;
    }

    public boolean isViewed() {
        return isViewed;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setViewed(boolean viewed) {
        isViewed = viewed;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public SuggestionType getType() {
        return type;
    }

    public void setType(SuggestionType type) {
        this.type = type;
    }

}

