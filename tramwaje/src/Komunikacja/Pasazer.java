package Komunikacja;

public class Pasazer {
    private Przystanek bliskiPrzystanek;
    private int numer;

    public Pasazer(Przystanek przystanek, int numer) {
        this.bliskiPrzystanek = przystanek;
        this.numer = numer;
    }
    public int getNumer(){
        return numer;
    }
}
