package model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


/**
 * Model class representing a student and manages student-related operations.
 *
 * @author Seung Yeon
 * @version 1.0
 * @since 2023-11-18
 */
public class Student extends User {
    private final Set<UUID> registeredCamps;
    private final Set<UUID> submittedEnquiries;

    private boolean isCampCommitteeMember;

    /**
     * Constructs a Student object with the specified parameters.
     *
     * @param name    The name of the student.
     * @param email   The email of the student.
     * @param faculty The faculty to which the student belongs.
     */
    public Student(String name, String email, UserGroup faculty) {
        super(name, email, faculty);
        this.isCampCommitteeMember = false;
        this.registeredCamps = new HashSet<>();
        this.submittedEnquiries = new HashSet<>();
    }

    /**
     * Registers the student for a camp.
     *
     * @param campID The UUID of the camp to register for.
     */
    public void registerForCamp(UUID campID) {
        registeredCamps.add(campID);
    }


    // ================ GETTER AND SETTER FUNCTIONS ================
    /**
     * Gets the set of UUIDs of camps the student is registered for.
     *
     * @return The set of UUIDs of registered camps.
     */
    public Set<UUID> getRegisteredCamps() {
        return registeredCamps;
    }

    /**
     * Gets the set of UUIDs of enquiries submitted by the student.
     *
     * @return The set of UUIDs of submitted enquiries.
     */
    public Set<UUID> getSubmittedEnquiries() {
        return submittedEnquiries;
    }

    /**
     * Submits an enquiry for the student.
     *
     * @param enquiryID The UUID of the enquiry to be submitted.
     */
    public void submitEnquiry(UUID enquiryID) {
        submittedEnquiries.add(enquiryID);
    }

    /**
     * Deletes an enquiry submitted by the student.
     *
     * @param enquiryID The UUID of the enquiry to be deleted.
     */
    public void deleteEnquiry(UUID enquiryID) {
        submittedEnquiries.remove(enquiryID);
    }

    /**
     * Checks if the student is a camp committee member.
     *
     * @return true if the student is a camp committee member, false otherwise.
     */
    public boolean isCampCommitteeMember() {
        return isCampCommitteeMember;
    }

    /**
     * Sets whether the student is a camp committee member.
     *
     * @param isCampComm true if the student is a camp committee member, false otherwise.
     */
    public void setCampCommitteeMember(boolean isCampComm) {
        isCampCommitteeMember = isCampComm;
    }

    /**
     * Withdraws the student from a registered camp.
     *
     * @param campID The UUID of the camp to withdraw from.
     */
    public void withdrawFromCamp(UUID campID) {
        registeredCamps.remove(campID);
    }
}
