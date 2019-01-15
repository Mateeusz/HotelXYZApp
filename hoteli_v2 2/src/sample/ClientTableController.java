package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientTableController implements Initializable{

    @FXML
    TableView<ClientReservationToDisplay> table;

    @FXML
    TableColumn<ClientReservationToDisplay, String> idTab;

    @FXML
    TableColumn<ClientReservationToDisplay, String> typTab;

    @FXML
    TableColumn<ClientReservationToDisplay, String> poczatekTab;

    @FXML
    TableColumn<ClientReservationToDisplay, String> koniecTab;

    @FXML
    TableColumn<ClientReservationToDisplay, String> numerTab;

    @FXML
    AnchorPane newReservationPaneClientId;

    LoginScreenController loginScreenController;

    ObservableList<ClientReservationToDisplay> oblist = FXCollections.observableArrayList();

    protected String login;
    protected String password;

    public AnchorPane getNewReservationPaneId() {
        return newReservationPaneClientId;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //System.out.println(loginScreenController.getCurrentLogin());
        //setPassword(loginScreenController.getCurrentPass());
        System.out.println(";;" + login + password + "[[");
        Connection con = DBConnector.getConnection();
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT\n" +
                    " roomReservation.reservationId,\n" +
                    " reservation.beginDate,\n" +
                    " reservation.endDate,\n" +
                    " room.roomNumber,\n" +
                    " room.type\n" +
                    "FROM\n" +
                    " reservation\n" +
                    "INNER JOIN roomReservation ON reservation.reservationId = roomReservation.reservationId\n" +
                    "INNER JOIN room ON roomReservation.roomId = room.roomId\n" +
                    "INNER JOIN client ON reservation.clientId = client.clientId\n" +
                    "INNER JOIN personalData ON client.personalDataId = personalData.personalDataId\n" +
                    "WHERE personalData.login = "+login+" AND personalData.password = "+password+";");

            while(rs.next()) {

                oblist.add(new ClientReservationToDisplay(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            System.out.println("i zapytanie");
            e.printStackTrace();
        }
//        System.out.println("poszlo" + oblist.get(1).getNumerRezerwacji() + oblist.get(1).getPoczatek() + oblist.get(1).getPoczatek());

        idTab.setCellValueFactory(new PropertyValueFactory<ClientReservationToDisplay, String>("numerRezerwacji") );
        poczatekTab.setCellValueFactory(new PropertyValueFactory<ClientReservationToDisplay, String>("poczatek"));
        koniecTab.setCellValueFactory(new PropertyValueFactory<ClientReservationToDisplay, String>("koniec"));
        numerTab.setCellValueFactory(new PropertyValueFactory<ClientReservationToDisplay, String>("numerPokoju"));
        typTab.setCellValueFactory(new PropertyValueFactory<ClientReservationToDisplay, String>("Typ"));

        table.setItems(oblist);

        System.out.println("L_______ " + oblist.get(0).getNumerRezerwacji());
    }
}
