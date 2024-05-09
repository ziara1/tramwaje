package Zdarzenia;

import Komunikacja.Pasazer;
import Komunikacja.Przystanek;
import Komunikacja.Tramwaj;
import Symulacja.Losowanie;

public class PasazerWsiadl extends Zdarzenie{
    private Pasazer pasazer;
    private Tramwaj tramwaj;
    private Przystanek przystanek;
    private int kierunek;
    
    public PasazerWsiadl(Przystanek przystanek, Tramwaj tramwaj,
                         int dzien, int minuta, Zdarzenie next, int kierunek) {
        super(dzien, minuta, next);
        // wsiada pierwszy pasazer z brzegu
        this.pasazer = przystanek.pierwszyPasazer();
        this.przystanek = przystanek;
        this.tramwaj = tramwaj;
        this.kierunek = kierunek;
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
        // taki ktory jest na trasie, ale nie jest aktualnym przystankiem
        int index = tramwaj.getLinia().znajdzIndeks(przystanek);
        int cel = 0;
        if (kierunek == 1){
            cel = Losowanie.losuj(index + 1, tramwaj.getLinia().getDlugoscTrasy() - 1);
        }
        else{
            cel = Losowanie.losuj(0, index - 1);
        }
        pasazer.ustawCel(tramwaj.getLinia().getPrzystanek(cel));
    }

    public void wykonaj(){
        assert (!tramwaj.czyPelny() && przystanek.czyPusty());
        tramwaj.dodajPasazera(pasazer);
        przystanek.usunPasazera(getMinuta());
        wylosujPrzystanek();
        System.out.println(this.toString());
    }
}
