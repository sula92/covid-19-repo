package util;


import javafx.scene.control.Button;

public class UserTM {

    private String unm;
    private String nm;
    private String role;
    private Button button;

    public UserTM() {
    }

    public UserTM(String unm, String nm, String role, Button button) {
        this.unm = unm;
        this.nm = nm;
        this.role = role;
        this.button = button;
    }

    public String getUnm() {
        return unm;
    }

    public void setUnm(String unm) {
        this.unm = unm;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "UserTM{" +
                "unm='" + unm + '\'' +
                ", nm='" + nm + '\'' +
                ", role='" + role + '\'' +
                ", button=" + button +
                '}';
    }
}
