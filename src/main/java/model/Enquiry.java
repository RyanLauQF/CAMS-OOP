package model;

import controller.EnquiryManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

public class Enquiry implements Serializable {
    private String query;
    private final Student createdBy;
    private final UUID campID;

    private boolean isProcessed;

    private String reply;
    private User repliedBy;

    public Enquiry(String query, Student createdBy, UUID campID) {
        this.query = query;
        this.createdBy = createdBy;
        this.campID = campID;
        this.reply = "";
        this.isProcessed = false;
    }

    public void printEnquiryDetails() {
        System.out.println("Query: " + query);
        System.out.println("Created by: " + createdBy.getName());
        if (isProcessed == true) {
            System.out.println("Reply: " + reply);
            System.out.println("Replied By: " + repliedBy.getName());

        }
    }

    public static void generateEnquiryReport(Camp camp, int choice) {
        Set<UUID> enquiryKeys = camp.getEnquiryID();
        if (enquiryKeys.isEmpty()) {
            System.out.println("No Enquiries to Print");
            System.out.println("Cancelling report");
            return;
        }
        BufferedWriter writer = null;
        try {
            int count = 0;
            String x = "EnquiryReportForCamp" + camp.getName() + ".txt";
            writer = new BufferedWriter(new FileWriter(x));
            writer.write("============================================== " + "\n");
            writer.write("    Enquiry Report for Camp " + camp.getName() + "    \n");
            writer.write("============================================== " + "\n");
            writer.write("Camp Name: " + camp.getName() + "\n");
            writer.write("Camp Description: " + camp.getDescription() + "\n");
            writer.write("============================================== " + "\n");
            for (UUID id : enquiryKeys) {
                count++;
                Enquiry curEnquiry = EnquiryManager.getEnquiry(id);
                if (choice == 1) {
                    writer.write("Enquiry " + count + ": " + "\n");
                    writer.write("  Made by: " + curEnquiry.getCreatedBy().getName() + "\n");
                    writer.write("  Query is: " + curEnquiry.getQuery() + "\n");
                    if (curEnquiry.getIsProcessed()) {
                        writer.write("  Reply is: " + curEnquiry.getReply() + "\n");
                        writer.write("  Replied by: " + curEnquiry.getRepliedBy() + "\n");
                    }
                } else if (choice == 2) {
                    writer.write("Enquiry " + count + ": " + "\n");
                    writer.write("  Made by: " + curEnquiry.getCreatedBy().getName() + "\n");
                    writer.write("  Query is: " + curEnquiry.getQuery() + "\n");
                } else if (choice == 3) {
                    if (curEnquiry.getIsProcessed()) {
                        writer.write("Enquiry " + count + ": ");
                        writer.write("  Made by: " + curEnquiry.getCreatedBy().getName() + "\n");
                        writer.write("  Query is: " + curEnquiry.getQuery() + "\n");
                        writer.write("  Reply is: " + curEnquiry.getReply() + "\n");
                        writer.write("  Replied by: " + curEnquiry.getRepliedBy() + "\n");
                    }
                }
                writer.write("----------------------------------------------\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
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

    public boolean getIsProcessed() {
        return isProcessed;
    }

    public User getRepliedBy() {
        return repliedBy;
    }

    public void setRepliedBy(User repliedBy) {
        this.repliedBy = repliedBy;
    }
}
