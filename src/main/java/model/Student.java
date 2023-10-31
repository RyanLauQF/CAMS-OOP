package model;

import java.util.ArrayList;
import java.time.LocalDate;

public class Student extends User {
    private boolean isCampCommitteeMember;
    private ArrayList<Camp> camps;


    public Student(String name, String email, String faculty){
        super(name, email, faculty);
        this.isCampCommitteeMember = false;
        this.camps = new ArrayList<Camp>();
    }

    // seeing what camps are available for student, also no. of slots put here instead?
    public ArrayList<Camp> viewCamps(Camp[] camps) {
        ArrayList<Camp> availableCamps = new ArrayList<Camp>();
        for (Camp camp: camps) {
            if (camp.getUserGroup() == this.getFaculty() || camp.getUserGroup().equals("NTU")) {
                availableCamps.add(camp);
            }
        }
        return availableCamps;
    }
//    public void viewCampsSlots() {
//        return camps;
//    } i feel like we should get rid of this function and put the no. of slots under each camp viewed
    public void signUp(Camp camp, StudentRole role) {
        try{
            camp.registerStudent(this, role);
        }
        catch(Exception e){
            System.out.println("Unable to register student");
            return;
        }

        if (role == StudentRole.CampComm) {
            this.isCampCommitteeMember = true;
        }

    }
    public void submitEnquiry(Camp camp, String query) {
        Enquiry enquiry = new Enquiry(query, this);
        camp.addEnquiry(enquiry);
    }


    // there is some whack ass thing where they said students can  view, edit and delete enquiries before processed,
    // but what is processed
    public String viewEnquiry(Camp camp, Enquiry query) {
        for (Enquiry enquiry : camp.getEnquiries()) {
            if (enquiry.getCreatedBy() == this && enquiry == query) {
                enquiry.getQuery();
            }
        }
        return "";
    }

    public void editEnquiry(Camp camp, Enquiry query, String newQuery) {
        for (Enquiry enquiry : camp.getEnquiries()) {
            if (enquiry.getCreatedBy() == this && enquiry == query) {
                enquiry.setQuery(newQuery);
            }
        }
    }

    public StudentRole getStudentRole() {
        if (this.isCampCommitteeMember) {
            return (StudentRole.CampComm);
        }
        return(StudentRole.Attendee);
    }

    // have yet to do the one student can be a CCM for one and attendee for the rest

    public void withdrawCamp(Camp camp) {
        camps.remove(camp);
        try{
            camp.withdrawStudent(this);
        }
        catch(Exception e){
            System.out.println("error withdrawing student");
        }
    }
}
