package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entity.User;
import entity.WorkPlace;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import util.UserTM;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageUsersController implements Initializable {
    public AnchorPane ancUser;
    public JFXTextField txtnm;
    public JFXTextField txtcont;
    public JFXTextField txtemail;
    public JFXTextField txtunm;
    public JFXPasswordField txtpwd;
    public JFXComboBox<String> txtwork;
    public JFXComboBox<String> txtrole;
    public Button btnSave;
    public Button btnAdd;
    public TableView<UserTM> tblUser;
    public TableColumn colunm;
    public TableColumn colnm;
    public TableColumn colrole;
    public TableColumn coldel;
    public JFXTextField txtsear;
    public FontAwesomeIconView iconHome;
    ObservableList<UserTM> userTMS=FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadUsers();

        txtsear.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                ObservableList<UserTM> users=FXCollections.observableList(tblUser.getItems());
                users.clear();

                for (UserTM userTM:userTMS) {
                    if(userTM.getUnm().contains(newValue)||userTM.getNm().contains(newValue)) {
                        Button button=new Button("DELETE");
                        button.setStyle("-fx-background-color: red");
                        //tblUser.getItems().clear();
                        users.add(new UserTM(userTM.getUnm(), userTM.getNm(), userTM.getRole(), button));
                        button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                users.remove(userTM);
                                btnDelOnAction(userTM.getUnm());
                                txtsear.clear();
                                tblUser.refresh();
                            }

                        });
                    }
                }
            }
        });




        colunm.setCellValueFactory(new PropertyValueFactory<>("unm"));
        colnm.setCellValueFactory(new PropertyValueFactory<>("nm"));
        colrole.setCellValueFactory(new PropertyValueFactory<>("role"));
        coldel.setCellValueFactory(new PropertyValueFactory<>("button"));


        tblUser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UserTM>() {
            @Override
            public void changed(ObservableValue<? extends UserTM> observable, UserTM oldValue, UserTM newValue) {
                if (newValue == null) {
                    txtcont.clear();
                    txtemail.clear();;
                    txtnm.clear();
                    txtpwd.clear();
                    return;
                }
                txtnm.setText(newValue.getNm());
                txtunm.setText(newValue.getUnm());

                User user=getUser(newValue.getUnm());
                txtemail.setText(user.getEmail());
                txtcont.setText(user.getContact());



                btnSave.setText("UPDATE");

               /* String x="cusImages/";
                String y=newValue.getId();
                String z=".jpg";
                String xyz=x.concat(y).concat(z);

                Image img=new Image(xyz);
                iv1.setImage(img);
                iv1.setFitHeight(200.0);
                iv1.setFitWidth(300.0);
*/
            }
        });

        //.......................................................................

        txtrole.getItems().add("Admin");
        txtrole.getItems().add("PSTF");
        txtrole.getItems().add("Hospital-IT");
        txtrole.getItems().add("Quarantine_Center_IT");

        txtwork.setDisable(true);
        txtwork.setVisible(false);

        txtrole.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.longValue() == 2 || newValue.longValue() == 3) {
                    txtwork.setDisable(false);
                    txtwork.setVisible(true);
                    /*pne1.setLayoutY(349);
                    Window window = pne1.getScene().getWindow();
                    window.setHeight(680);*/
                    txtwork.setPromptText(newValue.longValue() == 2?"Select Hospital":"Select Quarantine Center");

                    if(newValue.longValue() == 2){
                        txtwork.getItems().clear();
                        try {
                            PreparedStatement pst= DBConnection.getInstance().getConnection().prepareStatement("select name FROM hospital");
                            ResultSet rst=pst.executeQuery();
                            while (rst.next()){
                                txtwork.getItems().add(rst.getString(1));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    else {

                        txtwork.getItems().clear();
                        try {
                            PreparedStatement pst= DBConnection.getInstance().getConnection().prepareStatement("select name FROM qc");
                            ResultSet rst=pst.executeQuery();
                            while (rst.next()){
                                txtwork.getItems().add(rst.getString(1));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }

                } else {
                    txtwork.setDisable(true);
                    txtwork.setVisible(false);
                   /* pne1.setLayoutY(311);
                    Window window = pne1.getScene().getWindow();
                    window.setHeight(640);*/
                }
            }
        });

    }

    private void loadUsers() {
        tblUser.getItems().clear();
        //userTMS = tblUser.getItems();
        try {
            PreparedStatement pstm=DBConnection.getInstance().getConnection().prepareStatement("select * FROM user");
            ResultSet rst=pstm.executeQuery();
            while (rst.next()){
                Button button=new Button("Delete");
                button.setStyle("-fx-background-color: red");

                String x=rst.getString(4);
                String y=rst.getString(1);
                String z=rst.getString(6);

                userTMS.add(new UserTM(rst.getString(4),rst.getString(1),rst.getString(6),button));
                tblUser.getItems().add(new UserTM(rst.getString(4),rst.getString(1),rst.getString(6),button));

                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        UserTM userTM=new UserTM(x,y,z,button);
                        btnDelOnAction(userTM.getUnm());
                        tblUser.getSelectionModel().clearSelection();
                        userTMS.remove(userTM);
                        userTMS.clear();
                        tblUser.refresh();
                        loadUsers();

                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private void btnDelOnAction(String unmame){
        try {
            PreparedStatement pst=DBConnection.getInstance().getConnection().prepareStatement("delete from user where unm=?");
            pst.setString(1,unmame);
            int i=pst.executeUpdate();
            if(i<=0){
                new  Alert(Alert.AlertType.ERROR,"Failed to Delete The User!").show();
            }
            else {
                new  Alert(Alert.AlertType.INFORMATION,"Deleted").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public User getUser(String uname){
        try {
            PreparedStatement pstm=DBConnection.getInstance().getConnection().prepareStatement("select * FROM user where unm=?");
            pstm.setString(1,uname);
            ResultSet rst=pstm.executeQuery();
            rst.next();
            User user=new User(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),rst.getString(7),rst.getString(8));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void btnSaveonAction(ActionEvent event) {

        if(txtcont.getText().isEmpty()||txtemail.getText().isEmpty()||txtunm.getText().isEmpty()||txtnm.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Make Sure To Fill All The Fields").show();
            return;
        }

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);

        String email=txtemail.getText();
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()){
            new Alert(Alert.AlertType.ERROR,"The Email Pattern is Wrong").show();
            return;
        }


        if (!Pattern.matches("[0-9]{10}", txtcont.getText())){
            new Alert(Alert.AlertType.ERROR,"Wrong Contact Number").show();
            return;
        }


        if(btnSave.getText().equalsIgnoreCase("save")){
            try {
                PreparedStatement pst=DBConnection.getInstance().getConnection().prepareStatement("insert into user values(?,?,?,?,?,?,?,?)");
                pst.setString(1,txtnm.getText());
                pst.setString(2,txtcont.getText());
                pst.setString(3,txtemail.getText());
                pst.setString(4,txtunm.getText());
                pst.setString(5,txtpwd.getText());
                pst.setString(6,txtrole.getValue());

                if(txtrole.getValue().equalsIgnoreCase("Hospital-IT")){
                    pst.setString(7,getWorkPlace(txtwork.getValue(),txtrole.getValue()));
                    pst.setString(8,null);
                    int i=pst.executeUpdate();
                    if(i<=0){
                        new Alert(Alert.AlertType.ERROR,"Failed to Insert").show();
                    }
                    else {
                        //addImage(txtnm.getText());
                        new Alert(Alert.AlertType.INFORMATION,"Record Added Successfully").show();
                        loadUsers();
                    }
                }
                else if(txtrole.getValue().equalsIgnoreCase("Quarantine_Center_IT")){
                    pst.setString(7,null);
                    pst.setString(8,getWorkPlace(txtwork.getValue(),txtrole.getValue()));
                    int i=pst.executeUpdate();
                    if(i<=0){
                        new Alert(Alert.AlertType.ERROR,"Failed to Insert").show();
                        loadUsers();
                    }
                    else {
                        new Alert(Alert.AlertType.INFORMATION,"Record Added Successfully").show();
                        loadUsers();
                    }
                }
                else {
                    pst.setString(7,null);
                    pst.setString(8,null);
                    int i=pst.executeUpdate();
                    if(i<=0){
                        new Alert(Alert.AlertType.ERROR,"Failed to Insert").show();
                        loadUsers();
                    }
                    else {
                        new Alert(Alert.AlertType.INFORMATION,"Record Added Successfully").show();
                        loadUsers();
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{

            System.out.println("in upadate");

            try {
                PreparedStatement pst=DBConnection.getInstance().getConnection().prepareStatement("update user set contact=?, email=?, unm=?, pwd=?, role=?, hospitalid=?, qcid=? where name=?");

                pst.setString(1,txtcont.getText());
                pst.setString(2,txtemail.getText());
                pst.setString(3,txtunm.getText());
                pst.setString(4,txtpwd.getText());
                pst.setString(5,txtrole.getValue());
                pst.setString(8,txtnm.getText());

                if(txtrole.getValue().equalsIgnoreCase("Hospital-IT")){
                    pst.setString(6,txtwork.getValue());
                    pst.setString(7,null);
                    int i=pst.executeUpdate();
                    if(i<=0){
                        new Alert(Alert.AlertType.ERROR,"Failed to Update").show();
                    }
                    else {
                        new Alert(Alert.AlertType.INFORMATION,"Record Added Successfully").show();
                        loadUsers();
                    }
                }
                else if(txtrole.getValue().equalsIgnoreCase("Quarantine_Center_IT")){
                    pst.setString(6,null);
                    pst.setString(7,txtwork.getValue());
                    int i=pst.executeUpdate();
                    if(i<=0){
                        new Alert(Alert.AlertType.ERROR,"Failed to update").show();
                    }
                    else {
                        new Alert(Alert.AlertType.INFORMATION,"Record Updated Successfully").show();
                        loadUsers();
                    }
                }
                else {
                    System.out.println("in else");
                    pst.setString(6,null);
                    pst.setString(7,null);
                    int i=pst.executeUpdate();
                    System.out.println(i);
                    if(i<=0){
                        new Alert(Alert.AlertType.ERROR,"Failed to Update").show();
                    }
                    else {
                        new Alert(Alert.AlertType.INFORMATION,"Record Updated Successfully").show();
                        loadUsers();
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void btnAddonAction(ActionEvent event) {

        btnSave.setText("Save");
        txtwork.setVisible(false);
        txtwork.setDisable(true);
        txtnm.setText("");
        txtcont.setText("");
        txtemail.setText("");
        txtunm.setText("");
        tblUser.getSelectionModel().clearSelection();

    }


    public void homeOnClicked(MouseEvent mouseEvent) throws IOException {

        URL url=this.getClass().getResource("/view/Dashboard.fxml");
        Parent parent= FXMLLoader.load(url);
        Scene scene=new Scene(parent);
        Stage stage= (Stage) this.ancUser.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin");
        stage.setFullScreen(false);

    }

    public void addImage(String mid){

        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter=new FileNameExtensionFilter("*.IMAGE","jpg","gif","png");
        fileChooser.addChoosableFileFilter(filter);
        int result=fileChooser.showSaveDialog(null);
        if(result==JFileChooser.APPROVE_OPTION){
            File selectedFile=fileChooser.getSelectedFile();
            String path=selectedFile.getAbsolutePath();

            int width = 963;    //width of the image
            int height = 640;   //height of the image
            BufferedImage image = null;
            File f = null;

            //read image
            try{
                f = new File(path); //image file path
                image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                image = ImageIO.read(f);
                System.out.println("Reading complete.");
            }catch(IOException e){
                System.out.println("Error: "+e);
            }

            //String corId=getImageId(mid);


            String s="D:\\IT\\DEP5\\covid19\\src\\userImages\\";
            String id=mid;
            String ext=".jpg";
            String imgpath=s.concat(id).concat(ext);
            System.out.println(imgpath);

            //write image
            try{
                f = new File(imgpath);  //output file path
                ImageIO.write(image, "jpg", f);
                System.out.println("Writing complete.");
            }catch(IOException e){
                System.out.println("Error: "+e);
            }

            System.out.println(path);

        }
        else if(result== JFileChooser.CANCEL_OPTION){
            System.out.println("No Data");
        }

    }

    private String getWorkPlace(String nm,String role){
        PreparedStatement pst;
        Connection con =DBConnection.getInstance().getConnection();

        try {
            pst=con.prepareStatement("select id from ? where name=?");
            pst.setString(2,nm);
            if(role.equalsIgnoreCase("Quarantine_Center_IT")){
                pst.setString(1,"qc");
                ResultSet r=pst.executeQuery();
                r.next();
                return r.getString(1);
            }
            else {
                pst.setString(1,"hospital");
                ResultSet r=pst.executeQuery();
                r.next();
                return r.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
