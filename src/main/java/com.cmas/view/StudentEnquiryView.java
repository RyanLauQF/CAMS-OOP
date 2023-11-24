package com.cmas.view;

import com.cmas.helper.ConsoleColours;
import com.cmas.helper.UserIO;
import com.cmas.model.Student;


/**
 * The StudentEnquiryView class provides a method for rendering the enquiry menu for students
 * and managing user selections to render various views as part of the CreateEnquiryView interface.
 *
 * @author Tong Ying, Ryan Lau
 * @version 1.0
 * @since 2023-11-24
 */
public class StudentEnquiryView implements CreateEnquiryView{

    /**
     * Renders the enquiry menu for students and manages user selections to render various views.
     *
     * @param student The Student for whom the enquiry menu is rendered.
     */
    public static void studentEnquiryView(Student student) {
        while (true) {
            try {
                System.out.println("\n===================== ENQUIRY MENU ======================");
                System.out.print(ConsoleColours.BLUE);
                System.out.println("1) Show all submitted enquiries");
                System.out.println("2) Create new enquiry");
                System.out.println("3) Edit enquiry");
                System.out.println("4) Delete Enquiry");
                System.out.println("5) Exit enquiry menu");
                System.out.print(ConsoleColours.RESET);
                System.out.println("=========================================================");

                System.out.print("\nSelect an action: ");

                int choice = UserIO.getSelection(1, 5);

                switch (choice) {
                    case 1:
                        CreateEnquiryView.showAllSubmittedEnquiry(student);
                        break;
                    case 2:
                        CreateEnquiryView.createEnquiryView(student);
                        break;
                    case 3:
                        CreateEnquiryView.editEnquiryView(student);
                        break;
                    case 4:
                        CreateEnquiryView.deleteEnquiryView(student);
                        break;
                    case 5:
                        System.out.println("\nExiting enquiry menu...");
                        return;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
