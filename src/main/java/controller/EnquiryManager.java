package controller;

import database.Database;
import model.Enquiry;
import model.Student;

import java.util.HashMap;
import java.util.UUID;


/**
 * Manages operations related to Enquiries, such as adding, deleting, and updating Enquiries.
 */
public class EnquiryManager {
    // Database containing Enquiry data
    private static final HashMap<UUID, Enquiry> enquiryData = Database.ENQUIRY_DATA;

    /**
     * Retrieves all Enquiries from the database.
     *
     * @return A HashMap containing all Enquiries, where the key is the Enquiry UID.
     */
    public static HashMap<UUID, Enquiry> getAllEnquiries(){
        return enquiryData;
    }

    /**
     * Retrieves a specific Enquiry based on its UID.
     *
     * @param enquiryUID The UID of the Enquiry to retrieve.
     * @return The Enquiry object corresponding to the provided UID.
     */
    public static Enquiry getEnquiry(UUID enquiryUID){
        return enquiryData.get(enquiryUID);
    }

    /**
     * Adds a new Enquiry to the database, associating it with a specific Student.
     *
     * @param enquiry The Enquiry object to be added.
     * @param student The Student associated with the Enquiry.
     */
    public static void addNewEnquiry(Enquiry enquiry, Student student){
        // generate a unique key for each enquiry to be stored in database
        UUID uid = UUID.randomUUID();
        while(enquiryData.containsKey(uid)){
            uid = UUID.randomUUID();
        }
        enquiryData.put(uid, enquiry);
        StudentManager.addEnquiryToStudent(uid, student);
        CampManager.updateEnquiry(enquiry.getCampID(), uid);
    }

    /**
     * Deletes a specific Enquiry from the database and removes associated references.
     *
     * @param enquiryUID The UID of the Enquiry to be deleted.
     * @param student    The Student associated with the Enquiry.
     */
    public static void deleteEnquiry(UUID enquiryUID, Student student){
        Enquiry enquiry = getEnquiry(enquiryUID);
        StudentManager.deleteEnquiryToStudent(enquiryUID, student);
        CampManager.deleteEnquiry(enquiry.getCampID(), enquiryUID);
        enquiryData.remove(enquiryUID);
    }

    /**
     * Updates the query of a specific Enquiry.
     *
     * @param query      The new query for the Enquiry.
     * @param enquiryUID The UID of the Enquiry to be updated.
     */
    public static void updateEnquiryQuery(String query, UUID enquiryUID){
        Enquiry enquiry = getEnquiry(enquiryUID);
        enquiry.setQuery(query);
    }

    /**
     * Updates the status of a specific Enquiry to indicate that it has been processed.
     *
     * @param enquiryUID The UID of the Enquiry to be updated.
     */
    public static void updateEnquiryStatus(UUID enquiryUID){
        Enquiry enquiry = getEnquiry(enquiryUID);
        if (!enquiry.getIsProcessed()){
            enquiry.setIsProcessed(true);
        }
    }

    /**
     * Updates the reply of a specific Enquiry.
     *
     * @param reply      The new reply for the Enquiry.
     * @param enquiryUID The UID of the Enquiry to be updated.
     */
    public static void updateEnquiryReply(String reply, UUID enquiryUID){
        Enquiry enquiry = getEnquiry(enquiryUID);
        enquiry.setReply(reply);
    }
}
