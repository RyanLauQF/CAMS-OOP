package view;

import model.*;

import java.time.LocalDate;
import java.util.Scanner;

public class StaffView {

    public StaffView(){

    }

    public void startView(){

    }

    public Camp getCampDetails(Staff staff){
        Scanner sc = new Scanner(System.in);
        //String name, LocalDate startDate, LocalDate endDate, LocalDate closingDate, UserGroup userGroup, String location, int totalSlots, int campCommSlots, String description, Staff staffInCharge, boolean isVisible
        System.out.println("Enter the name of camp: ");
        String name = sc.nextLine();
        System.out.println("Enter the start date of the camp(yyy-MM-dd): ");
        String sDate = sc.nextLine();
        LocalDate startDate = LocalDate.parse(sDate);
        System.out.println("Enter the end date of the camp(yyy-MM-dd): ");
        String eDate = sc.nextLine();
        LocalDate endDate = LocalDate.parse(eDate);
        System.out.println("Enter the registration closing date of the camp(yyy-MM-dd): ");
        String cDate = sc.nextLine();
        LocalDate closingDate = LocalDate.parse(cDate);
        System.out.println("Enter group that camp is open to ");
        String group = sc.nextLine();
        UserGroup userGroup = UserGroup.valueOf(group.toUpperCase());
        System.out.println("Enter the location of camp: ");
        String location = sc.nextLine();
        System.out.println("Enter the number of slots: ");
        int totalSlots = sc.nextInt();
        System.out.println("Enter the number of camp committee members: ");
        int campCommSlots = sc.nextInt();
        System.out.println("Enter the description of your camp: ");
        String description = sc.nextLine();
        Staff staffInCharge = staff;
        boolean isVisible = true;

        sc.close();
        Camp newCamp = null;
        try{
            newCamp = new Camp(name, startDate, endDate,  closingDate,  userGroup,  location,  totalSlots, campCommSlots,  description,  staffInCharge,  isVisible);
        }
        catch(Exception e){
            System.out.println("unable to create camp");
        }

        return newCamp;
    }
    public void viewAllCamps(){};

    public void viewCreatedCamps(){};

    public void viewEnquiries(Camp camp, Student student){};

    public void viewSuggestions(Camp camp, CampCommMember member){};
}
