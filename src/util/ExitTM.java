package util;

import java.util.Date;

public class ExitTM {

    private String personId;
    private Date exitdate;
    private String hosExId;

    public ExitTM() {
    }

    public ExitTM(String personId, Date exitdate, String hosExId) {
        this.personId = personId;
        this.exitdate = exitdate;
        this.hosExId = hosExId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Date getExitdate() {
        return exitdate;
    }

    public void setExitdate(Date exitdate) {
        this.exitdate = exitdate;
    }

    public String getHosExId() {
        return hosExId;
    }

    public void setHosExId(String hosExId) {
        this.hosExId = hosExId;
    }

    @Override
    public String toString() {
        return "ExitTM{" +
                "personId='" + personId + '\'' +
                ", exitdate=" + exitdate +
                ", hosExId='" + hosExId + '\'' +
                '}';
    }
}
