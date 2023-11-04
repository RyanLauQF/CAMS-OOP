package view;

import controller.CampManager;
import helper.UserIO;
import model.UserGroup;

import java.time.LocalDate;
import java.util.UUID;

public class CampEditView {
    public static void editCampView(UUID campUID){
        while(true){
            try{
                System.out.println("======================= EDIT MENU =======================");
                CampManager.getCamp(campUID).printCampDetails();
                System.out.println("\nOptions: ");
                System.out.println("1) Change Name");
                System.out.println("2) Change Start Date");
                System.out.println("3) Change End Date");
                System.out.println("4) Change Registration Closing Date");
                System.out.println("5) Change Student Group");
                System.out.println("6) Change Location");
                System.out.println("7) Change Number of Slots");
                System.out.println("8) Change Description");
                System.out.println("9) Change Visibility");
                System.out.println("10) Cancel Editing");
                System.out.println("=========================================================\n");

                System.out.print("Select an action: ");

                int choice = UserIO.getSelection(1, 10);

                switch (choice){
                    case 1:
                        changeNameView(campUID);
                        break;
                    case 2:
                        changeStartDateView(campUID);
                        break;
                    case 3:
                        changeEndDateView(campUID);
                        break;
                    case 4:
                        changeClosingDateView(campUID);
                        break;
                    case 5:
                        changeStudentGroupView(campUID);
                        break;
                    case 6:
                        changeLocationView(campUID);
                        break;
                    case 7:
                        changeSlotsView(campUID);
                        break;
                    case 8:
                        changeDescriptionView(campUID);
                        break;
                    case 9:
                        changeVisibilityView(campUID);
                        break;
                    case 10:
                        System.out.println("Cancelling Editing...");
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

    public static void changeNameView(UUID campUID){
        System.out.print("Enter camp name: ");
        String campName = UserIO.getStringResponse();
        CampManager.updateCampName(campUID, campName);
    }

    public static void changeStartDateView(UUID campUID){
        System.out.print("Enter camp start date: ");
        String date = UserIO.getStringResponse();
        LocalDate startDate = LocalDate.parse(date);
        CampManager.updateStartDate(campUID, startDate);
    }

    public static void changeEndDateView(UUID campUID){
        System.out.print("Enter camp end date: ");
        String date = UserIO.getStringResponse();
        LocalDate endDate = LocalDate.parse(date);
        CampManager.updateEndDate(campUID, endDate);
    }

    public static void changeClosingDateView(UUID campUID){
        System.out.print("Enter camp registration closing date: ");
        String date = UserIO.getStringResponse();
        LocalDate closingDate = LocalDate.parse(date);
        CampManager.updateClosingDate(campUID, closingDate);
    }

    public static void changeStudentGroupView(UUID campUID){
        System.out.print("Enter student group: ");
        String group = UserIO.getStringResponse();
        UserGroup userGroup = UserGroup.valueOf(group.toUpperCase());
        CampManager.updateStudentGroup(campUID, userGroup);
    }

    public static void changeLocationView(UUID campUID){
        System.out.print("Enter camp location: ");
        String location = UserIO.getStringResponse();
        CampManager.updateLocation(campUID, location);
    }

    public static void changeSlotsView(UUID campUID){
        System.out.print("Enter number of slots: ");
        int numSlots = UserIO.getIntResponse();
        CampManager.updateNumSlots(campUID, numSlots);
    }

    public static void changeDescriptionView(UUID campUID){
        System.out.print("Enter camp description: ");
        String description = UserIO.getStringResponse();
        CampManager.updateDescription(campUID, description);
    }

    public static void changeVisibilityView(UUID campUID){
        System.out.print("Toggled Visibility");
        CampManager.updateVisibility(campUID);
    }
}
