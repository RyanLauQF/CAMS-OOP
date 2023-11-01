package helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserIO {
    public static Scanner sc = new Scanner(System.in);

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

    public static LocalDate getDateResponse(){
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

    public static String getStringResponse(){
        return sc.nextLine();
    }

    public static void close(){
        sc.close();
    }
}
