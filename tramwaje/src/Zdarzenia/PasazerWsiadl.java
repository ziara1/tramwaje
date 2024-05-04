package Zdarzenia;

import Komunikacja.Pasazer;
import Komunikacja.Przystanek;
import Komunikacja.Tramwaj;
import Symulacja.Losowanie;

public class PasazerWsiadl extends Zdarzenie{
    private Pasazer pasazer;
    private Tramwaj tramwaj;
    private Przystanek przystanek;
    
    public PasazerWsiadl(Przystanek przystanek, Tramwaj tramwaj,
                         int dzien, int minuta, Zdarzenie next) {
        super(dzien, minuta, next);
        this.pasazer = przystanek.pierwszyPasazer();
        this.przystanek = przystanek;
        this.tramwaj = tramwaj;
    }

    @Override
    public String toString() {
        return super.toString() + " " + " Pasazer " + pasazer.getNumer() +
                " wsiadl do tramwaju nr " + tramwaj.getNumer() + " linii "
                + tramwaj.getLinia().getnumerLinii() +  " na przystanku " + przystanek.getNazwa()
                + " z zamiarem dojechania na przystanek " + pasazer.getCelPodrozy().getNazwa();
    }

    public void wylosujPrzystanek(){
        int index = tramwaj.getLinia().znajdzIndeks(przystanek);
        Losowanie losujCel = new Losowanie();
        int cel = losujCel.losuj(0, tramwaj.getLinia().getDlugoscTrasy() - 2);
        if (cel >= index)
            cel++;
        pasazer.ustawCel(tramwaj.getLinia().getPrzystanek(cel));
    }

    public void wykonaj(){
        assert (!tramwaj.czyPelny() && przystanek.czyPusty());
        tramwaj.dodajPasazera(pasazer);
        przystanek.usunPasazera();
        wylosujPrzystanek();
        System.out.println(this.toString());
    }
}
// przewd wywolaniem trzeba sprawdzac czy pelny pusty?