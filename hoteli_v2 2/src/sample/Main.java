package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("LoginScreen.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane, 700, 450);
        primaryStage.setTitle("HotelXYZ v2.1");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {

        int l=101,m = 200;
        if(l == 100) {
            System.out.println("1");
        } else if(m == 200) {
            System.out.println("2");
        }

        launch(args);
    }

}
