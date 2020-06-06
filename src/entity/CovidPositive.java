package entity;

import java.sql.Date;

public class CovidPositive {

    private String id;
    private Date date;
    private String found;

    public CovidPositive() {
    }

    public CovidPositive(String id, Date date, String found) {
        this.id = id;
        this.date = date;
        this.found = found;
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

    public String getFound() {
        return found;
    }

    public void setFound(String found) {
        this.found = found;
    }

    @Override
    public String toString() {
        return "CovidPositive{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", found='" + found + '\'' +
                '}';
    }
}
