package model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


/**
 * Model class representing a camp committee member, extending the functionality of the
 * Student class. It includes additional attributes and methods specific to camp committee members.
 *
 * The class maintains information about the camp suggestions provided by the committee member, the camp ID
 * associated with their committee role, and a points system to track their contributions.
 *
 * @author Ryan Lau, Seung Yeon, Markus Lim
 * @version 1.0
 * @since 2023-11-18
 */
public class CampCommMember extends Student {

    /** The set of UUIDs representing camp suggestions made by the committee member. */
    private final Set<UUID> campSuggestions;

    /** The UUID of the camp associated with the committee role of the member. */
    private UUID commCampID;

    /** The points earned by the committee member. */
    private int points;

    /**
     * Constructor for CampCommMember object based on the information of an existing Student and a camp ID.
     * Performs a deep copy of the Student class and initializes committee-specific variables.
     *
     * @param student The Student object whose information is used for constructing the committee member.
     * @param campID The UUID of the camp associated with the committee role.
     */
    public CampCommMember(Student student, UUID campID){
        // deep copy student class
        super(student.getName(), student.getEmail(), student.getFaculty());
        this.setPassword(student.getPassword());
        this.setCampCommitteeMember(true);
        this.getRegisteredCamps().addAll(student.getRegisteredCamps());
        this.getSubmittedEnquiries().addAll(student.getSubmittedEnquiries());

        // variables unique to camp committee members
        this.campSuggestions = new HashSet<>();
        this.setCommCampID(campID);
        this.points = 0;
    }

    /**
     * Retrieves the set of UUIDs representing camp suggestions made by the committee member.
     *
     * @return The set of UUIDs representing camp suggestions.
     */
    public Set<UUID> getCampSuggestions(){
        return campSuggestions;
    }

    /**
     * Sets the UUID of the camp associated with the committee role.
     *
     * @param commCampID The UUID of the committee camp.
     */
    public void setCommCampID(UUID commCampID) {
        this.commCampID = commCampID;
    }

    /**
     * Retrieves the UUID of the camp associated with the committee role.
     *
     * @return The UUID of the committee camp.
     */
    public UUID getCommCampID() {
        return commCampID;
    }

    /**
     * Submits a camp suggestion by adding its UUID to the set of camp suggestions.
     *
     * @param suggestionID The UUID of the suggested camp.
     */
    public void submitCampSuggestion(UUID suggestionID) {
        campSuggestions.add(suggestionID);
    }

    /**
     * Deletes a camp suggestion by removing its UUID from the set of camp suggestions.
     *
     * @param suggestionID The UUID of the camp suggestion to be deleted.
     */
    public void deleteCampSuggestion(UUID suggestionID){
        campSuggestions.remove(suggestionID);
    }

    /**
     * Retrieves the number of points earned by the committee member.
     *
     * @return The number of points earned.
     */
    public int getPoints(){
        return points;
    }

    /**
     * Increments the points earned by the committee member.
     */
    public void addPoint(){
        this.points++;
    }
}
