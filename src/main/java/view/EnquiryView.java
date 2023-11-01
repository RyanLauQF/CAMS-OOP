package view;

import helper.UserIO;
import model.Student;


public class EnquiryView {
    public static void studentEnquiryView(Student student){
        while(true){
            try{
                System.out.println("======================= ENQUIRY MENU =======================");
                System.out.println("1) Show all submitted enquiries");
                System.out.println("2) Create new enquiry");
                System.out.println("3) Edit enquiry");
                System.out.println("4) Delete Enquiry");
                System.out.println("5) Exit enquiry menu");
                System.out.println("=========================================================\n");

                System.out.print("Select an action: ");

                int choice = UserIO.getSelection(1, 5);

                switch (choice){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        System.out.println("Exiting enquiry menu...");
                        return;
                    default:
                        break;
                }
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }

    public static void showAllSubmittedEnquiry(Student student){

    }
}
