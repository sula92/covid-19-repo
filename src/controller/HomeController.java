package controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HomeController {
    public FontAwesomeIconView iconSys;
    public FontAwesomeIconView iconPub;
    public AnchorPane ancHome;

    public void iconSysOnClick(MouseEvent mouseEvent) throws IOException {
        System.out.println("xxx");

        URL url=this.getClass().getResource("/view/Dashboard.fxml");
        Parent parent= FXMLLoader.load(url);
        Scene scene=new Scene(parent);
        Stage stage= (Stage) this.ancHome.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin");
        stage.setFullScreen(false);
    }

    public void iconPubonClick(MouseEvent mouseEvent) throws IOException {
        URL url=this.getClass().getResource("/view/Public.fxml");
        Parent parent= FXMLLoader.load(url);
        Scene scene=new Scene(parent);
        Stage stage= (Stage) this.ancHome.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin");
        stage.setFullScreen(false);

    }

    public void onEnter(MouseEvent mouseEvent) {
    }

    public void onExit(MouseEvent mouseEvent) {
    }
}
