import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AppInitializer extends Application {

    public static void main(String[] args) throws UnsupportedEncodingException {
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
