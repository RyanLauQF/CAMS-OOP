package model;

import java.io.Serializable;
import java.util.UUID;


/**
 * Model class representing a suggestion and manages suggestions-related operations.
 *
 * @author Seung Yeon, Shao Chong
 * @version 1.0
 * @since 2023-11-18
 */
public class Suggestion implements Serializable {
    private String suggestion;

    private final CampCommMember createdBy;

    private final UUID campID;

    private boolean isViewed;
    private boolean isAccepted;
    private SuggestionType type;

    /**
     * Constructs a Suggestion object with the specified suggestion, committee member, camp ID, and suggestion type.
     *
     * @param suggestion The suggestion made by the committee member.
     * @param createdBy  The committee member who created the suggestion.
     * @param campID     The UUID of the camp associated with the suggestion.
     * @param type       The type of the suggestion.
     */
    public Suggestion(String suggestion, CampCommMember createdBy, UUID campID, SuggestionType type) {
        this.suggestion = suggestion;
        this.createdBy = createdBy;
        this.campID = campID;
        this.isViewed = false;
        this.isAccepted = false;
        this.type = type;
    }

    /**
     * Gets the suggestion made by the committee member.
     *
     * @return The suggestion.
     */
    public String getSuggestion() {
        return suggestion;
    }

    /**
     * Prints the details of the suggestion, including the suggestion text, creator, type, and status.
     */
    public void printSuggestionDetails() {
        System.out.println("Suggestion: " + suggestion);
        System.out.println("Created by: " + createdBy.getName());
        System.out.println("Type: " + type);
        System.out.println("Status: " + (isViewed ? (isAccepted ? "Accepted" : "Rejected") : "Pending"));
    }

    /**
     * Gets the UUID of the camp associated with the suggestion.
     *
     * @return The UUID of the camp.
     */
    public UUID getCampID() {
        return campID;
    }

    /**
     * Sets the suggestion text.
     *
     * @param suggestion The new suggestion text.
     */
    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    /**
     * Gets the committee member who created the suggestion.
     *
     * @return The committee member who created the suggestion.
     */
    public CampCommMember getCreatedBy() {
        return createdBy;
    }

    /**
     * Checks if the suggestion has been viewed.
     *
     * @return True if the suggestion has been viewed; otherwise, false.
     */
    public boolean isViewed() {
        return isViewed;
    }

    /**
     * Checks if the suggestion has been accepted.
     *
     * @return True if the suggestion has been accepted; otherwise, false.
     */
    public boolean isAccepted() {
        return isAccepted;
    }

    /**
     * Sets the viewed status of the suggestion.
     *
     * @param viewed True if the suggestion has been viewed; otherwise, false.
     */
    public void setViewed(boolean viewed) {
        isViewed = viewed;
    }

    /**
     * Sets the accepted status of the suggestion.
     *
     * @param accepted True if the suggestion has been accepted; otherwise, false.
     */
    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    /**
     * Gets the type of the suggestion.
     *
     * @return The type of the suggestion.
     */
    public SuggestionType getType() {
        return type;
    }

    /**
     * Sets the type of the suggestion.
     *
     * @param type The new type of the suggestion.
     */
    public void setType(SuggestionType type) {
        this.type = type;
    }
}

