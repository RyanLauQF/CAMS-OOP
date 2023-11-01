package model;

public class Suggestion {
    private String suggestion;
    private final String creatorID;
    private boolean isAccepted;

    public Suggestion(String suggestion, String creatorID) {
        this.suggestion = suggestion;
        this.creatorID = creatorID;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
