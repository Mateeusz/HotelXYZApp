package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class ReceptionistScreenController {




    public void logOutAction1(Event event) {
        Parent tableViewParent = null;
        try {
            tableViewParent = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information.toSting
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();

    }

//    public void newRezAction() {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("newRezPane.fxml"));
//        try {
//           AnchorPaneId1  = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        borderPaneId2.setCenter(AnchorPaneId1);
//    }


}
