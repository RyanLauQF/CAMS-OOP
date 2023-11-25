package com.cmas.view;

import com.cmas.helper.ConsoleColours;
import com.cmas.helper.UserIO;
import com.cmas.model.Camp;
import com.cmas.model.UserGroup;
import com.cmas.controller.CampManager;

import java.time.LocalDate;
import java.util.UUID;


/**
 * View class for rendering the camp editing interface and managing interactions related to camp modifications.
 * Provides methods for editing various camp details such as name, dates, location, and visibility.
 *
 * @author Ryan Lau
 * @version 1.0
 * @since 2023-11-14
 */
public class CampEditView {

    /**
     * Renders camp editing interface and providing selection menu for modifying camp details.
     *
     * @param campUID The UUID of camp to be edited.
     */
    public static void editCampView(UUID campUID) {
        while (true) {
            try {
                System.out.println("\n======================= EDIT MENU =======================");
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

                switch (choice) {
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
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Renders view for changing the camp name.
     *
     * @param campUID The UUID of camp to be edited.
     */
    public static void changeNameView(UUID campUID) {
        System.out.print("Enter new camp name: ");
        String campName = UserIO.getStringResponse();
        CampManager.updateCampName(campUID, campName);
    }

    /**
     * Renders view for changing the camp start date.
     *
     * @param campUID The UUID of camp to be edited.
     */
    public static void changeStartDateView(UUID campUID) {

        Camp camp = CampManager.getCamp(campUID);
        LocalDate closingDate = camp.getClosingDate();
        while (true) {
            System.out.print("Enter new camp start date: ");
            LocalDate startDate = UserIO.getDateResponse();
            if (!startDate.isAfter(closingDate)) {
                System.out.print(ConsoleColours.RED + "Start date cannot be before the registration closing date!\n" + ConsoleColours.RESET);
                continue;
            }
            CampManager.updateStartDate(campUID, startDate);
            break;
        }
    }

    /**
     * Renders view for changing the camp end date.
     *
     * @param campUID The UUID of camp to be edited.
     */
    public static void changeEndDateView(UUID campUID) {

        Camp camp = CampManager.getCamp(campUID);
        LocalDate startDate = camp.getStartDate();
        while (true) {
            System.out.print("Enter new camp end date: ");
            LocalDate endDate = UserIO.getDateResponse();
            if (!endDate.isAfter(startDate)) {
                System.out.print(ConsoleColours.RED + "End date has to be after the camp start date!\n" + ConsoleColours.RESET);
                continue;
            }
            CampManager.updateEndDate(campUID, endDate);
            break;
        }
    }

    /**
     * Renders view for changing the camp registration closing date.
     *
     * @param campUID The UUID of camp to be edited.
     */
    public static void changeClosingDateView(UUID campUID) {
        Camp camp = CampManager.getCamp(campUID);
        LocalDate startDate = camp.getStartDate();

        while (true) {
            System.out.print("Enter new camp registration closing date: ");
            LocalDate closingDate = UserIO.getDateResponse();
            if (!closingDate.isBefore(startDate)) {
                System.out.print(ConsoleColours.RED + "Registration has to close before the start of the camp!\n" + ConsoleColours.RESET);
                continue;
            }
            CampManager.updateClosingDate(campUID, closingDate);
            break;
        }
    }

    /**
     * Renders view for changing the camp's student group.
     *
     * @param campUID The UUID of camp to be edited.
     */
    public static void changeStudentGroupView(UUID campUID) {
        System.out.print("Enter student group: ");
        String group = UserIO.getStringResponse();
        UserGroup userGroup = UserGroup.valueOf(group.toUpperCase());
        CampManager.updateStudentGroup(campUID, userGroup);
    }

    /**
     * Renders view for changing the camp location.
     *
     * @param campUID The UUID of camp to be edited.
     */
    public static void changeLocationView(UUID campUID) {
        System.out.print("Enter camp location: ");
        String location = UserIO.getStringResponse();
        CampManager.updateLocation(campUID, location);
    }

    /**
     * Renders view for changing the number of slots available in the camp.
     *
     * @param campUID The UUID of camp to be edited.
     */
    public static void changeSlotsView(UUID campUID) {
        System.out.print("Enter number of slots: ");
        int numSlots = UserIO.getIntResponse();
        CampManager.updateNumSlots(campUID, numSlots);
    }

    /**
     * Renders view for changing the camp description.
     *
     * @param campUID The UUID of camp to be edited.
     */
    public static void changeDescriptionView(UUID campUID) {
        System.out.print("Enter camp description: ");
        String description = UserIO.getStringResponse();
        CampManager.updateDescription(campUID, description);
    }

    /**
     * Renders view for toggling the camp visibility.
     *
     * @param campUID The UUID of camp to be edited.
     */
    public static void changeVisibilityView(UUID campUID) {
        Camp camp = CampManager.getCamp((campUID));
        if (camp.isVisible() && (camp.getRegisteredAttendees() != null || camp.getRegisteredCommMembers() != null)){
            System.out.print("Camp already has attendees. Camp visibility cannot be turned off.");
            return;
        }
        CampManager.updateVisibility(campUID);
        System.out.print("Visibility toggled to: " + (camp.isVisible() ? "Visible" : "Not visible") + "\n");
    }
}
