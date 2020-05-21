package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entity.Hospital;
import entity.QuarantineCenter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageQuarantineCentersController implements Initializable {
    public ListView<String> listHos;
    public TextField txtSer;
    public Button btnAdd;
    public JFXTextField txtId;
    public JFXTextField txtNm;
    public JFXTextField txtCity;
    public JFXComboBox txtDis;
    public JFXTextField txtCapa;
    public JFXTextField txtDire;
    public JFXTextField txtDireCont;
    public JFXTextField txtHosCont1;
    public JFXTextField txtHosCont2;

    public Button btnSave;
    public Button btnDel;
    public FontAwesomeIconView iconHome;
    public AnchorPane ancQc;
    ObservableList<String> names = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnAdd.setText("ADD NEW CENTER");

        loadQc();

        txtSer.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("www");
                listHos.getItems().clear();
                ObservableList<String> serchOrderTMS=FXCollections.observableList(listHos.getItems());

                names.stream().forEach(serchOrderTM -> {
                    System.out.println(serchOrderTM);
                    if ((serchOrderTM.contains(newValue))){
                        System.out.println(serchOrderTM);

                        serchOrderTMS.add(serchOrderTM);
                        //listHos.getItems().add(serchOrderTM);
                        listHos.refresh();
                    }

                });
            }
        });

        String districtsText = " Colombo\n" +
                " Gampaha\n" +
                " Kalutara\n" +
                " Kandy\n" +
                " Matale\n" +
                " Nuwara Eliya\n" +
                " Galle\n" +
                " Matara\n" +
                " Hambantota\n" +
                " Jaffna\n" +
                " Mannar\n" +
                " Vauniya\n" +
                " Mullativue\n" +
                " Ampara\n" +
                " Trincomalee\n" +
                " Batticaloa\n" +
                " Kilinochchi\n" +
                " Kurunegala\n" +
                " Puttalam\n" +
                " Anuradhapura\n" +
                " Polonnaruwa\n" +
                " Badulla\n" +
                " Moneragala\n" +
                " Ratnapura\n" +
                " Kegalle";
        String[] districts = districtsText.split("\n");
        ObservableList<String> olDistricts = FXCollections.observableArrayList(Arrays.asList(districts));
        txtDis.setItems(olDistricts);

        //...............................................................................


        listHos.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {

                btnSave.setText("Update");
                btnDel.setDisable(false);

                String hosnm=listHos.getSelectionModel().getSelectedItem();

                QuarantineCenter qc=getHospital(hosnm);
                txtId.setText(qc.getId());
                txtNm.setText(qc.getName());
                txtDis.setValue(qc.getCity());
                txtCapa.setText(qc.getCapacity());
                txtDire.setText(qc.gethead().getName());
                txtDireCont.setText(qc.gethead().getContact());
                txtHosCont1.setText(qc.getCotact1());
                txtHosCont2.setText(qc.getContact2());

            }
        });


    }

    public void btnAddOnAc(ActionEvent event) {

        btnDel.setDisable(true);


        try {
            PreparedStatement pstm= DBConnection.getInstance().getConnection().prepareStatement("select id From qc");
            ResultSet rst=pstm.executeQuery();
            rst.last();
            String id=rst.getString(1).replace("Q","");
            int incId=Integer.parseInt(id)+1;
            String newId;

            if(incId<10){
                newId="Q"+"00"+incId;
                txtId.setText(newId);
                return;
            }
            else if(incId<100 && incId>10){
                newId="Q"+"0"+incId;
                txtId.setText(newId);
                return;
            }
            else {
                newId="Q"+incId;
                txtId.setText(newId);
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        reset();

    }

    public void btnSaveOnAc(ActionEvent event) {

        if(txtDireCont.getText().isEmpty()||txtDire.getText().isEmpty()||txtCity.getText().isEmpty()||txtId.getText().isEmpty()||txtNm.getText().isEmpty()||txtHosCont1.getText().isEmpty()||txtHosCont2.getText().isEmpty()||txtCapa.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Make Sure To Fill All The Fields").show();
            return;
        }

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);



        if (!Pattern.matches("[0-9]{10}", txtHosCont1.getText())){
            new Alert(Alert.AlertType.ERROR,"Wrong Center Contact Number-1").show();
            return;
        }
        if (!Pattern.matches("[0-9]{10}", txtHosCont2.getText())){
            new Alert(Alert.AlertType.ERROR,"Wrong Center Contact Number-2").show();
            return;
        }
        if (!Pattern.matches("[0-9]{10}", txtDireCont.getText())){
            new Alert(Alert.AlertType.ERROR,"Wrong Head's Contact Number").show();
            return;
        }

        if(btnSave.getText().equalsIgnoreCase("save")){
            try {
                PreparedStatement pstm=DBConnection.getInstance().getConnection().prepareStatement("insert into qc values(?,?,?,?,?,?,?,?,?)");
                pstm.setString(1,txtId.getText());
                pstm.setString(2,txtNm.getText());
                pstm.setString(3,txtCity.getText());
                pstm.setString(4,txtDis.getValue().toString());
                pstm.setString(5,txtCapa.getText());
                pstm.setString(6,txtDire.getText());
                pstm.setString(7,txtDireCont.getText());
                pstm.setString(8,txtHosCont1.getText());
                pstm.setString(9,txtHosCont2.getText());

                int i=pstm.executeUpdate();

                if(i<=0){
                    new Alert(Alert.AlertType.ERROR,"Record Can Not be Added!").show();
                }
                else {
                    new Alert(Alert.AlertType.INFORMATION,"Record Added Successfully").show();
                    loadQc();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {

            try {
                PreparedStatement pstm=DBConnection.getInstance().getConnection().prepareStatement("update qc set `name`=?,city=?,district=?,capacity=?,head=?,head_contact_co=?,center_contact1=?,center_contact2=? where id=?");
                pstm.setString(1,txtNm.getText());
                pstm.setString(2,txtCity.getText());
                pstm.setString(3,txtDis.getValue().toString());
                pstm.setString(4,txtCapa.getText());
                pstm.setString(5,txtDire.getText());
                pstm.setString(6,txtDireCont.getText());
                pstm.setString(7,txtHosCont1.getText());
                pstm.setString(8,txtHosCont2.getText());
                pstm.setString(9,txtId.getText());
                int i=pstm.executeUpdate();
                if (i<=0){
                    new Alert(Alert.AlertType.ERROR,"Failed to Update").show();
                }
                else {
                    new Alert(Alert.AlertType.INFORMATION,"Updated Successfully").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        reset();
    }

    public void btnDelOnAc(ActionEvent event) {

        try {
            PreparedStatement pstm=DBConnection.getInstance().getConnection().prepareStatement("delete * FROM qc where id=?");
            pstm.setString(1,txtId.getText());
            int i=pstm.executeUpdate();
            if (i<=0){
                new Alert(Alert.AlertType.ERROR,"Failed To Delete!").show();
            }
            else {
                new Alert(Alert.AlertType.INFORMATION,"Record Deleted Successfully").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private QuarantineCenter getHospital(String nm){
        try {
            PreparedStatement pstm= DBConnection.getInstance().getConnection().prepareStatement("select * From qc where `name`=?");
            pstm.setString(1,nm);
            ResultSet rst=pstm.executeQuery();
            rst.next();
            String id=rst.getString(1);
            String name=rst.getString(2);
            String city=rst.getString(3);
            String district=rst.getString(4);
            String capacity=rst.getString(5);
            String director=rst.getString(6);
            String dicon=rst.getString(7);
            String hoscon1=rst.getString(8);
            String hoscon2=rst.getString(9);

            QuarantineCenter quarantineCenter=new QuarantineCenter(id,name,city,district,capacity,director,dicon,hoscon1,hoscon2);
            return quarantineCenter;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void iconHomeonClicked(MouseEvent mouseEvent) throws IOException {

        //Navigating Based on Access privileges

        Stage stage1 = (Stage) this.ancQc.getScene().getWindow();
        String title=stage1.getTitle();
        // System.out.println(title);


        if (title.equalsIgnoreCase("admin")){
            URL url=this.getClass().getResource("/view/Dashboard.fxml");
            Parent parent= FXMLLoader.load(url);
            Scene scene=new Scene(parent);
            Stage stage= (Stage) this.ancQc.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Admin");
            stage.setFullScreen(false);

        }else if(title.equalsIgnoreCase("PSTF")) {
            URL url=this.getClass().getResource("/view/Dashboard.fxml");
            Parent parent= FXMLLoader.load(url);
            Scene scene=new Scene(parent);
            Stage stage= (Stage) this.ancQc.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("PSTF");
            stage.setFullScreen(false);

        }
        else{
            URL url=this.getClass().getResource("/view/Login.fxml");
            Parent parent= FXMLLoader.load(url);
            Scene scene=new Scene(parent);
            Stage stage= (Stage) this.ancQc.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("IT-Person");
            stage.setFullScreen(false);

        }

    }

    public void reset(){

        btnDel.setDisable(true);

        btnSave.setText("Save");
        txtCapa.setText("");
        txtDire.setText("");
        //txtDireCont.setText("");
        txtHosCont1.setText("");
        txtDireCont.setText("");

        txtHosCont2.setText("");
        txtNm.setText("");
        txtId.setText("");
        txtCity.setText("");

        txtCapa.setPromptText("Capacity");
        txtDire.setPromptText("Head");
        txtDireCont.setPromptText("Head's Contact");
        txtHosCont1.setPromptText("Center Contact-1");
        txtHosCont2.setPromptText("Center Contact-2");

        txtNm.setPromptText("Center Name");
        txtId.setPromptText("Center ID");
        txtCity.setPromptText("City");

        Stage stag=new Stage();

    }

    private void loadQc(){

        //listHos = new ListView<String>(names);

        //listHos.setItems(names);

        try {
            PreparedStatement pstm= DBConnection.getInstance().getConnection().prepareStatement("select * From qc");
            ResultSet rst=pstm.executeQuery();
            while (rst.next()){
                names.add(rst.getString(2));
                listHos.getItems().add(rst.getString(2));
                System.out.println(rst.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
