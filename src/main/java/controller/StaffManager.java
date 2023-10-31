package controller;

import model.*;
import view.*;

public class StaffManager {
    private Staff staff;
    private StaffView staffView;

    //constructor
    public StaffManager(Staff staff, StaffView staffView){
        this.staff = staff;
        this.staffView = staffView;
    }

    public void createCamp(Camp newCamp){
        staff.createCamp(newCamp);
    }

    //control view object
    public Camp getCampDetails(){
        return staffView.getCampDetails(staff);
    }

}
