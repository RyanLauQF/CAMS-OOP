package helper;

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

    public static String getStringResponse(){
        return sc.next();
    }

    public static int getIntResponse() {
        int response;
        while (true) {
            try {
                response = sc.nextInt();
                break; // Valid input, break out of the loop
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // Consume invalid input
            }
        }
        sc.nextLine(); // Consume the newline character left in the input buffer
        return response;
    }

    public static void close(){
        sc.close();
    }
}
