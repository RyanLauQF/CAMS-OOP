package com.cmas.model;

import com.cmas.controller.EnquiryManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;


/**
 * The Enquiry class represents a user's query within a camp. It includes information about the query,
 * the user who created it (a Student), the associated camp ID, and details about the processing and reply.
 * The class also provides methods for printing enquiry details and generating an enquiry report for a camp.
 *
 * @author Tong Ying, Markus Lim, Shao Chong
 * @version 1.0
 * @since 2023-11-18
 */
public class Enquiry implements Serializable {
    private String query;
    private final Student createdBy;
    private final UUID campID;

    private boolean isProcessed;

    private String reply;
    private User repliedBy;

    /**
     * Constructs an Enquiry object with the specified query, creator (Student), and camp ID.
     *
     * @param query     The user's query.
     * @param createdBy The Student who created the enquiry.
     * @param campID    The UUID of the camp associated with the enquiry.
     */
    public Enquiry(String query, Student createdBy, UUID campID) {
        this.query = query;
        this.createdBy = createdBy;
        this.campID = campID;
        this.reply = "";
        this.isProcessed = false;
    }

    /**
     * Prints the details of the enquiry, including the query, creator, and reply (if processed).
     */
    public void printEnquiryDetails() {
        System.out.println("Query: " + query);
        System.out.println("Created by: " + createdBy.getName());
        if (isProcessed) {
            System.out.println("Reply: " + reply);
            System.out.println("Replied By: " + repliedBy.getName());

        }
    }

    /**
     * Generates an enquiry report for a camp based on the specified choice.
     *
     * @param camp   The camp for which the report is generated.
     * @param choice The choice specifying the information to include in the report.
     *              - Choice 1: Include all details (query, reply, creator, and replier).
     *              - Choice 2: Include only the query and creator details.
     *              - Choice 3: Include only processed enquiries with all details.
     */
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
            String x = "EnquiryReport.txt";
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

    /**
     * Gets the user's query.
     *
     * @return The user's query.
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets the user's query.
     *
     * @param query The user's query to be set.
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * Gets the UUID of the camp associated with the enquiry.
     *
     * @return The UUID of the camp.
     */
    public UUID getCampID() {
        return campID;
    }

    /**
     * Gets the Student who created the enquiry.
     *
     * @return The Student who created the enquiry.
     */
    public Student getCreatedBy() {
        return createdBy;
    }

    /**
     * Gets the reply to the enquiry.
     *
     * @return The reply to the enquiry.
     */
    public String getReply() {
        return reply;
    }

    /**
     * Sets the reply to the enquiry.
     *
     * @param reply The reply to be set for the enquiry.
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

    /**
     * Sets the processing status of the enquiry.
     *
     * @param isProcessed True if the enquiry has been processed; otherwise, false.
     */
    public void setIsProcessed(boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

    /**
     * Checks if the enquiry has been processed.
     *
     * @return True if the enquiry has been processed; otherwise, false.
     */
    public boolean getIsProcessed() {
        return isProcessed;
    }

    /**
     * Gets the user who replied to the enquiry.
     *
     * @return The user who replied to the enquiry.
     */
    public User getRepliedBy() {
        return repliedBy;
    }

    /**
     * Sets the user who replied to the enquiry.
     *
     * @param repliedBy The user who replied to the enquiry.
     */
    public void setRepliedBy(User repliedBy) {
        this.repliedBy = repliedBy;
    }
}
