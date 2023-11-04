package model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Student extends User {
    private final Set<UUID> registeredCamps;
    private final Set<UUID> submittedEnquiries;

    private boolean isCampCommitteeMember;

    public Student(String name, String email, UserGroup faculty){
        super(name, email, faculty);
        this.isCampCommitteeMember = false;
        this.registeredCamps = new HashSet<>();
        this.submittedEnquiries = new HashSet<>();
    }

    public void registerForCamp(UUID campID){
        registeredCamps.add(campID);
    }


    // ================ GETTER AND SETTER FUNCTIONS ================
    public Set<UUID> getRegisteredCamps() {
        return registeredCamps;
    }

    public Set<UUID> getSubmittedEnquiries(){
        return submittedEnquiries;
    }

    public void submitEnquiry(UUID enquiryID){
        submittedEnquiries.add(enquiryID);
    }

    public boolean isCampCommitteeMember() {
        return isCampCommitteeMember;
    }

    public void setCampCommitteeMember(boolean isCampComm) {
        isCampCommitteeMember = isCampComm;
    }
}
