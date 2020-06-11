package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entity.Quarantined;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.ExitTM;
import util.PatientTM;
import util.QuarantinedTM;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

public class QuarantinedController {

    public Button btnSave;
    public Button btnClear;
    public TableView<QuarantinedTM> tblPatient;
    public Button btnAdd;
    public AnchorPane ancPatient;
    public ComboBox<String> cmbDistricts;
    public ComboBox<String> cmbhospitals;
    public FontAwesomeIconView iconhome;
    public AnchorPane ancQuarantined;
    public ComboBox<String> cmbReason;
    public JFXDatePicker dateExitDate;
    public Button btnExit;
    public TableView<ExitTM> tblExit;
    public TableColumn colPersonId;
    public TableColumn colExitDate;
    public JFXTextField txtSearchExit;
    public TableColumn colQcExit;
    public JFXTextField txtTo;
    public Button btnCovid;
    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtFnm;

    @FXML
    private JFXTextField txtLnm;

    @FXML
    private TextArea txtAdd;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtDistrict;

    @FXML
    private JFXComboBox<String> txtProvince;

    @FXML
    private JFXTextField txtNic;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXComboBox<String> cmbBlood;

    @FXML
    private JFXTextField txtHos;

    @FXML
    private JFXDatePicker dateDate;

    @FXML
    private TextArea txtReason;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colNm;

    @FXML
    private TableColumn colCity;

    @FXML
    private TableColumn colDistrict;

    @FXML
    private TableColumn colHos;

    @FXML
    private TableColumn colProvince;

    @FXML
    private TableColumn colNIC;

    @FXML
    private TableColumn colDate;

    @FXML
    private TableColumn colRef;

    @FXML
    private TableColumn colDel;

    @FXML
    private JFXTextField txtSearch;

    Connection connection= DBConnection.getInstance().getConnection();

    ObservableList<QuarantinedTM> patients=FXCollections.observableArrayList();



    public void initialize() {

        loadHospitals();
        loadDistricts();
        setId();
        txtId.setDisable(true);
        btnCovid.setDisable(true);
        loadExitData();
        loadExitReasons();

        disableExit();


        String provinceText = " Western\n" +
                " Eastern\n" +
                " Southern\n" +
                " Central\n" +
                " North Central\n" ;
        String[] provinces = provinceText.split("\n");
        ObservableList<String> olProvinces = FXCollections.observableArrayList(Arrays.asList(provinces));
        txtProvince.setItems(olProvinces);

        String bgroupText =
                " A+\n" +
                " A-\n" +
                " B+\n" +
                " B-\n" +
                " O+\n" +
                " O-\n" +
                " AB+\n" +
                " AB-\n";
        String[] bgroups = bgroupText.split("\n");
        ObservableList<String> olbgroups = FXCollections.observableArrayList(Arrays.asList(bgroups));
        cmbBlood.setItems(olbgroups);


        try {
            loadPatient();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //table quarantined....................................................................

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNm.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colDistrict.setCellValueFactory(new PropertyValueFactory<>("district"));
        colHos.setCellValueFactory(new PropertyValueFactory<>("center"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colRef.setCellValueFactory(new PropertyValueFactory<>("ref"));
        colDel.setCellValueFactory(new PropertyValueFactory<>("del"));

        tblPatient.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String id=newValue.getId();
            btnSave.setText("UPDATE");
            btnCovid.setDisable(false);
            System.out.println("qc"+id);


            enableExitForm(id,newValue.getCenter());

            try {
                setPatient(id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                tblPatient.getItems().clear();

                ObservableList<QuarantinedTM> patientTMS=FXCollections.observableArrayList();
                patientTMS.clear();

                 patients.stream().forEach(patientTM -> {

                     if (patientTM.getId().contains(txtSearch.getText())||patientTM.getProvince().contains(txtSearch.getText())||patientTM.getName().contains(txtSearch.getText())){

                         patientTMS.add(patientTM);
                         tblPatient.setItems(patientTMS);
                         tblPatient.refresh();

                     }


                 });


            }
        });

        //tblexit................................................................

        colPersonId.setCellValueFactory(new PropertyValueFactory<>("personId"));
        colExitDate.setCellValueFactory(new PropertyValueFactory<>("exitdate"));
        colQcExit.setCellValueFactory(new PropertyValueFactory<>("hosExId"));

        loadExitData();

        tblExit.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            String id=newValue.getPersonId();

            enableExitForm(id,newValue.getHosExId());


            System.out.println(id);
            try {
                setPatient(id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });


    }


    @FXML
    void btnClearOnAction(ActionEvent event) {

        reset();
        initialize();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {

        String id=txtId.getText();
        String fnm=txtFnm.getText();
        String lnm=txtLnm.getText();
        String address=txtAdd.getText();
        String city=txtCity.getText();
        String dis=cmbDistricts.getValue();
        String pro=txtProvince.getValue();
        String nic=txtNic.getText();
        String con=txtContact.getText();
        String blood=cmbBlood.getValue();

        String hos=cmbhospitals.getValue();
        java.sql.Date date= java.sql.Date.valueOf(dateDate.getValue());
        String reason=txtReason.getText();

        if(id.isEmpty()||fnm.isEmpty()||address.isEmpty()||city.isEmpty()||dis.isEmpty()||pro.isEmpty()||blood.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Make Sure To Fill All The Fields").show();
            return;
        }

        if (!Pattern.matches("[0-9]{10}", con)){
            new Alert(Alert.AlertType.ERROR,"Wrong Contact Number").show();
            return;
        }

        if(btnSave.getText().equalsIgnoreCase("Save")){
           //connection.setAutoCommit(false);

            try {
                System.out.println("run");
                PreparedStatement preparedStatement=connection.prepareStatement("insert into people values(?,?,?,?,?,?,?,?,?,?)");
                preparedStatement.setString(1,id);
                preparedStatement.setString(2,fnm);
                preparedStatement.setString(3,lnm);
                preparedStatement.setString(4,address);
                preparedStatement.setString(5,city);
                preparedStatement.setString(6,dis);
                preparedStatement.setString(7,pro);
                preparedStatement.setString(8,nic);
                preparedStatement.setString(9,con);
                preparedStatement.setString(10,blood);
                int i=preparedStatement.executeUpdate();
                if(i>0){
                    System.out.println("run");
                    PreparedStatement pst=connection.prepareStatement("insert into quarantined values(?,?,?,?)");
                    pst.setString(1,id);
                    pst.setString(2,reason);
                    pst.setDate(3,date);
                    pst.setString(4,hos);
                    int j=pst.executeUpdate();
                    if (j>0){
                        new Alert(Alert.AlertType.INFORMATION,"Record Added Successfully").show();
                        //connection.commit();
                    }
                    else {
                        new Alert(Alert.AlertType.ERROR,"Failed to add the record").show();
                        //connection.rollback();
                    }
                }
                else {
                    new Alert(Alert.AlertType.ERROR,"Failed to add the record").show();
                    //connection.rollback();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        //Updating

        else {

            connection.setAutoCommit(false);

            PreparedStatement preparedStatement=connection.prepareStatement("update people set first_name=?,last_name=?,address=?,city=?,district=?,province=?,nic=?,contact_number=?,blood_group=? where id=?");
            preparedStatement.setString(1,fnm);
            preparedStatement.setString(2,lnm);
            preparedStatement.setString(3,address);
            preparedStatement.setString(4,city);
            preparedStatement.setString(5,dis);
            preparedStatement.setString(6,pro);
            preparedStatement.setString(7,nic);
            preparedStatement.setString(8,con);
            preparedStatement.setString(9,blood);
            preparedStatement.setString(10,id);

            int i=preparedStatement.executeUpdate();

            if(i>0){
                PreparedStatement pst=connection.prepareStatement("update quarantined set hospital=?,date=?,reason=? where id=?");
                pst.setString(1,hos);
                pst.setDate(2,date);
                pst.setString(3,reason);
                pst.setString(4,id);

                int k=pst.executeUpdate();

                if(k>0){
                    new Alert(Alert.AlertType.INFORMATION,"Record updated Successfully").show();
                    connection.commit();
                }
            }
            else {
                new Alert(Alert.AlertType.ERROR,"Failed to Update the record").show();
                connection.rollback();

            }
        }

        try {
            loadPatient();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void loadPatient() throws SQLException {

        System.out.println("in load");

        tblPatient.getItems().clear();



        PreparedStatement pst= DBConnection.getInstance().getConnection().prepareStatement("select * FROM people pe inner join quarantined p on pe.id=p.id");
        ResultSet rst=pst.executeQuery();


        while (rst.next()){
            String id=rst.getString(1);
            String nm=rst.getString(2)+" "+rst.getString(3);
            String city=rst.getString(5);
            String dis=rst.getString(6);
            String pro=rst.getString(7);
            String nic=rst.getString(8);
            String hos=rst.getString(14);
            Date date= rst.getDate(13);

            System.out.println(id);
            System.out.println(date);

            Button ref=new Button("References");
            ref.setStyle("-fx-background-color: blue; -fx-text-fill: white");

            Button del=new Button(("Delete"));
            del.setStyle("-fx-background-color: blue; -fx-text-fill: white");

            QuarantinedTM patientTM=new QuarantinedTM(id,nm,city,dis,hos,pro,nic,date,ref,del);
            patients.add(patientTM);
            System.out.println(patientTM.getCity());
            tblPatient.getItems().add(patientTM);
            tblPatient.refresh();

            del.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    btnDelOnAction(id);
                    tblPatient.getItems().remove(patientTM);
                    tblPatient.getSelectionModel().clearSelection();
                    tblPatient.refresh();
                    try {
                        loadPatient();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            });

        }


    }


    public void btnAddOnAction(ActionEvent event) {

        btnSave.setText("SAVE");
        reset();
        setId();

    }

    private void setId(){
        try {
            PreparedStatement pst=connection.prepareStatement("select id from people");
            ResultSet r=pst.executeQuery();

            if(!r.next()){
                txtId.setText("001");
                return;
            }
            r.last();
            int oldId= Integer.parseInt(r.getString(1));
            int newId=oldId+1;
            if(newId<10){
                String id="00"+newId;
                txtId.setText(id);
            }
            else if(newId<100 && newId>10){
                String id="0"+newId;
                txtId.setText(id);
            }
            else {
                String id=String.valueOf(newId);
                txtId.setText(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void reset(){
        txtAdd.setText("");
        txtCity.setText("");
        txtContact.setText("");
        //txtDistrict.setText("");
        txtFnm.setText("");
        txtLnm.setText("");
        //txtHos.setText("");
       //txtId.setText("");
        txtNic.setText("");
        txtReason.setText("");
        txtProvince.setValue("");
        cmbBlood.setValue("");
        dateDate.setValue(LocalDate.now());
        btnExit.setText("Exit");
        btnSave.setText("SAVE");
        btnSave.setDisable(true);
    }

    private void setPatient(String id) throws SQLException {

        System.out.println(id);

        PreparedStatement pst= DBConnection.getInstance().getConnection().prepareStatement("select * FROM people pe inner join quarantined p on pe.id=p.id where pe.id=?");
        pst.setString(1,id);
        ResultSet rst=pst.executeQuery();

        rst.next();
            String fnm = rst.getString(2);
            String lnm = rst.getString(3);
            String city = rst.getString(5);
            String dis = rst.getString(6);
            String pro = rst.getString(7);
            String nic = rst.getString(8);
            String hos = rst.getString(14);
            LocalDate date = rst.getDate(13).toLocalDate();;

            txtId.setText(id);
            txtFnm.setText(fnm);
            txtLnm.setText(lnm);
            txtAdd.setText(rst.getString(4));
            txtCity.setText(city);
            cmbDistricts.setValue(dis);
            txtProvince.setValue(pro);
            cmbhospitals.setValue(hos);
            txtNic.setText(nic);
            txtContact.setText(rst.getString(9));
            cmbBlood.setValue(rst.getString(10));
            //txtHos.setText(hos);
            dateDate.setValue(date);
            txtReason.setText(rst.getString(12));

    }

    private void loadHospitals(){

        ObservableList<String> hospitals=FXCollections.observableArrayList();

        try {
            PreparedStatement preparedStatement=connection.prepareStatement("select id from qc");
            ResultSet rst=preparedStatement.executeQuery();
            while (rst.next()){
                cmbhospitals.getItems().add(rst.getString(1));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void loadDistricts(){

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
        cmbDistricts.setItems(olDistricts);

    }

    private void btnDelOnAction(String id){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this patient?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            //PatientTM selectedItem = tblPatient.getSelectionModel().getSelectedItem();
            try {
                PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM quarantined WHERE id=?");
                pstm.setObject(1, id);
                if (pstm.executeUpdate() == 0) {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the patient", ButtonType.OK).show();
                } else {
                    PreparedStatement pstm1 = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM people WHERE id=?");
                    pstm1.setObject(1, id);
                    if(pstm1.executeUpdate()>0){
                        txtSearch.clear();
                        tblPatient.getItems().clear();
                        loadPatient();
                        new Alert(Alert.AlertType.INFORMATION,"Record Deleted successfully").show();
                    }
                    else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete the patient", ButtonType.OK).show();
                    }
                    //tblPatient.getItems().remove(selectedItem);
                    txtSearch.clear();
                    reset();
                    tblPatient.getItems().clear();
                    loadPatient();
                    tblPatient.getSelectionModel().clearSelection();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void iconhomeClick(MouseEvent mouseEvent) throws IOException {

        //Navigating Based on Access privileges

        Stage stage1 = (Stage) this.ancQuarantined.getScene().getWindow();
        String title=stage1.getTitle();
        // System.out.println(title);


        if (title.equalsIgnoreCase("admin")){
            URL url=this.getClass().getResource("/view/Dashboard.fxml");
            Parent parent= FXMLLoader.load(url);
            Scene scene=new Scene(parent);
            Stage stage= (Stage) this.ancQuarantined.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Admin");
            stage.centerOnScreen();
            stage.show();

        }else if(title.equalsIgnoreCase("PSTF")) {
            URL url=this.getClass().getResource("/view/Dashboard.fxml");
            Parent parent= FXMLLoader.load(url);
            Scene scene=new Scene(parent);
            Stage stage= (Stage) this.ancQuarantined.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("PSTF");
            stage.centerOnScreen();
            stage.show();

        }
        else{
            URL url=this.getClass().getResource("/view/Login.fxml");
            Parent parent= FXMLLoader.load(url);
            Scene scene=new Scene(parent);
            Stage stage= (Stage) this.ancQuarantined.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("IT-Person");
            stage.centerOnScreen();
            stage.show();

        }
    }


    public void btnExitOnActio(ActionEvent event) {

        String id=txtId.getText();
        String reas=cmbReason.getValue();
        String hosId=cmbhospitals.getValue();
        String to=txtTo.getText();
        String location="Quarantined Center";
        java.sql.Date date= java.sql.Date.valueOf(dateExitDate.getValue());

        if(id.isEmpty()||reas.isEmpty()||hosId.isEmpty()||to.isEmpty()||location.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Make Sure To Fill All The Fields").show();
            return;
        }


        if(btnExit.getText().equalsIgnoreCase("Exit")){
            try {
                PreparedStatement pst=connection.prepareStatement("insert into exit_data values(?,?,?,?,?,?)");
                pst.setString(1,id);
                pst.setString(2,reas);
                pst.setString(3,hosId);
                pst.setString(4,to);
                pst.setString(5,location);
                pst.setDate(6,date);

                int i=pst.executeUpdate();
                if(i>0){

                    PreparedStatement pst1=connection.prepareStatement("insert into quarantined_exits values(?,?,?,?,?,?)");
                    pst1.setString(1,id);
                    pst1.setString(2,hosId);

                    int j=pst1.executeUpdate();
                    if(j>0){
                        new Alert(Alert.AlertType.INFORMATION,"Exit Operation Successfully Completed").show();
                    }
                    else {
                        new Alert(Alert.AlertType.ERROR,"Unable to Exit").show();
                    }
                }
                else {
                    new Alert(Alert.AlertType.ERROR,"Unable to Exit").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                PreparedStatement ps=connection.prepareStatement("update exit_data set exit_reason=?, from_=?, to_=?, location=?, date=? where id=?");
                ps.setString(1,reas);
                ps.setString(2,hosId);
                ps.setString(3,to);
                ps.setString(4,location);
                ps.setDate(5,date);
                ps.setString(6,id);

                if(ps.executeUpdate()>0){

                    new Alert(Alert.AlertType.INFORMATION,"Upadated Successfullt").show();

                }
                else {
                    new Alert(Alert.AlertType.ERROR,"Failed To Update").show();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        initialize();

    }

    private void enableExitForm(String pid,String hosId){
        cmbReason.setDisable(false);
        txtTo.setDisable(false);
        dateExitDate.setDisable(false);
        btnExit.setDisable(false);


        PreparedStatement pst= null;
        try {
            pst = connection.prepareStatement("select * from exit_data where id=? AND from_=?");
            pst.setString(1,pid);
            pst.setString(2,hosId);
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                cmbReason.setValue(rs.getString(2));
                txtTo.setText(rs.getString(4));
                dateExitDate.setValue(rs.getDate(6).toLocalDate());

                btnExit.setText("Amend Exit");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadExitData(){
        tblExit.getItems().clear();

        try {
            PreparedStatement pst=connection.prepareStatement("select * from exit_data where from_ like ?");
            pst.setString(1,"Q%");
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                Date date=rs.getDate(6);
                System.out.println("aswdf"+date);
                ExitTM exitTM=new ExitTM(rs.getString(1),date,rs.getString(3));
                System.out.println(exitTM.getExitdate());
                tblExit.getItems().add(exitTM);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void disableExit(){
        cmbReason.setDisable(true);
        txtTo.setDisable(true);
        dateExitDate.setDisable(true);
        btnExit.setDisable(true);

        cmbReason.setValue("");
        txtTo.setText("");
        dateExitDate.setValue(LocalDate.now());
    }

    private void loadExitReasons(){
        ArrayList<String> reasons=new ArrayList<>();
        reasons.add("DISCHARGED");
        reasons.add("TRANSFERRED");
        reasons.add("DEAD ");

        cmbReason.getItems().setAll(reasons);
    }

    public void btnCovidOnAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to mark this person as covid+?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {

            try {
                java.sql.Date date= java.sql.Date.valueOf(LocalDate.now());
                PreparedStatement ps=connection.prepareStatement("insert into covid_positive values(?,?,?)");
                ps.setString(1,txtId.getText());
                ps.setDate(2,date);
                ps.setString(3,cmbhospitals.getValue());
                int i=ps.executeUpdate();
                if(i>0){
                    new Alert(Alert.AlertType.INFORMATION,"Marked As a Covid Positive").show();
                }
                else {
                    new Alert(Alert.AlertType.ERROR,"This Person has Already Been Marked As a Covid Positive ").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,"This Person has Already Been Marked As a Covid Positive ").show();
                e.printStackTrace();
            }

        }

    }
}
