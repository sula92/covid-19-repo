package controller;

import db.DBConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entity.GlobalData;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class GlobalCOVIDController implements Initializable {
    public Label labDate;
    public Label labCon;
    public Label labRec;
    public Label labDeath;
    public DatePicker dateDate;
    public TextField txtCon;
    public TextField txtRec;
    public TextField txtDeath;
    public Button btnUpdate;
    public AnchorPane ancGlobal;
    public PieChart pie;
    public FontAwesomeIconView iconHome;
    public BarChart bar;
    public Pane paneView;
    public Button btnReset;

    public void btnUpdateOnAction(ActionEvent event) {

        if(txtCon.getText().isEmpty()||txtDeath.getText().isEmpty()||txtRec.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Make Sure To Fill All The Fields").show();
            return;
        }

        try {

            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM globaldata where date_of_updated=?");
            stm.setDate(1, Date.valueOf(dateDate.getValue()));
            ResultSet rst = stm.executeQuery();
            //System.out.println(rst.next()); when you use rst.next() again and again it always changes its boolean value.
            boolean b=rst.next();
            if(b){
                System.out.println("update");
                PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE globaldata SET confirmed=?, recovered=?, death=? WHERE date_of_updated=?");
                pstm.setObject(1, txtCon.getText());
                pstm.setObject(2, txtRec.getText());
                pstm.setObject(3, txtDeath.getText());
                pstm.setObject(4, Date.valueOf(dateDate.getValue()));
                if (pstm.executeUpdate() == 0) {
                    new Alert(Alert.AlertType.ERROR, "Failed to update the Record").show();
                }
                else {
                    new Alert(Alert.AlertType.INFORMATION, "Updated Successfully!").show();
                }

            }
            else {

                System.out.println("insert");
                PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO globaldata VALUES (?,?,?,?)");
                pstm.setObject(1, Date.valueOf(dateDate.getValue()));
                pstm.setObject(2, txtCon.getText());
                pstm.setObject(3, txtRec.getText());
                pstm.setObject(4, txtDeath.getText());
                if (pstm.executeUpdate() == 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Failed to save the Record", ButtonType.OK).show();
                }
                else {

                    new Alert(Alert.AlertType.ERROR, "Record Added Successfully", ButtonType.OK).show();
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadAllData();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnUpdate.setText("Update Record");

      loadAllData();

        dateDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                System.out.println("xxxxxx");
                LocalDate localDate =newValue;
                try {
                    PreparedStatement pst=DBConnection.getInstance().getConnection().prepareStatement("select * from globaldata where date_of_updated=?");
                    pst.setString(1, String.valueOf(newValue));
                    ResultSet r=pst.executeQuery();
                    if(r.next()){
                        labDate.setText(newValue.toString());
                        labCon.setText(r.getString(2));
                        labRec.setText(r.getString(3));
                        labDeath.setText(r.getString(4));

                        txtCon.setText(r.getString(2));
                        txtRec.setText(r.getString(3));
                        txtDeath.setText(r.getString(4));

                        setGraphs();

                        btnUpdate.setText("Update Record");
                    }
                    else {
                        btnUpdate.setText("Save Record");

                        txtCon.setText("");
                        txtDeath.setText("");
                        txtRec.setText("");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });




    }


    public void loadAllData(){
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM globaldata");
            rst.last();
                Date date = rst.getDate(1);
                String con = rst.getString(2);
                String rec = rst.getString(3);
                String death = rst.getString(4);

                labDate.setText(date.toString());
                labCon.setText(con);
                labRec.setText(rec);
                labDeath.setText(death);

                txtCon.setText(rst.getString(2));
                txtRec.setText(rst.getString(3));
                txtDeath.setText(rst.getString(4));
                dateDate.valueProperty().setValue(date.toLocalDate());

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
        txtRec.setText("");
        txtDeath.setText("");
        txtCon.setText("");

        loadAllData();
    }
}
