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

public class TableController implements Initializable{

    @FXML
    TableView<Reservation> table;

    @FXML
    TableColumn<Reservation, String> idTab;

    @FXML
    TableColumn<Reservation, String> imieTab;

    @FXML
    TableColumn<Reservation, String> nazwiskoTab;

    @FXML
    TableColumn<Reservation, String> typTab;

    @FXML
    TableColumn<Reservation, String> poczatekTab;

    @FXML
    TableColumn<Reservation, String> koniecTab;

    @FXML
    TableColumn<Reservation, String> numerTab;

    @FXML
    AnchorPane allRezPaneId;

    ObservableList<Reservation> oblist = FXCollections.observableArrayList();

    private String login;

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("iii? " + login);
        Connection con = DBConnector.getConnection();
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT\n" +
                    " roomReservation.reservationId,\n" +
                    " reservation.beginDate,\n" +
                    " reservation.endDate,\n" +
                    " room.roomNumber,\n" +
                    " room.type,\n" +
                    " personalData.firstName,\n" +
                    " personalData.lastName\n" +
                    "FROM\n" +
                    " reservation\n" +
                    "INNER JOIN roomReservation ON reservation.reservationId = roomReservation.reservationId\n" +
                    "INNER JOIN room ON roomReservation.roomId = room.roomId\n" +
                    "INNER JOIN client ON reservation.clientId = client.clientId\n" +
                    "INNER JOIN personalData ON client.personalDataId = personalData.personalDataId;");

            while(rs.next()) {
                oblist.add(new Reservation(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            System.out.println("i zapytanie");
            e.printStackTrace();
        }
        System.out.println("poszlo" + oblist.get(1).getNazwisko() + oblist.get(1).getImie() + oblist.get(1).getPoczatek());

        idTab.setCellValueFactory(new PropertyValueFactory<Reservation, String>("numerRezerwacji") );
        poczatekTab.setCellValueFactory(new PropertyValueFactory<Reservation, String>("poczatek"));
        koniecTab.setCellValueFactory(new PropertyValueFactory<Reservation, String>("koniec"));
        numerTab.setCellValueFactory(new PropertyValueFactory<Reservation, String>("numerPokoju"));
        typTab.setCellValueFactory(new PropertyValueFactory<Reservation, String>("Typ"));
        imieTab.setCellValueFactory(new PropertyValueFactory<Reservation, String>("Imie"));
        nazwiskoTab.setCellValueFactory(new PropertyValueFactory<Reservation, String>("Nazwisko"));

        table.setItems(oblist);
    }
}
