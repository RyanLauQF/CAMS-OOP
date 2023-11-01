package model;

public class Enquiry {
    private String query;
    private final Student createdBy;

    private String reply;

    public Enquiry(String query, Student createdBy) {
        this.query = query;
        this.createdBy = createdBy;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
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
}
