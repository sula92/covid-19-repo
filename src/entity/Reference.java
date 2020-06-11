package entity;

public class Reference extends People {

    private String suspected_id;
    private String reference_id;
    private String connection;

    public Reference() {
    }

    public Reference(String suspected_id, String reference_id, String connection) {
        this.suspected_id = suspected_id;
        this.reference_id = reference_id;
        this.connection = connection;
    }

    public String getSuspected_id() {
        return suspected_id;
    }

    public void setSuspected_id(String suspected_id) {
        this.suspected_id = suspected_id;
    }

    public String getReference_id() {
        return reference_id;
    }

    public void setReference_id(String reference_id) {
        this.reference_id = reference_id;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    @Override
    public String toString() {
        return "Reference{" +
                "suspected_id='" + suspected_id + '\'' +
                ", reference_id='" + reference_id + '\'' +
                ", connection='" + connection + '\'' +
                '}';
    }
}
