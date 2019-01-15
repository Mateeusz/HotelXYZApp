package sample;

public class ClientReservationToDisplay {

    String numerRezerwacji, poczatek, koniec, numerPokoju, typ;

    public ClientReservationToDisplay(String numerRezerwacji, String poczatek, String koniec, String numerPokoju, String typ) {
        this.numerRezerwacji = numerRezerwacji;
        this.poczatek = poczatek;
        this.koniec = koniec;
        this.numerPokoju = numerPokoju;
        this.typ = typ;
    }

    public String getNumerRezerwacji() {
        return numerRezerwacji;
    }

    public void setNumerRezerwacji(String numerRezerwacji) {
        this.numerRezerwacji = numerRezerwacji;
    }

    public String getPoczatek() {
        return poczatek;
    }

    public void setPoczatek(String poczatek) {
        this.poczatek = poczatek;
    }

    public String getKoniec() {
        return koniec;
    }

    public void setKoniec(String koniec) {
        this.koniec = koniec;
    }

    public String getNumerPokoju() {
        return numerPokoju;
    }

    public void setNumerPokoju(String numerPokoju) {
        this.numerPokoju = numerPokoju;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }
}
