package controller;

import db.DBConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

public class LocalCOVIDController implements Initializable {
    public Label labDate;
    public Label labCon;
    public Label labRec;
    public Label labDeath;

    public AnchorPane ancGlobal;
    public PieChart pie;
    public FontAwesomeIconView iconHome;
    public BarChart bar;
    public Pane paneView;
    public Button btnReset;

    public void btnUpdateOnAction(ActionEvent event) {





    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



         loadAllData();


    }


    public void loadAllData(){
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT COUNT(*) FROM exit_data where exit_reason='DEAD'");

            Statement stm1 = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst1 = stm1.executeQuery("SELECT COUNT(*) FROM exit_data where exit_reason='DISCHARGED'");

            Statement stm2 = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst2 = stm2.executeQuery("SELECT COUNT(id) FROM covid_positive");

            rst.next();
            rst1.next();
            rst2.next();

            int deathCount=rst.getInt(1);
            int recoveredCount=rst1.getInt(1);
            int confirmedCount=rst2.getInt(1);

            LocalDate date=LocalDate.now();

                labDate.setText(date.toString());
                labCon.setText(String.valueOf(confirmedCount));
                labRec.setText(String.valueOf(recoveredCount));
                labDeath.setText(String.valueOf(deathCount));


            setGraphs();



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dateDateoOnAction(ActionEvent event) {



    }


    public void iconHomeOnClicked(MouseEvent mouseEvent) throws IOException {

        //Navigating Based on Access privileges

        Stage stage1 = (Stage) this.ancGlobal.getScene().getWindow();
        String title=stage1.getTitle();
        // System.out.println(title);


        if (title.equalsIgnoreCase("admin")){
            URL url=this.getClass().getResource("/view/Dashboard.fxml");
            Parent parent= FXMLLoader.load(url);
            Scene scene=new Scene(parent);
            Stage stage= (Stage) this.ancGlobal.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Admin");
            stage.setFullScreen(false);

        }else if(title.equalsIgnoreCase("PSTF")) {
            URL url=this.getClass().getResource("/view/Dashboard.fxml");
            Parent parent= FXMLLoader.load(url);
            Scene scene=new Scene(parent);
            Stage stage= (Stage) this.ancGlobal.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("PSTF");
            stage.setFullScreen(false);

        }
        else{
            URL url=this.getClass().getResource("/view/Login.fxml");
            Parent parent= FXMLLoader.load(url);
            Scene scene=new Scene(parent);
            Stage stage= (Stage) this.ancGlobal.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("IT-Person");
            stage.setFullScreen(false);

        }
    }



    private void setGraphs(){
        int conf= Integer.parseInt(labCon.getText());
        int reco= Integer.parseInt(labRec.getText());
        int deaths= Integer.parseInt(labDeath.getText());
        //int tot= Integer.parseInt(SelectedItem.getTot());

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Total Confirmed",conf ),
                        new PieChart.Data("Total Recovered", reco),
                        new PieChart.Data("Total Death", deaths));

        pie.setData(pieChartData);
        pie.setLegendSide(Side.TOP);
        //pie.setTitle("The Pie Chart");
        pie.setClockwise(false);


//........................................................................................................................

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>
                observableArrayList(Arrays.asList("Confirmed", "Recovered", "Deaths")));
        xAxis.setLabel("category");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Count");

        //Creating the Bar chart
        bar = new BarChart<>(xAxis, yAxis);
        bar.setTitle("The Bar Chart");

        //Prepare XYChart.Series objects by setting data
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName(labDate.getText());
        series1.getData().add(new XYChart.Data<>("Confirmed", conf));
        series1.getData().add(new XYChart.Data<>("Recovered", reco));
        series1.getData().add(new XYChart.Data<>("Deaths", deaths));



        //Setting the data to bar chart
        bar.getData().addAll(series1);
        paneView.getChildren().add(bar);
    }


    public void btnResetOnAction(ActionEvent event) {


        loadAllData();
    }
}
