package view;

import controller.CampManager;
import controller.UserManager;
import helper.UserIO;
import model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class StaffView {
    public static void renderView(String staffID){
        System.out.println("\nLogged in as " + staffID);

        while(true){
            User user = UserManager.getUser(staffID);
            Staff staff = (Staff) user;
            try{
                System.out.println("======================= HOME MENU =======================");
                System.out.println("1) View All Camps");
                System.out.println("2) Select Camp");
                System.out.println("3) Create new Camp");
                System.out.println("4) View all Suggestions");
                System.out.println("5) Accept/Reject Suggestions");
                System.out.println("6) Change Password");
                System.out.println("7) Logout");
                System.out.println("=========================================================\n");

                System.out.print("Select an action: ");

                int choice = UserIO.getSelection(1, 7);

                switch (choice){
                    case 1:
                        allCampView();
                        break;
                    case 2:
                        staffCampView(staff);
                        break;
                    case 3:
                        createCampView(staff);
                        break;
                    case 4:
                        allSuggestionsView(staff);
                        break;
                    case 5:
                        processSuggestionView(staff);
                        break;
                    case 6:
                        AppView.changePasswordView(staff);
                        break;
                    case 7:
                        System.out.println("Logging Out...");
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

    public static void allCampView(){
        HashMap<UUID, Camp> allCamps = CampManager.getAllCamps();

        System.out.println("\n=========================================================\n");

        for(UUID key : allCamps.keySet()){
            allCamps.get(key).printCampDetails();
        }

        System.out.println("\n=========================================================\n");
    }

    public static void staffCampView(Staff staff){

        Set<UUID> campKeys = staff.getCampIDs();
        HashMap<UUID, Camp> allCamps = CampManager.getAllCamps();

        // maps choice to UID of camp in hashmap
        HashMap<Integer, UUID> campSelection = new HashMap<>();

        // prints all camp staff has created
        System.out.println("\n=========================================================\n");

        int count = 1;
        for(UUID key : campKeys){
            System.out.println("Camp Number: " + count);
            System.out.println(key);
            allCamps.get(key).printCampDetails();
            campSelection.put(count, key);
            count++;
        }

        System.out.println("\n=========================================================\n");

        System.out.print("Select a camp: ");
        int choice = UserIO.getSelection(1, count);

        UUID campUID = campSelection.get(choice);
        System.out.println(campUID);

        selectCampView(staff, campUID);
    }


    public static void selectCampView(Staff staff, UUID campUID){
        while(true){
            try{
                System.out.println("======================= SELECT MENU =======================");
                System.out.println("1) Edit Camp Details");
                System.out.println("2) View Registered Attendees");
                System.out.println("3) Delete Camp");
                System.out.println("4) Cancel Selection");
                System.out.println("===========================================================\n");

                System.out.print("Select an action: ");

                int choice = UserIO.getSelection(1, 4);

                switch (choice){
                    case 1:
                        CampEditView.editCampView(campUID);
                        break;
                    case 2:
                        allAttendeesView(campUID);
                        break;
                    case 3:
                        deleteCampView(campUID);
                        break;
                    case 4:
                        System.out.println("Cancelling Selection...");
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

    public static void allAttendeesView(UUID campUID){
        System.out.println("print all attendees in the selected camp");
    }

    public static void deleteCampView(UUID campUID){
        System.out.println("Are you sure you want to delete this camp?");
        System.out.print("Enter 'YES' to confirm deletion: ");
        String confirmation = UserIO.getStringResponse();
        if(confirmation.equals("YES")){
            // TODO: DELETE CAMP FROM DB
            return;
        }
        System.out.println("Deletion Unsuccessful. Try again");
    }

    public static void createCampView(Staff staff){
        System.out.print("Enter the name of camp: ");
        String name = UserIO.getStringResponse();

        System.out.print("Enter the start date of the camp(yyyy-MM-dd): ");
//        String sDate = UserIO.getStringResponse();
        LocalDate startDate = UserIO.getDateResponse();

        System.out.print("Enter the end date of the camp(yyyy-MM-dd): ");
        String eDate = UserIO.getStringResponse();
        LocalDate endDate = LocalDate.parse(eDate);

        System.out.print("Enter the registration closing date of the camp(yyyy-MM-dd): ");
        String cDate = UserIO.getStringResponse();
        LocalDate closingDate = LocalDate.parse(cDate);

        System.out.print("Enter group that camp is open to ");
        String group = UserIO.getStringResponse();
        UserGroup userGroup = UserGroup.valueOf(group.toUpperCase());

        System.out.print("Enter the location of camp: ");
        String location = UserIO.getStringResponse();

        System.out.print("Enter the number of slots: ");
        int totalSlots = UserIO.getIntResponse();

        System.out.print("Enter the description of your camp: ");
        String description = UserIO.getStringResponse();

        boolean isVisible = true;

        Camp newCamp = new Camp(name, startDate, endDate,  closingDate,  userGroup,  location,  totalSlots,  description,  staff,  isVisible);
        CampManager.addNewCamp(newCamp, staff);
    }

    public static void allSuggestionsView(Staff staff){
        System.out.println("show all suggestions");
    }

    public static void processSuggestionView(Staff staff){
        System.out.println("accept/reject suggestions");
    }
}
