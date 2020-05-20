package entity;

public class Hospital implements WorkPlace {

    String id;
    String name;
    String city;
    String district;
    String capacity;
    Director director;
    String cotact1;
    String contact2;
    String fax;
    String email;

    public Hospital() {
    }

    public Hospital(String id, String name, String city, String district, String capacity, Director director, String cotact1, String contact2, String fax, String email) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.district = district;
        this.capacity = capacity;
        this.director = director;
        this.cotact1 = cotact1;
        this.contact2 = contact2;
        this.fax = fax;
        this.email = email;
    }

    public Hospital(String id, String name, String city, String district, String capacity, String directorName, String dirContact, String cotact1, String contact2, String fax, String email) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.district = district;
        this.capacity = capacity;
        this.director = new Director(directorName,dirContact);
        this.cotact1 = cotact1;
        this.contact2 = contact2;
        this.fax = fax;
        this.email = email;
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

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", capacity='" + capacity + '\'' +
                ", director=" + director +
                ", cotact1='" + cotact1 + '\'' +
                ", contact2='" + contact2 + '\'' +
                ", fax='" + fax + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
