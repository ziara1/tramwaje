package Zdarzenia;

import Komunikacja.Pasazer;
import Komunikacja.Przystanek;
import Komunikacja.Tramwaj;

public class PasazerWysiadl extends Zdarzenie{
    private Pasazer pasazer;
    private Tramwaj tramwaj;
    private Przystanek przystanek;

    public PasazerWysiadl(Przystanek przystanek, Tramwaj tramwaj,
                          Pasazer pasazer, int d, int m, Zdarzenie z) {
        super(d, m, z);
        this.pasazer = pasazer;
        this.przystanek = przystanek;
        this.tramwaj = tramwaj;
    }

    @Override
    public String toString() {
        return super.toString() + " " + " Pasazer " + pasazer.getNumer() +
                " wysiadl z tramwaju nr " + tramwaj.getNumer() + " linii " +
                tramwaj.getLinia().getnumerLinii() +  " na przystanku " +
                przystanek.getNazwa();
    }

    public void wykonaj(){
        assert (!przystanek.czyPelny() && tramwaj.czyPusty());
        przystanek.dodajPasazera(pasazer, getMinuta());
        tramwaj.usunPasazera(pasazer);
        System.out.println(this.toString());
    }
}