package view;

import controller.CampCommMemberManager;
import controller.CampManager;
import controller.StaffManager;
import controller.SuggestionManager;
import helper.ConsoleColours;
import helper.UserIO;
import model.*;

import java.util.*;
import java.util.stream.Collectors;

public class SuggestionView {

    public static void ccmSuggestionView(CampCommMember student) {
        while (true) {
            try {
                System.out.println("\n======================= SUGGESTIONS MENU =======================");
                System.out.println("1) Show all suggestions made by you");
                System.out.println("2) Submit suggestion to update camp details");
                System.out.println("3) Edit Suggestions");
                System.out.println("4) Delete Suggestion");
                System.out.println("5) Exit Suggestion Menu");
                System.out.println("================================================================\n");

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
                System.out.println(e.getMessage());
            }
        }
    }

    public static void showCCMSuggestion(CampCommMember student) {
        Set<UUID> ccmSuggestions = CampCommMemberManager.getCCMSuggestions(student);
        if (ccmSuggestions.isEmpty()) {
            System.out.println(ConsoleColours.YELLOW + "\nThere are no available suggestions!" + ConsoleColours.RESET);
            return;
        }
        // prints all suggestions student has created
        System.out.println("\n=========================================================");
        System.out.println("-----------------------");
        int count = 0;
        for (UUID key : ccmSuggestions) {
            count++;
            System.out.println("Suggestion Number: " + count);
            SuggestionManager.getSuggestion(key).printSuggestionDetails();
            System.out.println("-----------------------");
        }

        System.out.println("=========================================================\n");
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

            System.out.print("Select an action: ");

            int choice = UserIO.getSelection(1, 9);
            if (choice == 9) return;
            SuggestionType typeEnum = SuggestionType.values()[choice - 1];

            System.out.println("Enter suggestion: ");
            String suggestion = UserIO.getStringResponse();
            Suggestion newSuggestion = new Suggestion(suggestion, student, student.getCommCampID(), typeEnum);
            SuggestionManager.addSuggestion(newSuggestion, student);
            CampCommMemberManager.addPoint(student);
            System.out.println(ConsoleColours.GREEN + "\nYour suggestion has been submitted successfully!" + ConsoleColours.RESET);
        } catch (Exception e) {
            System.out.println("Error! " + e.getMessage());
        }
    }

    public static void editSuggestionView(CampCommMember student) {
        Set<UUID> ccmSuggestions = CampCommMemberManager.getCCMSuggestions(student);

        if (ccmSuggestions.isEmpty()) {
            System.out.println(ConsoleColours.YELLOW + "\nThere are no available suggestions!" + ConsoleColours.RESET);
            return;
        }
        HashMap<Integer, UUID> suggestionSelection = new HashMap<>();

        // prints all suggestions student has created
        System.out.println("\n=========================================================\n");

        int count = 0;
        for (UUID key : ccmSuggestions) {
            count++;
            System.out.println(ConsoleColours.BLUE + "Suggestion Number: " + count + ConsoleColours.RESET);
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
        System.out.println(ConsoleColours.GREEN + "\nYou suggestion has been edited successfully!" + ConsoleColours.RESET);
        System.out.println("\n=========================================================\n");
    }

    public static void deleteSuggestionView(CampCommMember student) {
        Set<UUID> ccmSuggestions = CampCommMemberManager.getCCMSuggestions(student);
        if (ccmSuggestions.isEmpty()) {
            System.out.println(ConsoleColours.YELLOW + "\nThere are no available suggestions!" + ConsoleColours.RESET);
            return;
        }
        HashMap<Integer, UUID> suggestionSelection = new HashMap<>();

        // prints all suggestions student has created
        System.out.println("\n=========================================================\n");

        int count = 0;
        for (UUID key : ccmSuggestions) {
            count++;
            System.out.println(ConsoleColours.BLUE + "Suggestion Number: " + count + ConsoleColours.RESET);
            SuggestionManager.getSuggestion(key).printSuggestionDetails();
            System.out.println("\n-----------------------\n");
            suggestionSelection.put(count, key);
        }

        System.out.println("Select suggestion to delete: ");
        int choice = UserIO.getSelection(1, count);
        UUID suggestionUID = suggestionSelection.get(choice);
        if (!SuggestionManager.getSuggestion(suggestionUID).isAccepted()) {
            SuggestionManager.removeSuggestion(suggestionUID, student);
        } else {
            System.out.println("Suggestion is already processed, you cannot delete");
        }
        System.out.println(ConsoleColours.GREEN + "\nYour suggestion has been deleted successfully!" + ConsoleColours.RESET);
        System.out.println("\n=========================================================\n");
    }

    public static void showStaffSuggestionView(Staff staff) {
        Set<UUID> camps = StaffManager.getAllCamps(staff);
        Set<UUID> campSuggestions = new HashSet<>();

        if (camps.isEmpty()) {
            System.out.println("You currently have no camps tagged to you!");
            return;
        }

        // get all suggestions
        for (UUID key : camps) {
            Set<UUID> suggestions = CampManager.getCampSuggestions(key);
            campSuggestions.addAll(suggestions);
        }

        if (campSuggestions.isEmpty()) {
            System.out.println("There are currently no suggestions for your camps.\n");
            return;
        }

        // prints all suggestions for camps staff is in charge of
        System.out.println("\n=========================================================");

        int count = 0;
        System.out.println("--------------------------------");
        for (UUID key : campSuggestions) {
            count++;
            System.out.println(ConsoleColours.BLUE + "Suggestion Number: " + count + ConsoleColours.RESET);
            Suggestion suggestion = SuggestionManager.getSuggestion(key);
            System.out.println("Camp Name: " + CampManager.getCamp(suggestion.getCampID()).getName());
            suggestion.printSuggestionDetails();
            System.out.println("--------------------------------");
        }

        System.out.println("=========================================================\n");
    }

    public static void getSuggestionsForCampView(UUID CampID) {
        Set<UUID> suggestions = CampManager.getCampSuggestions(CampID);
        int count = 0;
        System.out.println("\nSuggestions for Camp " + CampManager.getCamp(CampID).getName());
        System.out.println("\n=========================================================");
        System.out.println("--------------------------------");
        for (UUID key : suggestions) {
            count++;
            System.out.println(ConsoleColours.BLUE + "Suggestion Number: " + count + ConsoleColours.RESET);
            Suggestion suggestion = SuggestionManager.getSuggestion(key);
            suggestion.printSuggestionDetails();
            System.out.println("--------------------------------");
        }
        System.out.println("=========================================================\n");
    }

    public static void approveSuggestionView(Staff staff) {
        Set<UUID> camps = StaffManager.getAllCamps(staff);

        if (camps.isEmpty()) {
            System.out.println("You currently have no camps tagged to you!");
            return;
        }

        HashMap<Integer, UUID> campSelection = new HashMap<>();
        HashMap<Integer, UUID> suggestionSelection = new HashMap<>();

        // prints all suggestions for camps staff is in charge of
        System.out.println("\n=========================================================");

        int campCount = 0;
        int suggestionCount = 0;

        for (UUID key : camps) {
            Camp camp = CampManager.getCamp(key);
            Set<UUID> campSuggestions = CampManager.getCampSuggestions(key);
            List<Suggestion> unapprovedSuggestions = (List<Suggestion>) CampManager.getCampSuggestions(key).stream().map(SuggestionManager::getSuggestion).collect(Collectors.toList());
            unapprovedSuggestions = unapprovedSuggestions.stream().filter(suggestion -> !suggestion.isViewed()).collect(Collectors.toList());
            if (campSuggestions.isEmpty() || unapprovedSuggestions.isEmpty()) {
                continue;
            }
            camp.printCampDetails();
            campCount++;
            System.out.println("------------------------- CAMP " + campCount + " -----------------------");
            for (UUID id : campSuggestions) {
                Suggestion suggestion = SuggestionManager.getSuggestion(id);
                if (suggestion.isViewed()) {
                    continue;
                }
                suggestionCount++;
                System.out.println(ConsoleColours.BLUE + "Suggestion Number: " + suggestionCount + ConsoleColours.RESET);
                System.out.println("-----------------------");
                suggestion.printSuggestionDetails();
                suggestionSelection.put(suggestionCount, id);
            }
            campSelection.put(campCount, key);
            System.out.println("--------------------------------------------------------");
        }
        if (campCount == 0) {
            System.out.println(ConsoleColours.YELLOW + "\nYou have no pending suggestions!" + ConsoleColours.RESET);
            return;
        }
        System.out.print("Select suggestion to accept/reject: ");
        int choice = UserIO.getSelection(1, suggestionCount);

        UUID suggestionUID = suggestionSelection.get(choice);
        System.out.print("Accept? (Y to accept: N to reject): ");
        boolean reply = UserIO.getBoolResponse();
        SuggestionManager.setStatus(suggestionUID, reply);

        System.out.println(ConsoleColours.GREEN + "\nThe suggestion has been " + (reply ? "approved" : "rejected") + " successfully!" + ConsoleColours.RESET);
        System.out.println("\n=========================================================\n");
    }
}
