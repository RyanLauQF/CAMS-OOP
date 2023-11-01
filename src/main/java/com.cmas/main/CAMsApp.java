package com.cmas.main;
import controller.CampManager;
import controller.UserManager;
import database.Database;
import helper.UserIO;
import model.CampCommMember;
import model.Staff;
import model.Student;
import model.User;
import view.AppView;

import java.util.Scanner;

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
