package Zdarzenia;

import Komunikacja.Pasazer;
import Komunikacja.Przystanek;
import Komunikacja.Tramwaj;

public class PasazerWsiadl extends Zdarzenie{
    private Pasazer pasazer;
    private Tramwaj tramwaj;
    private Przystanek przystanek;
    
    public PasazerWsiadl(Pasazer pasazer, Przystanek przystanek, Tramwaj tramwaj,
                         int dzien, int minuta, Zdarzenie next) {
        super(dzien, minuta, next);
        this.pasazer = pasazer;
        this.przystanek = przystanek;
        this.tramwaj = tramwaj;
    }

    @Override
    public String toString() {
        return super.toString() + " " + " Pasazer " + pasazer.getNumer() +
                " wsiadl do tramwaju nr " + tramwaj.getNumer() + " linii "
                + tramwaj.getLinia().getnumerLinii() +  " na przystanku " + przystanek.getNazwa();
    }
}
