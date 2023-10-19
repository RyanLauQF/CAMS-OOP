package controller;

import database.Database;
import model.Camp;

import java.util.HashMap;

public class CampManager {
    private final HashMap<Integer, Camp> campsData;
    public CampManager(){
        campsData = Database.CAMP_DATA;
    }
}
