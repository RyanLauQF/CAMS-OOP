package model;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Student extends User {
    private final Set<UUID> registeredCamps;
    private UUID commCampID;
    private boolean isCampCommitteeMember;


    public Student(String name, String email, UserGroup faculty){
        super(name, email, faculty);
        this.isCampCommitteeMember = false;
        this.registeredCamps = new HashSet<>();
        this.commCampID = null;
    }

    // ================ GETTER AND SETTER FUNCTIONS ================
    public Set<UUID> getRegisteredCamps() {
        return registeredCamps;
    }

    public void registerForCamp(UUID campID){
        registeredCamps.add(campID);
    }

    public boolean isCampCommitteeMember() {
        return isCampCommitteeMember;
    }

    public void setCampCommitteeMember(boolean isCampComm) {
        isCampCommitteeMember = isCampComm;
        if(!isCampComm){
            setCommCampID(null);
        }
    }

    public void setCommCampID(UUID commCampID) {
        this.commCampID = commCampID;
    }

    public UUID getCommCampID() {
        return commCampID;
    }

    //    // seeing what camps are available for student, also no. of slots put here instead?
//    public ArrayList<Camp> viewCamps(Camp[] camps) {
//        ArrayList<Camp> availableCamps = new ArrayList<Camp>();
//        for (Camp camp: camps) {
//            if (camp.getUserGroup() == this.getFaculty() || camp.getUserGroup().equals("NTU")) {
//                availableCamps.add(camp);
//            }
//        }
//        return availableCamps;
//    }
//
//    public void signUp(Camp camp, StudentRole role) {
//        try{
//            camp.registerStudent(this, role);
//        }
//        catch(Exception e){
//            System.out.println("Unable to register student");
//            return;
//        }
//
//        if (role == StudentRole.CampComm) {
//            this.isCampCommitteeMember = true;
//        }
//
//    }
//    public void submitEnquiry(Camp camp, String query) {
//        Enquiry enquiry = new Enquiry(query, this);
//        camp.addEnquiry(enquiry);
//    }
//
//
//    // there is some whack ass thing where they said students can  view, edit and delete enquiries before processed,
//    // but what is processed
//    public String viewEnquiry(Camp camp, Enquiry query) {
//        for (Enquiry enquiry : camp.getEnquiries()) {
//            if (enquiry.getCreatedBy() == this && enquiry == query) {
//                enquiry.getQuery();
//            }
//        }
//        return "";
//    }
//
//    public void editEnquiry(Camp camp, Enquiry query, String newQuery) {
//        for (Enquiry enquiry : camp.getEnquiries()) {
//            if (enquiry.getCreatedBy() == this && enquiry == query) {
//                enquiry.setQuery(newQuery);
//            }
//        }
//    }
//
//    public StudentRole getStudentRole() {
//        if (this.isCampCommitteeMember) {
//            return (StudentRole.CampComm);
//        }
//        return(StudentRole.Attendee);
//    }
//
//    // have yet to do the one student can be a CCM for one and attendee for the rest
//
//    public void withdrawCamp(Camp camp) {
//        camps.remove(camp);
//        try{
//            camp.withdrawStudent(this);
//        }
//        catch(Exception e){
//            System.out.println("error withdrawing student");
//        }
//    }
}
