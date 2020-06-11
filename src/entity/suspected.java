package entity;

import java.sql.Date;

public class suspected extends People {

    private String id;
    private String reason;
    private Date date;

    public suspected() {
    }

    public suspected(String id, String reason, Date date) {
        this.id = id;
        this.reason = reason;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "suspected{" +
                "id='" + id + '\'' +
                ", reason='" + reason + '\'' +
                ", date=" + date +
                '}';
    }
}
