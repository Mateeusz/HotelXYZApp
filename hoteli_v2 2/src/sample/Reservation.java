package sample;

public class Reservation {

    String numerRezerwacji, poczatek, koniec, numerPokoju, typ, imie, nazwisko;

    public Reservation(String numerRezerwacji, String poczatek, String koniec, String numerPokoju, String typ) {
        this.numerRezerwacji = numerRezerwacji;
        this.poczatek = poczatek;
        this.koniec = koniec;
        this.numerPokoju = numerPokoju;
        this.typ = typ;
        this.imie = imie;
        this.nazwisko = nazwisko;
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

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
}
