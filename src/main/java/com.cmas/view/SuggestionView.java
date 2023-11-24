package com.cmas.view;

import com.cmas.helper.ConsoleColours;
import com.cmas.helper.UserIO;
import com.cmas.model.*;
import com.cmas.controller.CampCommMemberManager;
import com.cmas.controller.CampManager;
import com.cmas.controller.StaffManager;
import com.cmas.controller.SuggestionManager;

import java.util.*;
import java.util.stream.Collectors;


/**
 * View class for handling suggestions made by Camp Committee Members and Staff.
 *
 * @author Seung Yeon, Shao Chong
 * @version 1.0
 * @since 2023-11-18
 */
public class SuggestionView {

    /**
     * Displays the SUGGESTIONS MENU for Camp Committee Members (CCM).
     *
     * @param student The CampCommMember object representing the CCM.
     */
    public static void ccmSuggestionView(CampCommMember student) {
        while (true) {
            try {
                System.out.println("\n=================== SUGGESTIONS MENU ====================");
                System.out.print(ConsoleColours.BLUE);
                System.out.println("1) Show all suggestions made by you");
                System.out.println("2) Submit suggestion to update camp details");
                System.out.println("3) Edit Suggestions");
                System.out.println("4) Delete Suggestion");
                System.out.println("5) Exit Suggestion Menu");
                System.out.print(ConsoleColours.RESET);
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

    /**
     * Shows all suggestions made by a specific Camp Committee Member.
     *
     * @param student The CampCommMember object representing the CCM.
     */
    public static void showCCMSuggestion(CampCommMember student) {
        Set<UUID> ccmSuggestions = CampCommMemberManager.getCCMSuggestions(student);
        if (ccmSuggestions.isEmpty()) {
            System.out.println(ConsoleColours.YELLOW + "\nThere are no available suggestions!" + ConsoleColours.RESET);
            return;
        }
        // prints all suggestions student has created
        System.out.println("\n=========================================================");
        System.out.println("---------------------------------------------------------");
        int count = 0;
        for (UUID key : ccmSuggestions) {
            count++;
            System.out.println(ConsoleColours.BLUE + "Suggestion No.: " + count + ConsoleColours.RESET);
            SuggestionManager.getSuggestion(key).printSuggestionDetails();
            System.out.println("---------------------------------------------------------");
        }

        System.out.println("=========================================================\n");
    }

    /**
     * Displays the view for creating a new suggestion by a Camp Committee Member.
     *
     * @param student The CampCommMember object representing the CCM.
     */
    public static void createSuggestionView(CampCommMember student) {
        UUID campUID = CampCommMemberManager.getCampID(student);
        Camp camp = CampManager.getCamp(campUID);
        String campName = camp.getName();

        System.out.print("Create suggestion for " + campName + "\n");
        try {
            System.out.println("======================= SUGGESTION MENU =======================");
            System.out.print(ConsoleColours.BLUE);
            System.out.println("1) Change Name");
            System.out.println("2) Change Start Date");
            System.out.println("3) Change End Date");
            System.out.println("4) Change Registration Closing Date");
            System.out.println("5) Change Student Group");
            System.out.println("6) Change Location");
            System.out.println("7) Change Number of Slots");
            System.out.println("8) Change Description");
            System.out.println("9) Go back to suggestion menu");
            System.out.print(ConsoleColours.RESET);
            System.out.print("\nSelect an action: ");

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

    /**
     * Displays the view for editing an existing suggestion made by a Camp Committee Member.
     *
     * @param student The CampCommMember object representing the CCM.
     */
    public static void editSuggestionView(CampCommMember student) {
        Set<UUID> ccmSuggestions = CampCommMemberManager.getCCMSuggestions(student);

        if (ccmSuggestions.isEmpty()) {
            System.out.println(ConsoleColours.YELLOW + "\nThere are no available suggestions!" + ConsoleColours.RESET);
            return;
        }
        HashMap<Integer, UUID> suggestionSelection = new HashMap<>();

        // prints all suggestions student has created
        System.out.println("\n=========================================================");
        System.out.println("---------------------------------------------------------");
        int count = 0;
        for (UUID key : ccmSuggestions) {
            count++;
            System.out.println(ConsoleColours.BLUE + "Suggestion No.: " + count + ConsoleColours.RESET);
            SuggestionManager.getSuggestion(key).printSuggestionDetails();
            System.out.println("---------------------------------------------------------");
            suggestionSelection.put(count, key);
        }

        System.out.println("=========================================================\n");
        System.out.println("Select suggestion to edit (0 to exit):");
        int choice = UserIO.getSelection(0, count);
        if (choice == 0) return;
        UUID suggestionUID = suggestionSelection.get(choice);

        // check if it's processed
        if (!SuggestionManager.getSuggestion(suggestionUID).isAccepted()) {
            System.out.println("Enter new suggestion: ");
            String suggestion = UserIO.getStringResponse();
            SuggestionManager.updateSuggestion(suggestion, suggestionUID);
            System.out.println(ConsoleColours.GREEN + "\nYou suggestion has been edited successfully!" + ConsoleColours.RESET);
        } else {
            System.out.println(ConsoleColours.RED + "\nSuggestion is already processed, you cannot edit" + ConsoleColours.RESET);
        }
    }

    /**
     * Displays the view for deleting an existing suggestion made by a Camp Committee Member.
     *
     * @param student The CampCommMember object representing the CCM.
     */
    public static void deleteSuggestionView(CampCommMember student) {
        Set<UUID> ccmSuggestions = CampCommMemberManager.getCCMSuggestions(student);
        if (ccmSuggestions.isEmpty()) {
            System.out.println(ConsoleColours.YELLOW + "\nThere are no available suggestions!" + ConsoleColours.RESET);
            return;
        }
        HashMap<Integer, UUID> suggestionSelection = new HashMap<>();

        // prints all suggestions student has created
        System.out.println("\n=========================================================\n");
        System.out.println("---------------------------------------------------------");
        int count = 0;
        for (UUID key : ccmSuggestions) {
            count++;
            System.out.println(ConsoleColours.BLUE + "Suggestion No.: " + count + ConsoleColours.RESET);
            SuggestionManager.getSuggestion(key).printSuggestionDetails();
            System.out.println("---------------------------------------------------------");
            suggestionSelection.put(count, key);
        }

        System.out.println("=========================================================\n");
        System.out.println("Select suggestion to delete (0 to exit): ");
        int choice = UserIO.getSelection(0, count);
        if (choice == 0) return;
        UUID suggestionUID = suggestionSelection.get(choice);
        if (!SuggestionManager.getSuggestion(suggestionUID).isAccepted()) {
            SuggestionManager.removeSuggestion(suggestionUID, student);
            System.out.println(ConsoleColours.GREEN + "\nYour suggestion has been deleted successfully!" + ConsoleColours.RESET);
        } else {
            System.out.println("Suggestion is already processed, you cannot delete");
        }
    }

    /**
     * Displays the view for showing all suggestions for camps managed by a specific Staff member.
     *
     * @param staff The Staff object representing the staff member.
     */
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
            System.out.println(ConsoleColours.BLUE + "Suggestion No.: " + count + ConsoleColours.RESET);
            SuggestionManager.printSuggestionDetails(key);
            System.out.println("--------------------------------");
        }

        System.out.println("=========================================================\n");
    }

    /**
     * Displays a view of camp suggestions associated with a specified camp by printing details for each suggestion
     * along with a number incremented based on order.
     *
     * @param CampID The UUID of the camp for which suggestions are to be displayed.
     */
    public static void getSuggestionsForCampView(UUID CampID) {
        Set<UUID> suggestions = CampManager.getCampSuggestions(CampID);
        int count = 0;
        System.out.println("\nSuggestions for Camp " + CampManager.getCamp(CampID).getName());
        System.out.println("\n=========================================================");
        System.out.println("--------------------------------");
        for (UUID key : suggestions) {
            count++;
            System.out.println(ConsoleColours.BLUE + "Suggestion No.: " + count + ConsoleColours.RESET);
            Suggestion suggestion = SuggestionManager.getSuggestion(key);
            suggestion.printSuggestionDetails();
            System.out.println("--------------------------------");
        }
        System.out.println("=========================================================\n");
    }

    /**
     * Displays the view for approving or rejecting suggestions for camps managed by a specific Staff member.
     *
     * @param staff The Staff object representing the staff member.
     */
    public static void approveSuggestionView(Staff staff) {
        Set<UUID> camps = StaffManager.getAllCamps(staff);

        if (camps.isEmpty()) {
            System.out.println("You currently have no camps tagged to you!");
            return;
        }

        HashMap<Integer, UUID> suggestionSelection = new HashMap<>();

        // prints all suggestions for camps staff is in charge of
        System.out.println("\n=========================================================");

        int campCount = 0;
        int suggestionCount = 0;

        for (UUID key : camps) {
            Camp camp = CampManager.getCamp(key);
            Set<UUID> campSuggestions = CampManager.getCampSuggestions(key);
            List<Suggestion> unapprovedSuggestions = CampManager.getCampSuggestions(key).stream().map(SuggestionManager::getSuggestion).collect(Collectors.toList());
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
                System.out.println(ConsoleColours.BLUE + "Suggestion No.: " + suggestionCount + ConsoleColours.RESET);
                System.out.println("-----------------------");
                suggestion.printSuggestionDetails();
                suggestionSelection.put(suggestionCount, id);
            }
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
