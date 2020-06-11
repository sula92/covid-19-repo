package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import util.QuarantinedTM;
import util.RefTM;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ReferenceController implements Initializable {

    public Button btnSave;
    public Button btnClear;
    public TableView<RefTM> tblPatient;
    public Button btnAdd;
    public AnchorPane ancPatient;
    public ComboBox<String> cmbDistricts;
    public ComboBox<String> cmbhospitals;
    public FontAwesomeIconView iconhome;
    public AnchorPane ancQuarantined;
    public ListView<String> ViewListRef;
    public JFXTextField txtConnection;
    public JFXTextField txtSearRef;
    public JFXTextField txtReas;
    public AnchorPane ancRefs;
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

    ObservableList<RefTM> refs=FXCollections.observableArrayList();

    ObservableList<String> refIds=FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        loadDistricts();
        loadRefs();
        setId();
        btnCovid.setDisable(true);
        txtId.setDisable(true);


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

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNm.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colDistrict.setCellValueFactory(new PropertyValueFactory<>("district"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colRef.setCellValueFactory(new PropertyValueFactory<>("refId"));
        colDel.setCellValueFactory(new PropertyValueFactory<>("del"));



        txtSearRef.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("www");
                ViewListRef.getItems().clear();
                ObservableList<String> serchRefs=FXCollections.observableList(ViewListRef.getItems());

                refIds.stream().forEach(refTM -> {
                    System.out.println(refTM );
                    if ((refTM .contains(newValue))){
                        System.out.println(refTM );

                        serchRefs.add(refTM);
                        //listHos.getItems().add(serchOrderTM);
                        ViewListRef.refresh();
                    }

                });
            }
        });




        tblPatient.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String id=newValue.getId();
            btnSave.setText("UPDATE");
            btnCovid.setDisable(false);
            System.out.println(id);
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

                ObservableList<RefTM> refTMS=FXCollections.observableArrayList();
                refTMS.clear();

                 refs.stream().forEach(patientTM -> {

                     if (patientTM.getId().contains(txtSearch.getText())||patientTM.getProvince().contains(txtSearch.getText())||patientTM.getName().contains(txtSearch.getText())){

                         refTMS.add(patientTM);
                         tblPatient.setItems(refTMS);
                         tblPatient.refresh();

                     }


                 });


            }
        });


    }


    @FXML
    void btnClearOnAction(ActionEvent event) {

        reset();

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

        String refId=ViewListRef.getSelectionModel().getSelectedItem();
        String connect=txtConnection.getText();

        if(id.isEmpty()||fnm.isEmpty()||address.isEmpty()||city.isEmpty()||dis.isEmpty()||pro.isEmpty()||blood.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Make Sure To Fill All The Fields").show();
            return;
        }

        if (!Pattern.matches("[0-9]{10}", con)){
            new Alert(Alert.AlertType.ERROR,"Wrong Contact Number").show();
            return;
        }


        java.sql.Date date= java.sql.Date.valueOf(dateDate.getValue());
        String reason=txtReas.getText();

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
                    PreparedStatement pst=connection.prepareStatement("insert into reference values(?,?,?)");
                    pst.setString(1,id);
                    pst.setString(2,refId);
                    pst.setString(3,connect);

                    int j=pst.executeUpdate();
                    if (j>0){
                        System.out.println("run2");
                        PreparedStatement pst2=connection.prepareStatement("insert into suspected values(?,?,?)");
                        pst2.setString(1,id);
                        pst2.setString(2,reason);
                        pst2.setDate(3,date);

                        int k=pst2.executeUpdate();

                        if(k>0){
                            new Alert(Alert.AlertType.INFORMATION,"Record Added Successfully").show();
                        }
                        else {
                            new Alert(Alert.AlertType.ERROR,"Failed to add the record").show();
                        }

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
                PreparedStatement pst=connection.prepareStatement("update reference set reference_id=?,connection=? where suspect_id=?");
                pst.setString(1,refId);
                pst.setString(2,connect);
                pst.setString(3,id);


                int k=pst.executeUpdate();

                if(k>0){
                    PreparedStatement ps=connection.prepareStatement("update suspected set reason=?,date=? where id=?");
                    ps.setString(1,reason);
                    ps.setDate(2,date);
                    ps.setString(3,id);

                    int x=pst.executeUpdate();
                    if(x>0){
                        new Alert(Alert.AlertType.INFORMATION,"Record updated Successfully").show();
                        connection.commit();

                    }
                    else {
                        new Alert(Alert.AlertType.ERROR,"Failed to Update the record").show();
                        connection.rollback();

                    }

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



        PreparedStatement pst= DBConnection.getInstance().getConnection().prepareStatement("select * FROM (people pe inner join `reference` p on pe.id=p.suspect_id) inner join suspected s on pe.id=s.id");
        ResultSet rst=pst.executeQuery();


        while (rst.next()){
            String id=rst.getString(1);
            String nm=rst.getString(2)+" "+rst.getString(3);
            String city=rst.getString(5);
            String dis=rst.getString(6);
            String pro=rst.getString(7);
            String nic=rst.getString(8);
            String ref=rst.getString(12);
            Date date= rst.getDate(16);

            System.out.println(id);
            System.out.println(date);


            Button del=new Button(("Delete"));
            del.setStyle("-fx-background-color: blue; -fx-text-fill: white");

            RefTM refTM=new RefTM(id,nm,city,dis,pro,nic,date,ref,del);
            refs.add(refTM);
            System.out.println(refTM.getCity());
            tblPatient.getItems().add(refTM);
            tblPatient.refresh();

            del.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    btnDelOnAction(id);
                    tblPatient.getItems().remove(refTM);
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
        txtReas.setText("");
        txtProvince.setValue("");
        cmbBlood.setValue("");
        dateDate.setValue(LocalDate.now());
        txtConnection.setText("");
        btnCovid.setDisable(true);
    }

    private void setPatient(String id) throws SQLException {

        System.out.println(id);

        PreparedStatement pst= DBConnection.getInstance().getConnection().prepareStatement("select * FROM (people pe inner join `reference` p on pe.id=p.suspect_id) inner join suspected s on pe.id=s.id where pe.id=?");
        pst.setString(1,id);
        ResultSet rst=pst.executeQuery();

        rst.next();
            String fnm = rst.getString(2);
            String lnm = rst.getString(3);
            String city = rst.getString(5);
            String dis = rst.getString(6);
            String pro = rst.getString(7);
            String nic = rst.getString(8);
            String ref = rst.getString(12);
            LocalDate date = rst.getDate(16).toLocalDate();;

            txtId.setText(id);
            txtFnm.setText(fnm);
            txtLnm.setText(lnm);
            txtAdd.setText(rst.getString(4));
            txtCity.setText(city);
            cmbDistricts.setValue(dis);
            txtProvince.setValue(pro);
            txtSearRef.setText(ref);
            txtNic.setText(nic);
            txtContact.setText(rst.getString(9));
            cmbBlood.setValue(rst.getString(10));
            //txtHos.setText(hos);
            dateDate.setValue(date);
            txtReas.setText(rst.getString(15));
            txtConnection.setText(rst.getString(13));
            txtId.setText(rst.getString(1));

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
                PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM suspected WHERE id=?");
                pstm.setObject(1, id);
                if (pstm.executeUpdate() == 0) {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the patient", ButtonType.OK).show();
                } else {
                    PreparedStatement pstm1 = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM reference WHERE suspect_id=?");
                    pstm1.setObject(1, id);
                    if(pstm1.executeUpdate()>0){

                        PreparedStatement pstm2 = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM people WHERE id=?");
                        pstm2.setObject(1, id);

                        if(pstm2.executeUpdate()>0){
                            txtSearch.clear();
                            tblPatient.getItems().clear();
                            loadPatient();
                            new Alert(Alert.AlertType.INFORMATION,"Record Deleted successfully").show();

                        }
                        else {
                            new Alert(Alert.AlertType.ERROR, "Failed to delete the patient", ButtonType.OK).show();

                        }


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

        Stage stage1 = (Stage) this.ancRefs .getScene().getWindow();
        String title=stage1.getTitle();
        // System.out.println(title);


        if (title.equalsIgnoreCase("admin")){
            URL url=this.getClass().getResource("/view/Dashboard.fxml");
            Parent parent= FXMLLoader.load(url);
            Scene scene=new Scene(parent);
            Stage stage= (Stage) this.ancRefs.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Admin");
            stage.centerOnScreen();
            stage.show();

        }else if(title.equalsIgnoreCase("PSTF")) {
            URL url=this.getClass().getResource("/view/Dashboard.fxml");
            Parent parent= FXMLLoader.load(url);
            Scene scene=new Scene(parent);
            Stage stage= (Stage) this.ancRefs.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("PSTF");
            stage.centerOnScreen();
            stage.show();

        }
        else{
            URL url=this.getClass().getResource("/view/Login.fxml");
            Parent parent= FXMLLoader.load(url);
            Scene scene=new Scene(parent);
            Stage stage= (Stage) this.ancRefs.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("IT-Person");
            stage.centerOnScreen();
            stage.show();

        }
    }

    private void loadRefs(){
        try {
            PreparedStatement pst1=connection.prepareStatement("select id from patient");
            ResultSet r1=pst1.executeQuery();
            while (r1.next()){
              ViewListRef.getItems().add(r1.getString(1));
              refIds.add(r1.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement pst2=connection.prepareStatement("select id from quarantined");
            ResultSet r2=pst2.executeQuery();
            while (r2.next()){
                ViewListRef.getItems().add(r2.getString(1));
                refIds.add(r2.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                ps.setString(3,"Random PCR Test");
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
