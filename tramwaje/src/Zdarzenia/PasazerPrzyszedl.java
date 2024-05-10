package Zdarzenia;

import Komunikacja.Pasazer;
import Komunikacja.Przystanek;

public class PasazerPrzyszedl extends Zdarzenie{ //przyszedl na przystanek
    private Pasazer pasazer;
    private Przystanek przystanek;

    public PasazerPrzyszedl(Przystanek przystanek, Pasazer pasazer,
                            int d, int m, Zdarzenie z) {
        super(d, m, z);
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
        przystanek.dodajPasazera(pasazer, getMinuta());
        System.out.println(this.toString());
    }
}