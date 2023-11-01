package model;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Staff extends User {

    //attributes
    private final Set<UUID> campIDs;
    
    //constructor
    public Staff(String name, String email, UserGroup faculty){
        //change faculty to UserGroup?
        super(name, email, faculty);
        this.campIDs = new HashSet<>();
    }

    public boolean hasCreatedCamp(UUID campID){
        return campIDs.contains(campID);
    }
    public void registerNewCamp(UUID campID) {
        campIDs.add(campID);
    }
    public Set<UUID> getCampIDs() {
        return campIDs;
    }
}
