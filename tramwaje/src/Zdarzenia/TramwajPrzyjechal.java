package Zdarzenia;

import Komunikacja.Pasazer;
import Komunikacja.Przystanek;
import Komunikacja.Tramwaj;
import Symulacja.ListaPasazerow;

public class TramwajPrzyjechal extends Zdarzenie{
    private Tramwaj tramwaj;
    private Przystanek przystanek;

    public TramwajPrzyjechal(Przystanek przystanek, Tramwaj tramwaj,
                         int dzien, int minuta, Zdarzenie next) {
        super(dzien, minuta, next);
        this.przystanek = przystanek;
        this.tramwaj = tramwaj;
    }

    @Override
    public String toString() {
        return super.toString() + " Tramwaj nr " + tramwaj.getNumer() + " linii "
                + tramwaj.getLinia().getnumerLinii() +  " " +
                " zatrzymal sie na przystanku " + przystanek.getNazwa();
    }

    public void wykonaj(){
        ListaPasazerow p = przystanek.getHead().getNext();
        ListaPasazerow t = przystanek.getHead().getNext();
       while (t != tramwaj.getTail() && !przystanek.czyPelny()){
            if (t.getVal().getCelPodrozy() == przystanek){
                (new PasazerWysiadl(przystanek, tramwaj, t.getVal(),
                        getDzien(), getMinuta(), null)).wykonaj();
            }
           t = t.getNext();
       }
       while (p != przystanek.getTail() && !tramwaj.czyPelny()){
           (new PasazerWsiadl(przystanek, tramwaj, getDzien(), getMinuta(), null)).wykonaj();
           p = p.getNext();
       }
    }
}