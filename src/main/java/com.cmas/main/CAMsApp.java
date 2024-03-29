package com.cmas.main;
import com.cmas.helper.UserIO;
import com.cmas.database.Database;
import com.cmas.view.AppView;

import javax.xml.crypto.Data;


/**
 * The main class for the Camp Application and Management System (CAMS).
 * This class initializes the database and renders the main application view upon run.
 * When user logs out, changes are saved to the database to maintain persistent data integrity.
 * Additionally, input scanner for handling user IO is closed.
 *
 * @author Ryan Lau
 * @version 1.0
 * @since 2023-11-14
 */
public class CAMsApp {

    /**
     * Main function to run Camp Application and Management System (CAMS)
     */
    public static void main(String[] args){
        Database db = Database.getInstance(); // LOAD DATABASE
        AppView.renderView();
        db.saveToDatabase(); // WRITE ALL CHANGES TO DATABASE
        UserIO.close(); // CLOSES SCANNER
    }
}
