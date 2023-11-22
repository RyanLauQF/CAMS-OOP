package com.cmas.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


/**
 * Utility class for user input/output operations. Provides methods for obtaining various types of user responses,
 * such as integers, strings, and dates, with input validation.
 *
 * @author Ryan Lau, Shao Chong
 * @version 1.0
 * @since 2023-11-18
 */
public class UserIO {

    /**
     * Scanner object for reading user input.
     */
    public static Scanner sc = new Scanner(System.in);

    /**
     * Retrieves a user selection within specified range of [lowestChoiceIdx, highestChoiceIdx]
     *
     * @param lowestChoiceIdx  The lowest allowed choice index.
     * @param highestChoiceIdx The highest allowed choice index.
     * @return The user's selected integer choice.
     */
    public static int getSelection(int lowestChoiceIdx, int highestChoiceIdx) {
        int choice;
        do {
            while (!sc.hasNextInt()) {
                System.out.println("Invalid Choice! Please enter a valid integer.");
                sc.nextLine();
                System.out.print("Select an action: ");
            }
            choice = sc.nextInt();
            sc.nextLine();

            if (choice < lowestChoiceIdx || choice > highestChoiceIdx) {
                System.out.println("Choice must be between " + lowestChoiceIdx + " and " + highestChoiceIdx + ".");
                System.out.print("Select an action: ");
            }
        } while (choice < lowestChoiceIdx || choice > highestChoiceIdx);

        return choice;
    }

    /**
     * Retrieves a user-inputted date in the format YYYY-MM-DD.
     *
     * @return The user's entered LocalDate.
     */
    public static LocalDate getDateResponse() {
        LocalDate date = null;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adjust the format as needed

        boolean isValid = false;
        while (!isValid) {
            String input = sc.nextLine();

            try {
                date = LocalDate.parse(input, dateFormatter);
                isValid = true;
            } catch (Exception e) {
                System.out.print("Invalid date format. Please enter a date in the format yyyy-MM-dd: ");
            }
        }

        return date;
    }

    /**
     * Retrieves a user-inputted integer. Input validation and re-prompts user upon invalid input.
     *
     * @return The user's entered integer.
     */
    public static int getIntResponse() {
        int response;
        while (true) {
            try {
                response = sc.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // read invalid input and try again
            }
        }
        sc.nextLine(); // read newline character left in the input buffer
        return response;
    }

    /**
     * Retrieves a user-inputted string.
     *
     * @return The user's entered string.
     */
    public static String getStringResponse() {
        return sc.nextLine();
    }

    /**
     * Retrieves a user-inputted boolean.
     *
     * @return The user's choice of yes/no
     */
    public static boolean getBoolResponse() {
        while (true) {
            String input = getStringResponse();
            if (input.equalsIgnoreCase("Y")) {
                return true;
            }
            if (input.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println("Invalid input. Please enter Y for yes, N for no.");
        }
    }

    /**
     * Closes the Scanner object to release resources.
     */
    public static void close() {
        sc.close();
    }
}
