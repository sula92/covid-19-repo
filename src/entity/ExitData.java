package entity;

import java.sql.Date;

public class ExitData {

    private String id;
    private String exit_reason;
    private String from;
    private String to;
    private String location;
    private Date date;

    public ExitData() {
    }

    public ExitData(String id, String exit_reason, String from, String to, String location, Date date) {
        this.id = id;
        this.exit_reason = exit_reason;
        this.from = from;
        this.to = to;
        this.location = location;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExit_reason() {
        return exit_reason;
    }

    public void setExit_reason(String exit_reason) {
        this.exit_reason = exit_reason;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ExitData{" +
                "id='" + id + '\'' +
                ", exit_reason='" + exit_reason + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", location='" + location + '\'' +
                ", date=" + date +
                '}';
    }
}
