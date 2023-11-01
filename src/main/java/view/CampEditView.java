package view;

import helper.UserIO;

import java.util.UUID;

public class CampEditView {
    public static void editCampView(UUID campUID){
        while(true){
            try{
                System.out.println("======================= EDIT MENU =======================");
                System.out.println("1) Change Name");
                System.out.println("2) Change Start Date");
                System.out.println("3) Change End Date");
                System.out.println("4) Change Registration Closing Date");
                System.out.println("5) Change Student Group");
                System.out.println("6) Change Location");
                System.out.println("7) Change Number of Slots");
                System.out.println("8) Change Description");
                System.out.println("9) Cancel Editing");
                System.out.println("=========================================================\n");

                System.out.print("Select an action: ");

                int choice = UserIO.getSelection(1, 9);

                switch (choice){
                    case 1:
                        changeNameView();
                        break;
                    case 2:
                        changeStartDateView();
                        break;
                    case 3:
                        changeEndDateView();
                        break;
                    case 4:
                        changeClosingDateView();
                        break;
                    case 5:
                        changeStudentGroupView();
                        break;
                    case 6:
                        changeLocationView();
                        break;
                    case 7:
                        changeSlotsView();
                        break;
                    case 8:
                        changeDescriptionView();
                        break;
                    case 9:
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

    public static void changeNameView(){

    }

    public static void changeStartDateView(){

    }

    public static void changeEndDateView(){

    }

    public static void changeClosingDateView(){

    }

    public static void changeStudentGroupView(){

    }

    public static void changeLocationView(){

    }

    public static void changeSlotsView(){

    }

    public static void changeDescriptionView(){

    }
}
