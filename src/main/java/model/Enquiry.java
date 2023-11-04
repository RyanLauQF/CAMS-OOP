package model;

import java.io.Serializable;
import java.util.UUID;

public class Enquiry implements Serializable {
    private String query;
    private final Student createdBy;
    private final UUID campID;

    private boolean isProcessed;

    private String reply;

    public Enquiry(String query, Student createdBy, UUID campID) {
        this.query = query;
        this.createdBy = createdBy;
        this.campID = campID;
        this.reply = "";
        this.isProcessed = false;

    }

    public void printEnquiryDetails(){
        System.out.println("Query: " + query);
        System.out.println("Created by: " + createdBy.getName());
        if (isProcessed == true){
            System.out.println("Reply: " + reply);
        }
    }
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public UUID getCampID() {
        return campID;
    }

    public Student getCreatedBy() {
        return createdBy;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public void setIsProcessed(boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

    public boolean getIsProcessed(){
        return isProcessed;
    }
}
