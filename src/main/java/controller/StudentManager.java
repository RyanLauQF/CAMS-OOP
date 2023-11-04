package controller;

import model.Staff;
import model.Student;

import java.util.UUID;

public class StudentManager {
    public static void addEnquiryToStudent(UUID enquiryID, Student student){
        student.submitEnquiry(enquiryID);
    }
}
