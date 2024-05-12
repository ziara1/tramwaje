package Zdarzenia;

import Komunikacja.Linia;
import Komunikacja.Pasazer;
import Komunikacja.Przystanek;
import Komunikacja.Tramwaj;
import Symulacja.Losowanie;

public class PasazerWsiadl extends Zdarzenie{
    private Pasazer pasazer;
    private Tramwaj tramwaj;
    private Przystanek przystanek;
    private int kierunek;
    private int index;
    
    public PasazerWsiadl(Przystanek przystanek, Tramwaj tramwaj,
                         int d, int m, Zdarzenie z, int kierunek, int index) {
        super(d, m, z);
        // wsiada pierwszy pasazer z brzegu
        this.pasazer = przystanek.pierwszyPasazer();
        this.przystanek = przystanek;
        this.tramwaj = tramwaj;
        this.kierunek = kierunek;
        this.index = index;
    }

    @Override
    public String toString() {
        return super.toString() + " " + " Pasazer " + pasazer.getNumer() +
                " wsiadl do tramwaju nr " + tramwaj.getNumer() + " linii "
                + tramwaj.getLinia().getnumerLinii() +  " na przystanku " +
                przystanek.getNazwa() + " z zamiarem dojechania na przystanek "
                + pasazer.getCelPodrozy().getNazwa();
    }

    public void wylosujPrzystanek(){ // losuje przystanek docelowy dla pasazera
        // indeks aktualnego przystanku
        Linia l = tramwaj.getLinia();
        int cel = 0;
        // w zaleznosci od tego w ktora strone jedzie tramwaj, wybieramy
        // przedzial z ktorego bedziemy losowac przystanek (przed/po tramwaju)
        if (kierunek == 1)
            cel = Losowanie.losuj(index + 1, l.getDlugoscTrasy() - 1);
        else
            cel = Losowanie.losuj(0, index - 1);

        pasazer.ustawCel(l.getPrzystanek(cel));
    }

    public void wykonaj(){
        assert (!tramwaj.czyPelny() && przystanek.czyPusty());
        tramwaj.dodajPasazera(pasazer);
        przystanek.usunPasazera(getMinuta());
        wylosujPrzystanek();
        System.out.println(this.toString());
    }
}
