package view;

import controller.*;
import helper.UserIO;
import model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class SuggestionView {
    public static void ccmSuggestionView(CampCommMember student) {
        while (true) {
            try {
                System.out.println("======================= SUGGESTIONS MENU =======================");
                System.out.println("1) Show all suggestions made by me");
                System.out.println("2) Submit suggestion to update camp details");
                System.out.println("3) Edit Suggestions");
                System.out.println("4) Delete Suggestion");
                System.out.println("5) Exit Suggestion Menu");
                System.out.println("=========================================================\n");

                System.out.print("Select an action: ");

                int choice = UserIO.getSelection(1, 5);

                switch (choice) {
                    case 1:
                        showCCMSuggestion(student);
                        break;
                    case 2:
                        createSuggestionView(student);
                        break;
                    case 3:
                        editSuggestionView(student);
                        break;
                    case 4:
                        deleteSuggestionView(student);
                        break;
                    case 5:
                        System.out.println("Exiting enquiry menu...");
                        return;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    public static void showCCMSuggestion(CampCommMember student) {
        Set<UUID> ccmSuggestions = CampCommMemberManager.getCCMSuggestions(student);

        // prints all suggestions student has created
        System.out.println("\n=========================================================\n");
        if (ccmSuggestions.isEmpty()) {
            System.out.println("No suggestions");
        } else {
            int count = 0;
            for (UUID key : ccmSuggestions) {
                count++;
                System.out.println("Suggestion Number: " + count);
                SuggestionManager.getSuggestion(key).printSuggestionDetails();
                System.out.println("\n-----------------------\n");
            }
        }

        System.out.println("\n=========================================================\n");
    }

    // idk if we should limit each CCM student to one suggestion, bc they only can suggest to the camp they are in charge of
    public static void createSuggestionView(CampCommMember student) {
        UUID campUID = CampCommMemberManager.getCampID(student);
        Camp camp = CampManager.getCamp(campUID);
        String campName = camp.getName();

        System.out.print("Create suggestion for " + campName + "\n");
        try {
            System.out.println("======================= SUGGESTION MENU =======================");
            System.out.println("\nOptions: ");
            System.out.println("1) Change Name");
            System.out.println("2) Change Start Date");
            System.out.println("3) Change End Date");
            System.out.println("4) Change Registration Closing Date");
            System.out.println("5) Change Student Group");
            System.out.println("6) Change Location");
            System.out.println("7) Change Number of Slots");
            System.out.println("8) Change Description");
            System.out.println("9) Go back to suggestion menu");
            System.out.println("10) Exit program\n");

            System.out.print("Select an action: ");

            int choice = UserIO.getSelection(1, 8);
            SuggestionType typeEnum = SuggestionType.values()[choice - 1];

            System.out.println("Enter suggestion: ");
            String suggestion = UserIO.getStringResponse();
            Suggestion newSugggestion = new Suggestion(suggestion, student, student.getCommCampID(), typeEnum);
            SuggestionManager.addSuggestion(newSugggestion, student);
            CampCommMemberManager.addPoint(student);
            System.out.println("\nSuggestion submitted!");
            System.out.println("Camp Comm Member Points: " + CampCommMemberManager.getPoints(student));
        } catch (Exception e) {
            System.out.println("Error! " + e.getMessage());
        }
    }

    public static void editSuggestionView(CampCommMember student) {
        Set<UUID> ccmSuggestions = CampCommMemberManager.getCCMSuggestions(student);

        HashMap<Integer, UUID> suggestionSelection = new HashMap<>();

        // prints all suggestions student has created
        System.out.println("\n=========================================================\n");

        int count = 0;
        for (UUID key : ccmSuggestions) {
            count++;
            System.out.println("Suggestion Number: " + count);
            SuggestionManager.getSuggestion(key).printSuggestionDetails();
            System.out.println("\n-----------------------\n");
            suggestionSelection.put(count, key);
        }

        if (count == 0) return;
        System.out.println("Select suggestion to edit: ");
        int choice = UserIO.getSelection(1, count);
        UUID suggestionUID = suggestionSelection.get(choice);

        // check if it's processed
        if (!SuggestionManager.getSuggestion(suggestionUID).isAccepted()) {
            System.out.println("Enter new suggestion: ");
            String suggestion = UserIO.getStringResponse();
            SuggestionManager.updateSuggestion(suggestion, suggestionUID);
        } else {
            System.out.println("Suggestion is already processed, you cannot edit");
        }

        System.out.println("\n=========================================================\n");
    }

    public static void deleteSuggestionView(CampCommMember student) {
        Set<UUID> ccmSuggestions = CampCommMemberManager.getCCMSuggestions(student);

        HashMap<Integer, UUID> suggestionSelection = new HashMap<>();

        // prints all suggestions student has created
        System.out.println("\n=========================================================\n");

        int count = 0;
        for (UUID key : ccmSuggestions) {
            count++;
            System.out.println("Suggestion Number: " + count);
            SuggestionManager.getSuggestion(key).printSuggestionDetails();
            System.out.println("\n-----------------------\n");
            suggestionSelection.put(count, key);
        }

        if (count == 0) return;
        System.out.println("Select suggestion to delete: ");
        int choice = UserIO.getSelection(1, count);
        UUID suggestionUID = suggestionSelection.get(choice);
        if (!SuggestionManager.getSuggestion(suggestionUID).isAccepted()) {
            SuggestionManager.removeSuggestion(suggestionUID, student);
        } else {
            System.out.println("Suggestion is already processed, you cannot delete");
        }

        System.out.println("\n=========================================================\n");
    }

    public static void showStaffSuggestionView(Staff staff) {
        Set<UUID> camps = StaffManager.getAllCamps(staff);

        // prints all suggestions for camps staff is in charge of
        System.out.println("\n=========================================================\n");

        int count = 0;
        for (UUID key : camps) {
            count++;
            System.out.println("Camp Number: " + count);
            Camp camp = CampManager.getCamp(key);
            camp.printCampDetails();
            Set<UUID> campSuggestions = CampManager.getCampSuggestions(key);

            for (UUID id : campSuggestions) {
                Suggestion suggestion = SuggestionManager.getSuggestion(id);
                suggestion.printSuggestionDetails();
            }
            System.out.println("\n-----------------------\n");
        }
    }

    public static void approveSuggestionView(Staff staff) {
        Set<UUID> camps = StaffManager.getAllCamps(staff);

        HashMap<Integer, UUID> campSelection = new HashMap<>();
        HashMap<Integer, UUID> suggestionSelection = new HashMap<>();

        // prints all suggestions for camps staff is in charge of
        System.out.println("\n=========================================================\n");

        int campCount = 0;
        int suggestionCount = 0;

        for (UUID key : camps) {
            campCount++;
            System.out.println("\n-----------CAMP " + campCount + "------------\n");
            Camp camp = CampManager.getCamp(key);
            camp.printCampDetails();
            Set<UUID> campSuggestions = CampManager.getCampSuggestions(key);

            for (UUID id : campSuggestions) {
                suggestionCount++;
                Suggestion suggestion = SuggestionManager.getSuggestion(id);
                System.out.println("Suggestion Number: " + suggestionCount);
                System.out.println("\n-----------------------\n");
                suggestion.printSuggestionDetails();
                suggestionSelection.put(suggestionCount, id);
            }
            campSelection.put(campCount, key);
            System.out.println("\n-----------------------\n");
        }
        if (campCount == 0) {
            return;
        }
        System.out.print("Select suggestion to accept/reject: ");
        int choice = UserIO.getSelection(1, suggestionCount);

        UUID suggestionUID = suggestionSelection.get(choice);
        System.out.print("Accept? (Y to accept: N to reject): ");
        String reply = UserIO.getStringResponse();

        while (true) {
            if (reply.strip().equalsIgnoreCase("y")) {
                SuggestionManager.setStatus(suggestionUID, true);
                return;
            } else if (reply.strip().equalsIgnoreCase("n")) {
                SuggestionManager.setStatus(suggestionUID, false);
                return;
            } else {
                System.out.println("Invalid input. Key either 'y' or 'n'");
            }
        }
    }
}
