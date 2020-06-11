package entity;

import java.sql.Date;

public class Patient extends People {

    private String id;
    private Hospital hospital;
    private Date date;
    private String reason;

    public Patient() {
    }

    public Patient(String id, Hospital hospital, Date date, String reason) {
        this.id = id;
        this.hospital = hospital;
        this.date = date;
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id='" + id + '\'' +
                ", hospital=" + hospital +
                ", date=" + date +
                ", reason='" + reason + '\'' +
                '}';
    }
}
