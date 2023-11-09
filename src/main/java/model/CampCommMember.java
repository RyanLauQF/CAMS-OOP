package model;


import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CampCommMember extends Student {
    private final Set<UUID> campSuggestions;
    private UUID commCampID;

    public CampCommMember(Student student, UUID campID){
        // deep copy student class
        super(student.getName(), student.getEmail(), student.getFaculty());
        this.setPassword(student.getPassword());
        this.setCampCommitteeMember(true);
        this.getRegisteredCamps().addAll(student.getRegisteredCamps());
        this.getSubmittedEnquiries().addAll(student.getSubmittedEnquiries());

        // variables unique to camp committee members
        this.campSuggestions = new HashSet<>();
        this.commCampID = campID;
    }

    public Set<UUID> getCampSuggestions(){
        return campSuggestions;
    }

    public void setCommCampID(UUID commCampID) {
        this.commCampID = commCampID;
    }

    public UUID getCommCampID() {
        return commCampID;
    }
    public void submitCampSuggestion(UUID suggestionID) {
        campSuggestions.add(suggestionID);
    }
    public void deleteCampSuggestion(UUID suggestionID){
        campSuggestions.remove(suggestionID);
    }
}
