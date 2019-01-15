package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginScreenController {

    @FXML
    private BorderPane borderPaneId2;

    @FXML
    private Pane AnchorPaneId1;

    @FXML
    private Pane deletePaneId;

    @FXML
    private Pane changePaneId;

    @FXML
    private Pane allPaneId;

    @FXML
    private Pane dataPaneId;

    @FXML
    private java.awt.Button wyszukajButtonId;

    @FXML
    private BorderPane borderPaneId;

    @FXML
    private Pane myReservationsPaneId;

    @FXML
    private Pane newReservationPaneId;

    @FXML
    private Pane newReservationPaneClientId;

    @FXML
    private Pane myAccountCenterPaneId;

    @FXML
    private BorderPane loginBorderPaneId;

    @FXML
    private AnchorPane loginPaneId, createAccountPaneId, allRezPaneId;

    @FXML
    private Button haveNotAccouId, gotAccountBut;

    @FXML
    public TextField loginLabel, newLoginLabel;

    @FXML
    public PasswordField passwordLabel, newPasswordLabel;

    @FXML
    private TextField nameLabel, surrnameLabel, emailLabel, phoneLabel;

    @FXML
    private TextField peselLabel, streetLabel, streetNumberLabel, apartmentNumberLabel;

    @FXML
    private TextField cityLabel, zipLabel, dayLabel, monthLabel, yearLabel, wyszukajPeselId, reservationDeleteId;;

    @FXML
    private Label labelName, labelLastname, labelPesel, labelBirthdate, labelCity, labelStreet;

    @FXML
    private Label labelHomeNumber, labelFlatNumber, labelZipcode, labelPhoneNumber, labelEmail;

    @FXML
    private TextField sprawdzPeselId, domRezId, mieszkanieRezId, kodRezId, miejscowoscRezId, dzienRezId, rokRezId;

    @FXML
    private TextField loginRezId,hasloRezId, imieRezId, nazwiskoRezId, emailRezId, teleRezId, ulicaRezId, miesiacRezId;

    @FXML
    private ComboBox beginDayId, beginMonthId, beginYearId, endDayId, endMonthId, endYearId, roomTypeId;

    @FXML
    private Label labelNameClient,labelLastnameClient,labelPeselClient,labelEmailClient,labelPhoneNumberClient,labelBirthdateClient, labelStreetClient, labelHomeNumberClient, labelFlatNumberClient, labelZipcodeClient, labelCityClient;

    public String currentLogin, currentPass;

    public String getCurrentLogin() {
        return currentLogin;
    }

    public String getCurrentPass() {
        return currentPass;
    }

    public void createAccount() {

        gotAccountBut.setVisible(false);
        haveNotAccouId.setVisible(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateAccountPanelScreen.fxml"));
        try {
            createAccountPaneId = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loginBorderPaneId.setCenter(createAccountPaneId);


    }

    public void logIntoPage() {

        haveNotAccouId.setVisible(false);
        gotAccountBut.setVisible(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreenPane.fxml"));
        try {
            loginPaneId = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loginBorderPaneId.setCenter(loginPaneId);
    }

    public void setCurrentLogin(String currentLogin) {
        this.currentLogin = currentLogin;
    }

    public void setCurrentPass(String currentPass) {
        this.currentPass = currentPass;
    }

    public void logIntoApp(ActionEvent event) {

        ArrayList list = new ArrayList();
        Connection con = DBConnector.getConnection();
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT *\n" +
                    "FROM personaldata\n" +
                    "WHERE login='" + loginLabel.getText()  + "' AND password = '" + passwordLabel.getText() + "';");


            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("i zapytanie");
            e.printStackTrace();
        }
        currentLogin = loginLabel.getText();
        currentPass = (passwordLabel.getText());
        System.out.println("Świeże " + currentLogin + currentPass);
        if (list.size() != 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("HotelXYZ");
            alert.setHeaderText("Status logowania");
            alert.setContentText("Wprowadzono niepoprawny login lub hasło!");
            alert.showAndWait();
        }
        if (list.size() == 1) {
//            String wyraz;
//            String[] napis = new String[]{"1", "2","3","4"};
//            wyraz = "recepcionista" + napis[1];
//            System.out.println(wyraz);
            if(loginLabel.getText().equals("recepcjonista1") || loginLabel.getText().equals("recepcionista2") || loginLabel.getText().equals("recepcionista3") ){

                System.out.println("--> " + loginLabel.getText() + ", " + passwordLabel.getText());

                System.out.println("--> " + loginLabel.getText() + ", " + passwordLabel.getText());
                Parent tableViewParent = null;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReceptionistScreen.fxml"));
                try {
                    tableViewParent = fxmlLoader.load();
                    LoginScreenController controller = fxmlLoader.getController();
                    controller.setCurrentLogin(loginLabel.getText());
                    controller.setCurrentPass(passwordLabel.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene tableViewScene = new Scene(tableViewParent);

                //This line gets the Stage information.toSting
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(tableViewScene);
                window.show();

            }else {
                System.out.println("--> " + loginLabel.getText() + ", " + passwordLabel.getText());
                Parent tableViewParent = null;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
                try {
                    tableViewParent = fxmlLoader.load();
                    LoginScreenController controller = fxmlLoader.getController();
                    controller.setCurrentLogin(loginLabel.getText());
                    controller.setCurrentPass(passwordLabel.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene tableViewScene = new Scene(tableViewParent);

                //This line gets the Stage information.toSting
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(tableViewScene);
                window.show();
            }
        }
    }


    public void goToLoginPane() {

    }


    public void createAnAccount() {

        String pesel = peselLabel.getText();


        if ((newLoginLabel.getText().equals("")) || (newPasswordLabel.getText().equals("")) || (surrnameLabel.getText().equals(""))
                || (nameLabel.getText().equals("")) || (peselLabel.getText().equals("")) || (cityLabel.getText().equals(""))
                || (streetNumberLabel.getText().equals("")) || (zipLabel.getText().equals("")) || (dayLabel.getText().equals(""))
                || (monthLabel.getText().equals("")) || (yearLabel.getText().equals(""))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("HotelXYZ");
            alert.setHeaderText("Status nowego konta");
            alert.setContentText("Nowe konto niezostało utworzone. Wprowadź wszystkie pola obowiązkowe!");
            alert.showAndWait();
            System.out.println("1");
        } else if (pesel.length() != 11) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("HotelXYZ");
            alert.setHeaderText("Status nowego konta");
            alert.setContentText("Nowe konto niezostało utworzone. Niepoprawna długość numeru PESEL!");
            alert.showAndWait();
            System.out.println("2");

        } else {
            System.out.println("Czy są przedemną 1, 2, 3?  !");
            boolean peselIsWrong = false;
            boolean loginIsWrong = false;
            ArrayList list = new ArrayList();
            ArrayList listLogin = new ArrayList();
            Connection con = DBConnector.getConnection();
            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT pesel\n" +
                        "FROM personaldata\n" +
                        "WHERE pesel='" + peselLabel.getText() + "';");
                ResultSet rs1 = con.createStatement().executeQuery("SELECT login\n" +
                        "FROM personaldata;");

                while (rs.next()) {
                    list.add(rs.getString(1));
                }
                while (rs1.next()) {
                    listLogin.add(rs1.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 11; i++) {
                //48-57 cyfry
                if (pesel.charAt(i) > 57 || pesel.charAt(i) < 48) {
                    peselIsWrong = true;
                    break;
                }
            }

            for (int i = 0; i < listLogin.size(); i++) {
                System.out.println(listLogin.get(i));
                if (listLogin.get(i).equals(newLoginLabel.getText())) {
                    loginIsWrong = true;
                    break;
                }
            }

                if (peselIsWrong == true) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("HotelXYZ");
                    alert.setHeaderText("Status nowego konta");
                    alert.setContentText("Nowe konto niezostało utworzone. Numer PESEL mogą tworzyć tylko cyfry! " +
                            "Sprawdź poprawność wprowadzonego numeru PESEL!");
                    alert.showAndWait();
                }

                if (list.size() != 0) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("HotelXYZ");
                    alert.setHeaderText("Status nowego konta");
                    alert.setContentText("Nowe konto niezostało utworzone. Konto o podanym numerze PESEL już istnieje! ");
                    alert.showAndWait();
                    System.out.println("4!");

                } else if (loginIsWrong == true) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("HotelXYZ");
                    alert.setHeaderText("Status nowego konta");
                    alert.setContentText("Nowe konto niezostało utworzone. Konto o podanym loginie już istnieje! ");
                    alert.showAndWait();

                } else {
                    System.out.println("6! Czy byli 5, 4?");
                    Connection con1 = DBConnector.getConnection();
                    boolean createdAccounMessage = false;

                    try {

                        con1.createStatement().execute("Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)\n" +
                                "Values ( '" + nameLabel.getText() + "', '" + surrnameLabel.getText() + "', '" + yearLabel.getText() + "-" + monthLabel.getText() + "-" + dayLabel.getText() + "', " + phoneLabel.getText() + "," + peselLabel.getText() + ", '" + emailLabel.getText() + "', '" + newPasswordLabel.getText() + "', '" + newLoginLabel.getText() + "');");
                        con1.createStatement().execute("Insert Into addressData( streetNumber, streetName, city, zipCode, apartmentNumber )\n" +
                                "Values ( " + streetNumberLabel.getText() + ", '" + streetLabel.getText() + "', '" + cityLabel.getText() + "', '" + zipLabel.getText() + "'," + apartmentNumberLabel.getText() + ");");
                        con1.createStatement().execute("INSERT INTO client (personalDataId, addressDataId) (\n" +
                                "  SELECT addressData.addressDataId, personalData.personalDataId\n" +
                                "    FROM personalData, addressData\n" +
                                "    WHERE personalData.pesel = '" + peselLabel.getText() + "' AND addressData.streetName = '" + streetLabel.getText() + "' AND addressData.city = '" + cityLabel.getText() + "'\n" +
                                " AND addressData.zipCode = '" + zipLabel.getText() + "' AND addressData.streetNumber = '" + streetNumberLabel.getText() + "' );");
                         createdAccounMessage = true;
                    } catch (SQLException e) {

                        try {

                            con1.createStatement().execute("DELETE from addressData where streetName = '"+streetLabel.getText()+"' AND city = '"+cityLabel.getText()+"' AND zipcode = '"+zipLabel+"';\n");
                            con1.createStatement().execute("DELETE from personalData where pesel = '"+peselLabel+"';");
                        } catch (SQLException e1) {

                            e1.printStackTrace();
                        }

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("HotelXYZ");
                        alert.setHeaderText("Status nowego konta");
                        alert.setContentText("Nowe konto niezostało utworzone. Sprawdź poprawność wprowadzonych danych! ");
                        alert.showAndWait();
                        e.printStackTrace();
                    }

                    if(createdAccounMessage == true) {


                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("HotelXYZ");
                        alert.setHeaderText("Status nowego konta");
                        alert.setContentText("Nowe konto zostało utworzone poprawnie! Możesz się do niego zalogować korzystając z panelu logowania.");
                        alert.showAndWait();
                    }
                }
            }
        }


    public void wyszukajAction() {   // akcja do przycisku znajdź dane klient po nr pesel, funkcjonalność po stronie pracownika
        String wyszukajPesel = wyszukajPeselId.getText();



        if(wyszukajPesel.length() != 11)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("HotelXYZ");
            alert.setHeaderText("Status");
            alert.setContentText("Niepoprawna długość numeru PESEL!");
            alert.showAndWait();
        }else
        {
            ArrayList list = new ArrayList();
            Connection con = DBConnector.getConnection();
            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT *\n" +
                        "FROM personaldata\n" +
                        "WHERE pesel='" + wyszukajPeselId.getText() + "';");
                while (rs.next()) {
                    list.add(rs.getString(1));
                }
            } catch (SQLException e) {
                System.out.println("i zapytanie");
                e.printStackTrace();
            }
            if (list.size() != 1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("HotelXYZ");
                alert.setHeaderText("Status");
                alert.setContentText("Wprowadzono niepoprawny pesel lub nie ma takiego w bazie.");
                alert.showAndWait();
            }
            if(list.size() == 1) {
                String imie = null;
                String nazwisko = null;
                String urodziny = null;
                String numertele = null;
                String pesel = null;
                String email = null;
                String miasto = null;
                String ulica = null;
                String numerdomu = null;
                String numermieszkania = null;
                String kodpocztowy = null;
                String IdPersonal = null;
                try
                {
                    ResultSet rs = con.createStatement().executeQuery("SELECT * FROM personaldata WHERE pesel='"+wyszukajPeselId.getText() +"';");
                    while (rs.next()) {

                        IdPersonal = (rs.getString("personaldataid"));
                        imie = (rs.getString("firstname"));
                        nazwisko = (rs.getString("lastname"));
                        pesel = (rs.getString("pesel"));
                        urodziny = (rs.getString("birtdate"));
                        numertele = (rs.getString("phonenumber"));
                        email = (rs.getString("email"));
                        labelName.setText(imie);
                        labelLastname.setText(nazwisko);
                        labelPesel.setText(pesel);
                        labelBirthdate.setText(urodziny);
                        labelPhoneNumber.setText(numertele);
                        labelEmail.setText(email);
                    }
                    ResultSet rs1 = con.createStatement().executeQuery("SELECT * FROM addressdata WHERE addressdataid='"+IdPersonal +"';");
                    while (rs1.next()) {
                        numerdomu = (rs1.getString("streetnumber"));
                        ulica = (rs1.getString("streetname"));
                        miasto = (rs1.getString("city"));
                        kodpocztowy = (rs1.getString("zipcode"));
                        numermieszkania = (rs1.getString("apartmentnumber"));
                    }
                    labelHomeNumber.setText(numerdomu);
                    labelStreet.setText(ulica);
                    labelCity.setText(miasto);
                    labelZipcode.setText(kodpocztowy);
                    labelFlatNumber.setText(numermieszkania);


                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sprawdzAction() {   // akcja do przycisku szukania klienta w systemie przy dodawaniu nowej rezerwacji po stronie pracownika
        String szukajPesel = sprawdzPeselId.getText();
        if(szukajPesel.length() != 11)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("HotelXYZ");
            alert.setHeaderText("Status");
            alert.setContentText("Niepoprawna długość numeru PESEL!");
            alert.showAndWait();
        }else {
            ArrayList list = new ArrayList();
            Connection con = DBConnector.getConnection();
            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT *\n" +
                        "FROM personaldata\n" +
                        "WHERE pesel='" + sprawdzPeselId.getText() + "';");
                while (rs.next()) {
                    list.add(rs.getString(1));
                }
            } catch (SQLException e) {
                System.out.println("i zapytanie");
                e.printStackTrace();
            }
            if (list.size() != 1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("HotelXYZ");
                alert.setHeaderText("Status");
                alert.setContentText("Wprowadzono niepoprawny pesel lub nie ma takiego w bazie.");
                alert.showAndWait();

            }
            if(list.size() == 1){
                System.out.println("oki wszedłem");
                loginRezId.setDisable(true);
                hasloRezId.setDisable(true);
                imieRezId.setDisable(true);
                nazwiskoRezId.setDisable(true);
                emailRezId.setDisable(true);
                teleRezId.setDisable(true);
                ulicaRezId.setDisable(true);
                domRezId.setDisable(true);
                mieszkanieRezId.setDisable(true);
                kodRezId.setDisable(true);
                miejscowoscRezId.setDisable(true);
                dzienRezId.setDisable(true);
                miesiacRezId.setDisable(true);
                rokRezId.setDisable(true);
            }
        }
    }


    public void createNewReservation() { //nowa rezerwacja przy cliencie

        if(beginDayId.getSelectionModel().isEmpty() || beginMonthId.getSelectionModel().isEmpty()  || beginYearId.getSelectionModel().isEmpty()  ||
                endDayId.getSelectionModel().isEmpty()  || endMonthId.getSelectionModel().isEmpty()  || endYearId.getSelectionModel().isEmpty()  ||
                roomTypeId.getSelectionModel().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("HotelXYZ");
            alert1.setHeaderText("Nowa rezerwacja");
            alert1.setContentText("Rezerwacja nie została złożona pomyślnie! \n Wprowadź wszystkie dane!");
            alert1.showAndWait();
        }

        String value = "";
        if(roomTypeId.getValue().toString().equals("Pokój jednoosobowy")) {
            value = "1";
        } else if(roomTypeId.getValue().toString().equals("Pokój dwuosobowy")) {
            value = "2";
        } else if(roomTypeId.getValue().toString().equals("Pokój trzyosobowy")) {
            value = "3";
        } else if(roomTypeId.getValue().toString().equals("Apartament - max 4 os.")) {
            value = "4";
        }

        int days = Integer.parseInt(beginDayId.getValue().toString());
        int months = Integer.parseInt(beginMonthId.getValue().toString());
        int years = Integer.parseInt(beginYearId.getValue().toString());
        int daye = Integer.parseInt(endDayId.getValue().toString());
        int monthe = Integer.parseInt(endMonthId.getValue().toString());
        int yeare = Integer.parseInt(endYearId.getValue().toString());

        String startDate = beginYearId.getValue().toString() +"-" +beginMonthId.getValue().toString() + "-"+ beginDayId.getValue().toString();
        String stopDate = endYearId.getValue().toString() +"-" +endMonthId.getValue().toString() + "-"+ endDayId.getValue().toString();
        String roomType = roomTypeId.getValue().toString();
        String choosedRoom = null;
        LocalDate beginDate = LocalDate.of(years, months, days);;
        LocalDate endDate= LocalDate.of(yeare, monthe, daye);

        ArrayList bookedRooms = new ArrayList();
        ArrayList possibleRooms = new ArrayList();

        Connection connection = DBConnector.getConnection();
        System.out.println("Dane wsyłane do zapytania: "+currentLogin+currentPass);
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT\n" +
                    " roomReservation.reservationId,\n" +
                    " room.roomId,\n" +
                    " reservation.beginDate,\n" +
                    " reservation.endDate,\n" +
                    " room.type\n" +
                    "FROM\n" +
                    " reservation\n" +
                    "INNER JOIN roomReservation ON reservation.reservationId = roomReservation.reservationId\n" +
                    "INNER JOIN room ON roomReservation.roomId = room.roomId\n" +
                    "WHERE (  reservation.beginDate < '"+startDate+"' AND reservation.endDate < '"+startDate+"'\n" +
                    "OR reservation.beginDate > '"+endDate+"' AND reservation.endDate > '"+endDate+"')AND room.type = '"+roomType+"';");
            while (rs.next()) {
                bookedRooms.add(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//-ok
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT\n" +
                    "roomId\n" +
                    "FROM room WHERE room.type = '"+roomType+"';");
            while (rs.next()) {
                possibleRooms.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//ma sens
        for(int i=0; i<possibleRooms.size(); i++) {
            choosedRoom = possibleRooms.get(i).toString();
            for(int j=0; j<bookedRooms.size(); j++)
            if(possibleRooms.get(i).toString().equals(bookedRooms.get(j).toString())) {
                possibleRooms.remove(i);
                choosedRoom = null;
                break;
            }
            if(choosedRoom != null){
                break;
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("HotelXYZ");
                alert1.setHeaderText("Nowa rezerwacja");
                alert1.setContentText("Rezerwacja nie została złożona pomyślnie! \n Brak wolnych pokoi szukanego typu w zadanym terminie!");
                alert1.showAndWait();

            }
        }

        if(beginDate.isAfter(endDate)) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("HotelXYZ");
            alert1.setHeaderText("Nowa rezerwacja");
            alert1.setContentText("Rezerwacja nie została złożona pomyślnie! \n Błędnie wprowadzone daty!");
            alert1.showAndWait();

        }  else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("HotelXYZ");
            alert.setHeaderText("Powierdź złożenie nowej rezerwacji. Upenij się, że wprowadzone dane są poprawne");
            alert.setContentText("Data rozpoczęcia pobytu: "+ beginDayId.getValue().toString() +" - " + beginMonthId.getValue().toString() +" - "
                    + beginYearId.getValue().toString()+"\n"+ "Data zakończenia pobytu: "+ endDayId.getValue().toString() +" - " +
                    endMonthId.getValue().toString() +" - " + endYearId.getValue().toString() +"\n" +
                    "Typ pokoju: " + roomTypeId.getValue().toString() + " maksymalna liczba gości: " + value +
                    "\nJeśli wprowadzone dane są poprawne i chcesz złożyć rezerwację naciśnij zarezerwuj. " +
                    "\nUWAGA! Po wykonaniu tej akcji rezerwacja zostanie złożona");

            ButtonType buttonTypeThree = new ButtonType("Zarezerwuj");
            ButtonType buttonTypeCancel = new ButtonType("Wróć", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeThree, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeThree){

                String personalDataIdOfClient = "", currentClientId = "";
                //Connection connection = DBConnector.getConnection();
                System.out.println("Dane wsyłane do zapytania: "+currentLogin+currentPass);
                try {
                    ResultSet rs = connection.createStatement().executeQuery("SELECT personalDataId FROM personalData WHERE login='"+currentLogin+"' AND password = '"+currentPass+"';");
                    while (rs.next()) {
                        personalDataIdOfClient = (rs.getString(1));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    ResultSet rs1 = connection.createStatement().executeQuery("SELECT clientId from client where personalDataId = "
                            +personalDataIdOfClient+";");
                    while (rs1.next()) {
                        currentClientId = (rs1.getString(1));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    connection.createStatement().execute("Insert Into reservation ( beginDate, endDate, clientId) "+
                            "Values ( '"+startDate+"', '"+endDate+"', "+currentClientId+");");

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println(choosedRoom);
                int tmp=0;
                if(choosedRoom.equals(null)) {
                    System.out.println("lalalla");
                } else {
                    tmp = Integer.parseInt(choosedRoom);
                }

                try {
                    String roomIdReserved = "0";
                    int currentReservationId = 0;
                    ResultSet rs = connection.createStatement().executeQuery("SELECT roomId FROM room WHERE roomNumber='"+tmp+"'");
                    while (rs.next()) {
                        roomIdReserved = (rs.getString(1));
                    }

                    ResultSet rs1 = connection.createStatement().executeQuery("SELECT reservationId\n" +
                            "FROM reservation\n" +
                            "WHERE beginDate '"+startDate+"' = AND endDate = '"+stopDate+"' AND clientId = "+currentClientId+";");
                    while (rs1.next()) {
                        currentReservationId = Integer.parseInt(rs.getString(1));
                    }
                    connection.createStatement().execute("Insert into roomReservation("+Integer.parseInt(roomIdReserved)+", "+currentReservationId+") Values (1,17);");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("HotelXYZ");
                alert1.setHeaderText("Nowa rezerwacja");
                alert1.setContentText("Rezerwacja została złożona pomyślnie!");
                alert1.showAndWait();
            }  else {
                alert.close();
            }
        }
    }


    public void zarezerwujBtnAction() {
        String pesel = sprawdzPeselId.getText();
        System.out.println(pesel);

    }

    public void newRezAction() {

        System.out.println(currentLogin + ", " + currentPass);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewReservationPane.fxml"));
        try {
            newReservationPaneId = loader.load();
            LoginScreenController loginScreenController = loader.getController();
            loginScreenController.setCurrentPass(currentPass);
            loginScreenController.setCurrentLogin(currentLogin);

        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPaneId.setCenter(newReservationPaneId);

        System.out.println(currentLogin + ", DZD" + currentPass);

    }

    public void myRezAction() {

        System.out.println("POKAŻ MOJE REZ:" + currentLogin + currentPass);
        //Parent tableViewParent = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyReservationsPane.fxml"));
        try {
//            newReservationPaneId = loader.load();
//            ClientTableController clientTableController = loader.getController();
//            clientTableController.setLogin(currentLogin);
//            clientTableController.setPassword(currentPass);
//            borderPaneId.setCenter(clientTableController.getNewReservationPaneId());
            ClientTableController clientTableController = new ClientTableController();
            newReservationPaneId = loader.load();
            loader.setController(clientTableController);
            clientTableController.setLogin(currentLogin);
            clientTableController.setPassword(currentPass);
            //borderPaneId.setCenter(clientTableController.getNewReservationPaneId());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(loginLabel.getText());

    }

    public void myAccountAction() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyAccountCenterPane.fxml"));
        try {
            myAccountCenterPaneId = loader.load();
            LoginScreenController loginScreenController = loader.getController();
            loginScreenController.currentLogin = currentLogin;
            borderPaneId.setCenter(loginScreenController.myAccountCenterPaneId);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showMyDataClient(){

        //sprawdź odswiezanie
        String imie = null;
        String nazwisko = null;
        String urodziny = null;
        String numertele = null;
        String pesel = null;
        String email = null;
        String miasto = null;
        String ulica = null;
        String numerdomu = null;
        String numermieszkania = null;
        String kodpocztowy = null;
        String IdPersonal1 = null;

        System.out.println(currentLogin);
        Connection con = DBConnector.getConnection();
        try
        {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM personaldata WHERE login='"+currentLogin+"';"); //Tezba zmienić wyszukajPesel gdyż my go ręcznie nie wpisujemy a pobieramy już z zalogowanego konta
            while (rs.next()) {
                IdPersonal1 = (rs.getString(1));
                imie = (rs.getString(2));
                nazwisko = (rs.getString(3));
                pesel = (rs.getString(4));
                urodziny = (rs.getString(5));
                numertele = (rs.getString(6));
                email = (rs.getString(7));
            }
            labelNameClient.setText(imie);
            labelLastnameClient.setText(nazwisko);
            labelPeselClient.setText(pesel);
            labelBirthdateClient.setText(urodziny);
            labelPhoneNumberClient.setText(numertele);
            labelEmailClient.setText(email);


            ResultSet rs1 = con.createStatement().executeQuery("SELECT * FROM addressdata WHERE addressdataid='"+IdPersonal1+"';");
            while (rs1.next()) {
                numerdomu = (rs1.getString("streetnumber"));
                ulica = (rs1.getString("streetname"));
                miasto = (rs1.getString("city"));
                kodpocztowy = (rs1.getString("zipcode"));
                numermieszkania = (rs1.getString("apartmentnumber"));
            }
            labelHomeNumberClient.setText(numerdomu);
            labelStreetClient.setText(ulica);
            labelCityClient.setText(miasto);
            labelZipcodeClient.setText(kodpocztowy);
            labelFlatNumberClient.setText(numermieszkania);


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void logOutAction(Event event) {

        borderPaneId.getCenter().setVisible(true);
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


//--------------------------------------PRACOWNICY--------------------------


    public void editRezAction() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("changeRezPane.fxml"));
        try {
            changePaneId  = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPaneId2.setCenter(changePaneId);
    }

    public void deleteReservationAction() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("deleteRezPane.fxml"));
        try {
            deletePaneId  = loader.load();
            LoginScreenController loginScreenController = loader.getController();
            borderPaneId2.setCenter(loginScreenController.deletePaneId);

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void showRezAction() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("allRezPane.fxml"));
        try {
            newReservationPaneId = loader.load();
            TableController tableController = loader.getController();
            tableController.setLogin(currentLogin);
            borderPaneId2.setCenter(tableController.allRezPaneId);

        } catch (IOException e) {
            e.printStackTrace();
        }


//        FXMLLoader loader = new FXMLLoader(getClass().getResource("allRezPane.fxml"));
//        try {
//            allPaneId  = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        borderPaneId2.setCenter(allPaneId);
    }

    public void dataClientAction(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dataRezKlientPane.fxml"));
        try {
            dataPaneId  = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPaneId2.setCenter(dataPaneId);
    }

    public void checkInAction() {
    }

    public void checkOutAction() {
    }

    public void newRezActionEmployee() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("newRezPane.fxml"));
        try {
            AnchorPaneId1  = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPaneId2.setCenter(AnchorPaneId1);
    }

    public void logOutActionEmployee(ActionEvent actionEvent) {

        borderPaneId2.getCenter().setVisible(true);
        Parent tableViewParent = null;
        try {
            tableViewParent = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information.toSting
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void przyciskUsunAkcja() {

        String rezerwacjaId = reservationDeleteId.getText();
        if(rezerwacjaId.length() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("HotelXYZ");
            alert.setHeaderText("Status");
            alert.setContentText("Wprowadz ponowanie ID rezerwacji");
            alert.showAndWait();
        }else {
            ArrayList list = new ArrayList();
            Connection con = DBConnector.getConnection();
            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT *\n" +
                        "FROM reservation\n" +
                        "WHERE reservationid='" + reservationDeleteId.getText() + "';");
                while (rs.next()) {
                    list.add(rs.getString(1));
                }
            } catch (SQLException e) {
                System.out.println("i zapytanie");
                e.printStackTrace();
            }
            if (list.size() != 1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("HotelXYZ");
                alert.setHeaderText("Status");
                alert.setContentText("Rezerwacja o padanym reserwationid nie istnieje");
                alert.showAndWait();
            }
            if(list.size() == 1) {

                try {
                    con.createStatement().execute("DELETE from reservation where reservationid = '"+reservationDeleteId.getText()+"';\n");
                    con.createStatement().execute("DELETE from roomreservation where reservationid = '"+reservationDeleteId.getText()+"';\n");

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("HotelXYZ");
                    alert.setHeaderText("Status");
                    alert.setContentText("Rezerwacja o padanym reserwationid została usunięta!");
                    alert.showAndWait();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

