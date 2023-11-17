package controller;

import database.Database;
import model.CampCommMember;
import model.Enquiry;
import model.Student;
import model.User;

import java.util.HashMap;
import java.util.UUID;

public class EnquiryManager {
    private static final HashMap<UUID, Enquiry> enquiryData = Database.ENQUIRY_DATA;

    public static HashMap<UUID, Enquiry> getAllEnquiries() {
        return enquiryData;
    }

    public static Enquiry getEnquiry(UUID enquiryUID) {
        return enquiryData.get(enquiryUID);
    }

    public static void addNewEnquiry(Enquiry enquiry, Student student) {
        // generate a unique key for each enquiry to be stored in database
        UUID uid = UUID.randomUUID();
        while (enquiryData.containsKey(uid)) {
            uid = UUID.randomUUID();
        }
        enquiryData.put(uid, enquiry);
        StudentManager.addEnquiryToStudent(uid, student);
        CampManager.updateEnquiry(enquiry.getCampID(), uid);
    }

    public static void deleteEnquiry(UUID enquiryUID, Student student) {
        Enquiry enquiry = getEnquiry(enquiryUID);
        StudentManager.deleteEnquiryToStudent(enquiryUID, student);
        CampManager.deleteEnquiry(enquiry.getCampID(), enquiryUID);
        enquiryData.remove(enquiry);
    }

    public static void updateEnquiryQuery(String query, UUID enquiryUID) {
        Enquiry enquiry = getEnquiry(enquiryUID);
        enquiry.setQuery(query);
    }

    public static void updateEnquiryStatus(UUID enquiryUID) {
        Enquiry enquiry = getEnquiry(enquiryUID);
        if (!enquiry.getIsProcessed()) {
            enquiry.setIsProcessed(true);
        }
    }

    public static void updateEnquiryReply(String reply, UUID enquiryUID, User replier) {
        Enquiry enquiry = getEnquiry(enquiryUID);
        enquiry.setReply(reply);
        enquiry.setRepliedBy(replier);
    }
}
