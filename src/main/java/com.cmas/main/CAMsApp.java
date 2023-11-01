package com.cmas.main;
import database.Database;
import helper.UserIO;
import view.AppView;

public class CAMsApp {

    /**
     * Main function to run Camp Application and Management System (CAMs)
     */
    public static void main(String[] args){
        Database db = new Database(); // LOAD DATABASE
        AppView.renderView();
        db.saveToDatabase(); // WRITE ALL CHANGES TO DATABASE
        UserIO.close(); // CLOSES SCANNER
    }
}
