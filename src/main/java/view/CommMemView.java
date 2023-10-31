package view;

import model.CampCommMember;

import java.util.Scanner;

public class CommMemView {
    public static void renderView(CampCommMember commMember){

        // render committee member view
        Scanner sc = new Scanner(System.in);
        System.out.println("\nLogged in as Camp Committee Member");

        while(true){
            try{
                System.out.println("========================================================");
                System.out.println("1) Implement function");
                System.out.println("2) Logout\n");
                System.out.print("Select an action: ");

                while(!sc.hasNextInt()){
                    System.out.println("Invalid Choice!");
                    sc.nextLine();
                    System.out.print("Select an action: ");
                }
                int choice = sc.nextInt();

                switch (choice){
                    case 1:
                        // do smth
                        System.out.println("Not implemented");
                        break;
                    case 2:
                        System.out.println("Logging Out...");
                        return;
                    default:
                        // do smth
                        break;
                }
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }
}
