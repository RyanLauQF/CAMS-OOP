package controller;

import model.Student;

import java.util.UUID;


public class StudentManager {
    public static void addEnquiryToStudent(UUID enquiryID, Student student){
        student.submitEnquiry(enquiryID);
    }
    public static void deleteEnquiryToStudent(UUID enquiryID, Student student) {student.deleteEnquiry(enquiryID);}
}
