package entity;

import java.sql.Date;

public class GlobalData {

    Date date;
    String confirmed;
    String recovered;
    String death;

    public GlobalData() {
    }

    public GlobalData(Date date, String confirmed, String recovered, String death) {
        this.date = date;
        this.confirmed = confirmed;
        this.recovered = recovered;
        this.death = death;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    @Override
    public String toString() {
        return "GlobalData{" +
                "date=" + date +
                ", confirmed='" + confirmed + '\'' +
                ", recovered='" + recovered + '\'' +
                ", death='" + death + '\'' +
                '}';
    }
}
