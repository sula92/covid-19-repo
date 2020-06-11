package util;

import javafx.scene.control.Button;

import java.util.Date;

public class QuarantinedTM {

    private String id;
    private String name;
    private String city;
    private String district;
    private String center;
    private String province;
    private String nic;
    private Date date;
    private Button ref;
    private Button del;

    public QuarantinedTM() {
    }

    public QuarantinedTM(String id, String name, String city, String district, String center, String province, String nic, Date date, Button ref, Button del) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.district = district;
        this.center = center;
        this.province = province;
        this.nic = nic;
        this.date = date;
        this.ref = ref;
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

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
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

    public Button getRef() {
        return ref;
    }

    public void setRef(Button ref) {
        this.ref = ref;
    }

    public Button getDel() {
        return del;
    }

    public void setDel(Button del) {
        this.del = del;
    }

    @Override
    public String toString() {
        return "QuarantinedTM{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", center='" + center + '\'' +
                ", province='" + province + '\'' +
                ", nic='" + nic + '\'' +
                ", date=" + date +
                ", ref=" + ref +
                ", del=" + del +
                '}';
    }
}
