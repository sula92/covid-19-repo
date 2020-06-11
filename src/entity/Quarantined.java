package entity;

import java.sql.Date;

public class Quarantined extends People {

    private String id;
    private String reason;
    private Date entered_date;
    private QuarantineCenter qc;

    public Quarantined() {
    }

    public Quarantined(String id, String reason, Date enterd_date, QuarantineCenter qc) {
        this.id = id;
        this.reason = reason;
        this.entered_date = enterd_date;
        this.qc = qc;
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

    public Date getEnterd_date() {
        return entered_date;
    }

    public void setEnterd_date(Date enterd_date) {
        this.entered_date = enterd_date;
    }

    public QuarantineCenter getQc() {
        return qc;
    }

    public void setQc(QuarantineCenter qc) {
        this.qc = qc;
    }

    @Override
    public String toString() {
        return "Quarantined{" +
                "id='" + id + '\'' +
                ", reason='" + reason + '\'' +
                ", enterd_date='" + entered_date + '\'' +
                ", qc=" + qc +
                '}';
    }
}
