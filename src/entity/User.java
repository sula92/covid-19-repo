package entity;

public class User {

    private String name;
    private String contact;
    private String email;
    private String unm;
    private String pwd;
    private String role;
    private String hospitalid;
    private String qcid;

    public User() {
    }

    public User(String name, String contact, String email, String unm, String pwd, String role, String hospitalid, String qcid) {
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.unm = unm;
        this.pwd = pwd;
        this.role = role;
        this.hospitalid = hospitalid;
        this.qcid = qcid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUnm() {
        return unm;
    }

    public void setUnm(String unm) {
        this.unm = unm;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHospitalid() {
        return hospitalid;
    }

    public void setHospitalid(String hospitalid) {
        this.hospitalid = hospitalid;
    }

    public String getQcid() {
        return qcid;
    }

    public void setQcid(String qcid) {
        this.qcid = qcid;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", unm='" + unm + '\'' +
                ", pwd='" + pwd + '\'' +
                ", role='" + role + '\'' +
                ", hospitalid='" + hospitalid + '\'' +
                ", qcid='" + qcid + '\'' +
                '}';
    }
}
