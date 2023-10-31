package model;

public class Suggestion {
    private String suggestion;
    private final CampCommMember createdBy;
    private boolean isAccepted;

    public Suggestion(String suggestion, CampCommMember createdBy) {
        this.suggestion = suggestion;
        this.createdBy = createdBy;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public CampCommMember getCreatedBy() {
        return createdBy;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
