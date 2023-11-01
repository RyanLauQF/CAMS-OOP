package view;

import controller.CampManager;
import helper.UserIO;
import model.Camp;
import model.Student;

import java.util.HashMap;
import java.util.UUID;

public class StudentView {
    public static void renderView(Student student){

        System.out.println("\nLogged in as " + student.getName());

        while(true){
            if(student.isCampCommitteeMember()){
                CampCommView.renderView(student);
                return;
            }
            else{
                try{
                    System.out.println("======================= HOME MENU =======================");
                    System.out.println("1) View all available camps within faculty");
                    System.out.println("2) Register for camp");
                    System.out.println("3) View registered camps");
                    System.out.println("4) View all enquiries");
                    System.out.println("5) View enquiry reply");
                    System.out.println("6) Change password");
                    System.out.println("7) Logout");
                    System.out.println("=========================================================\n");

                    System.out.print("Select an action: ");
                    int choice = UserIO.getSelection(1, 7);

                    switch (choice){
                        case 1:
                            availableCampsView(student);
                            break;
                        case 2:
                            registerCampView(student);
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
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
    }

    public static void availableCampsView(Student student){
        HashMap<UUID, Camp> filteredCamps = CampManager.getCampInFaculty(student.getFaculty());

        // TODO: make this look better
        for(UUID key : filteredCamps.keySet()){
            Camp camp = CampManager.getCamp(key);
            camp.printCampDetails();
        }
    }

    public static void registerCampView(Student student){
        HashMap<UUID, Camp> filteredCamps = CampManager.getCampInFaculty(student.getFaculty());
        HashMap<Integer, UUID> selection = new HashMap<>();

        int count = 1;
        for(UUID key : filteredCamps.keySet()){
            System.out.println("Camp: " + count);
            Camp camp = CampManager.getCamp(key);
            camp.printCampDetails();
            selection.put(count, key);
            count++;
        }
        System.out.print("Select camp you will like to register for: ");
        int selected = UserIO.getSelection(1, count);
        UUID selectedCampID = selection.get(selected);

        while(true){
            try{
                System.out.println("Would you like to register as a:");
                System.out.println("1) Attendee");
                System.out.println("2) Camp Committee Member");
                System.out.println("3) Cancel Registration\n");

                System.out.print("Enter selection: ");
                int choice = UserIO.getSelection(1, 3);

                switch (choice){
                    case 1:
                        CampManager.registerStudent(student, selectedCampID);
                        return;
                    case 2:
                        CampManager.registerCommMember(student, selectedCampID);
                        return;
                    case 3:
                        System.out.println("Cancelling Registration...");
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

    public static void registeredCampsView(){

    }

    public static void enquiriesView(){

    }
}
