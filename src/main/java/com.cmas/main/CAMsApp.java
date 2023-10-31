package com.cmas.main;
import controller.CampManager;
import controller.UserManager;
import database.Database;
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
        // TODO: load db etc.
        Database db = new Database();

        // db.load();

        AppView.renderView();

        // TODO: write updated data to db
    }

}
