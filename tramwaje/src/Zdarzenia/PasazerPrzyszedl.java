package Zdarzenia;

import Komunikacja.Pasazer;
import Komunikacja.Przystanek;

public class PasazerPrzyszedl extends Zdarzenie{
    private Pasazer pasazer;
    private Przystanek przystanek;

    public PasazerPrzyszedl(Przystanek przystanek, Pasazer pasazer,
                         int dzien, int minuta, Zdarzenie next) {
        super(dzien, minuta, next);
        this.pasazer = pasazer;
        this.przystanek = przystanek;
    }

    @Override
    public String toString() {
        return super.toString() + " " + " Pasazer " + pasazer.getNumer() +
                 " przyszedl na przystanek " + przystanek.getNazwa();
    }

    public void wykonaj(){
        assert (!przystanek.czyPelny());
        przystanek.dodajPasazera(pasazer);
        System.out.println(this.toString());
    }
}