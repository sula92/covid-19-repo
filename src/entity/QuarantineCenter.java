package entity;

public class QuarantineCenter implements WorkPlace{

    String id;
    String name;
    String city;
    String district;
    String capacity;
    Head head;
    String cotact1;
    String contact2;

    public QuarantineCenter() {
    }

    public QuarantineCenter(String id, String name, String city, String district, String capacity, Head head, String cotact1, String contact2) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.district = district;
        this.capacity = capacity;
        this.head = head;
        this.cotact1 = cotact1;
        this.contact2 = contact2;

    }

    public QuarantineCenter(String id, String name, String city, String district, String capacity, String hName, String hContact, String cotact1, String contact2) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.district = district;
        this.capacity = capacity;
        this.head = new Head(hName,hContact);
        this.cotact1 = cotact1;
        this.contact2 = contact2;

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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public Head gethead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public String getCotact1() {
        return cotact1;
    }

    public void setCotact1(String cotact1) {
        this.cotact1 = cotact1;
    }

    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }


    @Override
    public String toString() {
        return "Hospital{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", capacity='" + capacity + '\'' +
                ", director=" + head +
                ", cotact1='" + cotact1 + '\'' +
                ", contact2='" + contact2 + '\'' +
                '}';
    }

}
