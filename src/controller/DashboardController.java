package controller;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class DashboardController {
    public AnchorPane dashboard;
    public ImageView imgViewGlobalCovid;
    public ImageView imgViewHospital;
    public ImageView imgViewQuarantine;
    public ImageView imgViewUser;
    public Label lblDescription;
    public ImageView imgViewPatient;
    public ImageView imgViewQuarantined;
    public ImageView imgViewRefs;
    public ImageView imgViewLocalCovid;

    public void initialize(){
        DropShadow glow = new DropShadow();
        glow.setColor(Color.BLUE);
        glow.setWidth(20.0D);
        glow.setHeight(20.0D);
        glow.setRadius(40.0D);
        imgViewGlobalCovid.setEffect(glow);
        imgViewHospital.setEffect(glow);
        imgViewQuarantine.setEffect(glow);
        imgViewUser.setEffect(glow);
    }

    public void imgGlobalClick(MouseEvent mouseEvent) {
    }

    public void imgHospitakClick(MouseEvent mouseEvent) {
    }

    public void imgQuarantineClick(MouseEvent mouseEvent) {
    }

    public void imgUserClick(MouseEvent mouseEvent) {
    }

    @FXML
    private void navigate(MouseEvent event) throws IOException {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            Parent root = null;

            FXMLLoader fxmlLoader = null;
            switch (icon.getId()) {
                case "imgViewGlobalCovid":
                    root = FXMLLoader.load(this.getClass().getResource("/view/GlobalCovid.fxml"));
                    break;
                case "imgViewHospital":
                    root = FXMLLoader.load(this.getClass().getResource("/view/ManageHospital.fxml"));
                    break;
                case "imgViewQuarantine":
                    root = FXMLLoader.load(this.getClass().getResource("/view/ManageQuarantineCenters.fxml"));
                    break;

                case "imgViewPatient":
                    root = FXMLLoader.load(this.getClass().getResource("/view/Patient.fxml"));
                    break;
                case "imgViewQuarantined":
                    root = FXMLLoader.load(this.getClass().getResource("/view/Quarantined.fxml"));
                    break;
                case "imgViewRefs":
                    root = FXMLLoader.load(this.getClass().getResource("/view/Refence.fxml"));
                    break;
                case "imgViewLocalCovid":
                    root = FXMLLoader.load(this.getClass().getResource("/view/LocalCovid.fxml"));
                    break;
                case "imgViewUser":

                    Stage stage= (Stage) dashboard.getScene().getWindow();
                    String title=stage.getTitle();
                    if(!title.equalsIgnoreCase("Admin")){
                        new Alert(Alert.AlertType.INFORMATION,"Sorry,You Don't Have the Admin Privilege!").show();
                        return;
                    }

                    fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/ManageUsers.fxml"));
                    root = fxmlLoader.load();
                    break;

            }

            if (root != null) {
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.dashboard.getScene().getWindow();

                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();

            }
        }
    }

    public void img_mouseEnterence(MouseEvent mouseEvent) {
        ImageView icon = (ImageView)mouseEvent.getSource();
        ScaleTransition scaleT = new ScaleTransition(Duration.millis(200.0D), icon);
        scaleT.setToX(1.2D);
        scaleT.setToY(1.2D);
        scaleT.play();
        DropShadow glow = new DropShadow();
        glow.setColor(Color.RED);
        glow.setWidth(20.0D);
        glow.setHeight(20.0D);
        glow.setRadius(40.0D);
        icon.setEffect(glow);

        String cat =icon.getId();
        switch (cat){
            case "imgViewGlobalCovid":

                this.lblDescription.setText("Click to Manage Global Covid Data");
                break;

            case "imgViewHospital":

                this.lblDescription.setText("Click to Manage Hospital");
                break;

            case "imgViewQuarantine":

                this.lblDescription.setText("Click to Manage Quarantine Centers");
                break;

            case "imgViewUser":

                this.lblDescription.setText("Click to Manage Users");
                break;

            case "imgViewPatient":

                this.lblDescription.setText("Click to Manage Patients");
                break;

        }

    }

    public void img_mouseExit(MouseEvent mouseEvent) {
        ImageView icon = (ImageView) mouseEvent.getSource();
        ScaleTransition scaleT = new ScaleTransition(Duration.millis(200.0D),icon);
        scaleT.setToX(1D);
        scaleT.setToY(1D);
        scaleT.play();
        icon.setEffect(null);
        DropShadow glow = new DropShadow();
        glow.setColor(Color.BLUE);
        glow.setWidth(20.0D);
        glow.setHeight(20.0D);
        glow.setRadius(40.0D);
        icon.setEffect(glow);
       /* lblMenu.setText("");
        lblDescription.setText("");*/
        lblDescription.setText("");

    }

}
