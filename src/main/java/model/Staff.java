package model;
import java.time.LocalDate;
import java.util.ArrayList;

public class Staff extends User {

    //attributes
    private ArrayList<Camp> campsCreated;
    
    //constructor
    public Staff(String name, String email, String faculty){
        //change faculty to UserGroup?
        super(name, email, faculty);
        this.campsCreated =  new ArrayList<Camp>();
    }

    //methods
    public void createCamp(Camp newCamp){
        //add camp into list
        campsCreated.add(newCamp);
    };

    public void editCamp(Camp camp){
    };

    public void deleteCamp(Camp camp){};

    public void toggleVisibilityCamp(Camp camp){};



    public void approveSuggestions(Camp camp, CampCommMember member){};

    public void generateStudentList(Camp camp){};

    public void generatePerformanceReport(CampCommMember member){};




}
