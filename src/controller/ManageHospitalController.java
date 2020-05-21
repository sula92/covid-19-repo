package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entity.Hospital;
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

public class ManageHospitalController implements Initializable {
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
    public JFXTextField txtFax;
    public JFXTextField txtEmail;
    public Button btnSave;
    public Button btnDel;
    public FontAwesomeIconView iconhome;
    public AnchorPane ancHos;
    ObservableList<String> names=FXCollections.observableArrayList();
    //listHos = new ListView<String>(names);


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadHos();

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

               Hospital hospital=getHospital(hosnm);
               txtId.setText(hospital.getId());
               txtNm.setText(hospital.getName());
               txtDis.setValue(hospital.getDistrict());
               txtCapa.setText(hospital.getCapacity());
               txtDire.setText(hospital.getDirector().getName());
               txtDireCont.setText(hospital.getDirector().getContact());
               txtHosCont1.setText(hospital.getCotact1());
               txtHosCont2.setText(hospital.getContact2());
               txtFax.setText(hospital.getFax());
               txtCity.setText(hospital.getCity());
               txtEmail.setText(hospital.getEmail());
            }
        });


    }

    private String getTitle() {
        Stage stage = (Stage) this.ancHos.getScene().getWindow();
        String title=stage.getTitle();
        System.out.println(title);
        return title;

    }

    public void btnAddOnAc(ActionEvent event) {

        btnDel.setDisable(true);
        btnSave.setText("Save");
        reset();

        try {
            PreparedStatement pstm= DBConnection.getInstance().getConnection().prepareStatement("select id From hospital");
            ResultSet rst=pstm.executeQuery();
            rst.last();
            String id=rst.getString(1).replace("H","");
            int incId=Integer.parseInt(id)+1;
            String newId;

            if(incId<10){
                newId="H"+"00"+incId;
                txtId.setText(newId);
                return;
            }
            else if(incId<100 && incId>10){
                newId="H"+"0"+incId;
                txtId.setText(newId);
                return;
            }
            else {
                newId="H"+incId;
                txtId.setText(newId);
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void btnSaveOnAc(ActionEvent event) {

        if(txtDireCont.getText().isEmpty()||txtDire.getText().isEmpty()||txtCity.getText().isEmpty()||txtId.getText().isEmpty()||txtNm.getText().isEmpty()||txtFax.getText().isEmpty()||txtEmail.getText().isEmpty()||txtHosCont1.getText().isEmpty()||txtHosCont2.getText().isEmpty()||txtCapa.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Make Sure To Fill All The Fields").show();
            return;
        }

        if(btnSave.getText().equalsIgnoreCase("save")){

            String regex = "^(.+)@(.+)$";
            Pattern pattern = Pattern.compile(regex);

            String email=txtEmail.getText();
                Matcher matcher = pattern.matcher(email);

                if(!matcher.matches()){
                    new Alert(Alert.AlertType.ERROR,"The Email Pattern is Wrong").show();
                   return;
                }


                if (!Pattern.matches("[0-9]{10}", txtHosCont1.getText())){
                    new Alert(Alert.AlertType.ERROR,"Wrong Hospital Contact Number-1").show();
                    return;
                }
            if (!Pattern.matches("[0-9]{10}", txtHosCont2.getText())){
                new Alert(Alert.AlertType.ERROR,"Wrong Hospital Contact Number-2").show();
                return;
            }
            if (!Pattern.matches("[0-9]{10}", txtDireCont.getText())){
                new Alert(Alert.AlertType.ERROR,"Wrong Director's Contact Number").show();
                return;
            }

            try {
                PreparedStatement pstm=DBConnection.getInstance().getConnection().prepareStatement("insert into hospital values(?,?,?,?,?,?,?,?,?,?,?)");
                pstm.setString(1,txtId.getText());
                pstm.setString(2,txtNm.getText());
                pstm.setString(3,txtCity.getText());
                pstm.setString(4,txtDis.getValue().toString());
                pstm.setString(5,txtCapa.getText());
                pstm.setString(6,txtDire.getText());
                pstm.setString(7,txtDireCont.getText());
                pstm.setString(8,txtHosCont1.getText());
                pstm.setString(9,txtHosCont2.getText());
                pstm.setString(10,txtFax.getText());
                pstm.setString(11,txtEmail.getText());

                int i=pstm.executeUpdate();

                if(i<=0){
                    new Alert(Alert.AlertType.ERROR,"Record Can Not be Added!").show();
                }
                else {
                    new Alert(Alert.AlertType.INFORMATION,"Record Added Successfully").show();
                    listHos.getItems().clear();
                    loadHos();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {

            try {
                PreparedStatement pstm=DBConnection.getInstance().getConnection().prepareStatement("update hospital set `name`=?,city=?,district=?,capacity=?,director=?,director_contact_co=?,hospital_contact1=?,hospital_contact2=?,fax=?,email=? where id=?");
                pstm.setString(1,txtNm.getText());
                pstm.setString(2,txtCity.getText());
                pstm.setString(3,txtDis.getValue().toString());
                pstm.setString(4,txtCapa.getText());
                pstm.setString(5,txtDire.getText());
                pstm.setString(6,txtDireCont.getText());
                pstm.setString(7,txtHosCont1.getText());
                pstm.setString(8,txtHosCont2.getText());
                pstm.setString(9,txtFax.getText());
                pstm.setString(10,txtEmail.getText());
                pstm.setString(11,txtId.getText());
                int i=pstm.executeUpdate();
                if (i<=0){
                    new Alert(Alert.AlertType.ERROR,"Failed to Update").show();
                }
                else {
                    new Alert(Alert.AlertType.INFORMATION,"Updated Successfully").show();
                    listHos.getItems().clear();
                    loadHos();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            reset();

        }
        reset();
    }

    public void btnDelOnAc(ActionEvent event) {

        try {
            PreparedStatement pstm=DBConnection.getInstance().getConnection().prepareStatement("delete * FROM hospital where id=?");
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

    private Hospital getHospital(String nm){
        try {
            PreparedStatement pstm= DBConnection.getInstance().getConnection().prepareStatement("select * From hospital where `name`=?");
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
            String fax=rst.getString(10);
            String email=rst.getString(11);

            Hospital hospital=new Hospital(id,name,city,district,capacity,director,dicon,hoscon1,hoscon2,fax,email);
            return hospital;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void homeOnclicked(MouseEvent mouseEvent) throws IOException {

        //Navigating Based on Access privileges

        Stage stage1 = (Stage) this.ancHos.getScene().getWindow();
        String title=stage1.getTitle();
       // System.out.println(title);


        if (title.equalsIgnoreCase("admin")){
            URL url=this.getClass().getResource("/view/Dashboard.fxml");
            Parent parent= FXMLLoader.load(url);
            Scene scene=new Scene(parent);
            Stage stage= (Stage) this.ancHos.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Admin");
            stage.setFullScreen(false);

        }else if(title.equalsIgnoreCase("PSTF")) {
            URL url=this.getClass().getResource("/view/Dashboard.fxml");
            Parent parent= FXMLLoader.load(url);
            Scene scene=new Scene(parent);
            Stage stage= (Stage) this.ancHos.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("PSTF");
            stage.setFullScreen(false);

        }
        else{
            URL url=this.getClass().getResource("/view/Login.fxml");
            Parent parent= FXMLLoader.load(url);
            Scene scene=new Scene(parent);
            Stage stage= (Stage) this.ancHos.getScene().getWindow();
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
        txtDireCont.setText("");
        txtHosCont1.setText("");
        txtDireCont.setText("");
        txtEmail.setText("");
        txtFax.setText("");
        txtHosCont2.setText("");
        txtNm.setText("");
        txtId.setText("");
        txtCity.setText("");
        txtDis.setValue(null);

        txtCapa.setPromptText("Capacity");
        txtDire.setPromptText("Director");
        txtDireCont.setPromptText("Director's Contact");
        txtHosCont1.setPromptText("Hospital Contact-1");
        txtHosCont2.setPromptText("Hospital Contact-2");
        txtEmail.setPromptText("Email");
        txtFax.setPromptText("Fax");
        txtNm.setPromptText("Hoapital Name");
        txtId.setPromptText("Hospital ID");
        txtCity.setPromptText("City");

    }

    private void loadHos(){


        //listHos = new ListView<String>(names);

        //listHos.setItems(names);

        try {
            PreparedStatement pstm= DBConnection.getInstance().getConnection().prepareStatement("select * From hospital");
            ResultSet rst=pstm.executeQuery();
            while (rst.next()){
                names.add(rst.getString(2));
                System.out.println(rst.getString(2));
                listHos.getItems().add(rst.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
