package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    public AnchorPane ancLogin;
    public JFXTextField uname;
    public JFXTextField pswd;
    public Button btnLogin;

    public void btnLoginOnAction(ActionEvent event) {

        System.out.println("xxxs");

        try {
            PreparedStatement pstm= DBConnection.getInstance().getConnection().prepareStatement("select unm,pwd,role from user where unm=? AND pwd=?");
            pstm.setString(1,uname.getText());
            pstm.setString(2,pswd.getText());
            ResultSet rst=pstm.executeQuery();
            if (rst.next()){
                String u=rst.getString(1);
                String p=rst.getString(2);
                String r=rst.getString(3);

                System.out.println(u+p+r);

                login(r);
            }
            else {
                new Alert(Alert.AlertType.ERROR,"Wrong User Name or Password").show();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }


    }

    private void login(String role) throws IOException {

        System.out.println(role);

        Parent root = null;

        FXMLLoader fxmlLoader = null;

        switch (role) {
            case "admin":
                URL url=this.getClass().getResource("/view/Dashboard.fxml");
                Parent parent= FXMLLoader.load(url);
                Scene scene=new Scene(parent);
                Stage stage= (Stage) this.ancLogin.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Admin");
                stage.centerOnScreen();
                stage.show();
                break;

            case "Hospital-IT":
                URL url2=this.getClass().getResource("/view/Patient.fxml");
                Parent parent2= FXMLLoader.load(url2);
                Scene scene2=new Scene(parent2);
                Stage stage2= (Stage) this.ancLogin.getScene().getWindow();
                stage2.setScene(scene2);
                stage2.setTitle("Hospital-IT");
                stage2.centerOnScreen();
                stage2.show();
                break;
            case "Quarantine_Center_IT":
                URL url3=this.getClass().getResource("/view/Quarantined.fxml");
                Parent parent3= FXMLLoader.load(url3);
                Scene scene3=new Scene(parent3);
                Stage stage3= (Stage) this.ancLogin.getScene().getWindow();
                stage3.setScene(scene3);
                stage3.setTitle("Quarantine_Center-IT");
                stage3.centerOnScreen();
                stage3.show();
                break;
            case "PSTF":
                URL url4=this.getClass().getResource("/view/Dashboard.fxml");
                Parent parent4= FXMLLoader.load(url4);
                Scene scene4=new Scene(parent4);
                Stage stage4= (Stage) this.ancLogin.getScene().getWindow();
                stage4.setScene(scene4);
                stage4.setTitle("PSTF");
                stage4.centerOnScreen();
                stage4.show();
                break;

        }

    }
}
