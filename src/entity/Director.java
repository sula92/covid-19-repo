package entity;

public class Director {

    String name;
    String contact;

    public Director() {
    }

    public Director(String name, String contact) {
        this.name = name;
        this.contact = contact;
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

    @Override
    public String toString() {
        return "Director{" +
                "name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
