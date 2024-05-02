package Komunikacja;

abstract public class Pojazd {
    private int numerBoczny;
    private Linia linia;

    public Pojazd(int numerBoczny, Linia linia) {
        this.numerBoczny = numerBoczny;
        this.linia = linia;
    }
    public int getNumer(){
        return numerBoczny;
    }
    public Linia getLinia(){
        return linia;
    }
}
