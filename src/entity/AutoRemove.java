package entity;

import java.sql.Date;

public class AutoRemove {

    private String id;
    private Date date;

    public AutoRemove() {
    }

    public AutoRemove(String id, Date date) {
        this.id = id;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "AutoRemove{" +
                "id='" + id + '\'' +
                ", date=" + date +
                '}';
    }
}
