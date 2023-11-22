package com.cmas.controller;

import com.cmas.model.Student;

import java.util.UUID;


/**
 * Controller class that manages student objects. It provides methods to add and delete
 * enquiries for a student.
 *
 * @author Tong Ying
 * @version 1.0
 * @since 2023-11-18
 */
public class StudentManager {

    /**
     * Adds an enquiry to the specified student.
     *
     * @param enquiryID The unique identifier for the enquiry.
     * @param student   The student to whom the enquiry is added.
     */
    public static void addEnquiryToStudent(UUID enquiryID, Student student){
        student.submitEnquiry(enquiryID);
    }

    /**
     * Deletes an enquiry from the specified student.
     *
     * @param enquiryID The unique identifier for the enquiry to be deleted.
     * @param student   The student from whom the enquiry is deleted.
     */
    public static void deleteEnquiryToStudent(UUID enquiryID, Student student){
        student.deleteEnquiry(enquiryID);
    }
}
