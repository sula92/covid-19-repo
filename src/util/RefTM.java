package util;

import javafx.scene.control.Button;

import java.util.Date;

public class RefTM {

    private String id;
    private String name;
    private String city;
    private String district;
    private String province;
    private String nic;
    private Date date;
    private String refId;
    private Button del;

    public RefTM() {
    }

    public RefTM(String id, String name, String city, String district, String province, String nic, Date date, String refId, Button del) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.district = district;
        this.province = province;
        this.nic = nic;
        this.date = date;
        this.refId = refId;
        this.del = del;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public Button getDel() {
        return del;
    }

    public void setDel(Button del) {
        this.del = del;
    }

    @Override
    public String toString() {
        return "RefTM{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", province='" + province + '\'' +
                ", nic='" + nic + '\'' +
                ", date=" + date +
                ", refId='" + refId + '\'' +
                ", del=" + del +
                '}';
    }
}
