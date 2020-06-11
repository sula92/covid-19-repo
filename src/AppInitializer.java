import db.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AppInitializer extends Application {

    public static void main(String[] args) throws UnsupportedEncodingException {


        LocalDate currentdate= LocalDate.now();

        try {
            PreparedStatement pst= DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM suspected");
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
               String id=rs.getString(1);
               Date date=rs.getDate(3);

                LocalDate date1= date.toLocalDate();
                System.out.println(date1);
                System.out.println(id);

                int duration = (int) ChronoUnit.DAYS.between(date1, currentdate);
                System.out.println(duration);
                if(duration>=21){

                    PreparedStatement ps5=DBConnection.getInstance().getConnection().prepareStatement("select id from auto_remove");
                    ResultSet rs2=ps5.executeQuery();

                    if(!rs2.next()){
                        PreparedStatement ps2=DBConnection.getInstance().getConnection().prepareStatement("insert into auto_remove values (?,?)");
                        ps2.setString(1,id);
                        ps2.setDate(2, Date.valueOf(LocalDate.now()));
                        ps2.executeUpdate();

                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement ps2=DBConnection.getInstance().getConnection().prepareStatement("select id from covid_positive");
            ResultSet resultSet=ps2.executeQuery();
            while (resultSet.next()){
                String id1=resultSet.getString(1);
                PreparedStatement ps3=DBConnection.getInstance().getConnection().prepareStatement("select id from auto_remove where id=?");
                ps3.setString(1,id1);
                ResultSet r=ps3.executeQuery();
                if(r.next()){
                    PreparedStatement ps4=DBConnection.getInstance().getConnection().prepareStatement("delete from auto_remove where id=?");
                    ps4.setString(1,id1);
                    ps4.executeUpdate();

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/Login.fxml"));
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("COVID-19 Surveillance System");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
