package Zdarzenia;

import Komunikacja.Pasazer;
import Komunikacja.Przystanek;
import Komunikacja.Tramwaj;
import Symulacja.ListaPasazerow;

public class TramwajPrzyjechal extends Zdarzenie{
    private Tramwaj tramwaj;
    private Przystanek przystanek;
    private int liczbaPrzejazdow;

    public TramwajPrzyjechal(Przystanek przystanek, Tramwaj tramwaj,
                         int dzien, int minuta, Zdarzenie next) {
        super(dzien, minuta, next);
        this.przystanek = przystanek;
        this.tramwaj = tramwaj;
        liczbaPrzejazdow = 0;
    }

    @Override
    public String toString() {
        return super.toString() + " Tramwaj nr " + tramwaj.getNumer() +
                " linii " + tramwaj.getLinia().getnumerLinii() +  " " +
                " zatrzymal sie na przystanku " + przystanek.getNazwa();
    }

    public void wykonaj(){
        ListaPasazerow p = przystanek.getHead().getNext();
        ListaPasazerow t = tramwaj.getHead().getNext();
        System.out.println(toString());
        // jesli jest po 23:00 to pasazerowie nie jezdza
        // czyli dajemy tylko komunikat ze tramwaj przyjechal
        if (getMinuta() <= 1380) {
            while (t != tramwaj.getTail() && !przystanek.czyPelny()) {
                // szukamy pasazerow ktorzy chca wysiasc na tym przystanku
                if (t.getVal().getCelPodrozy() == przystanek) {
                    (new PasazerWysiadl(przystanek, tramwaj, t.getVal(),
                            getDzien(), getMinuta(), null)).wykonaj();
                }
                t = t.getNext();
            }
            int index = tramwaj.getLinia().znajdzIndeks(przystanek);
            int dlugoscTrasy = tramwaj.getLinia().getDlugoscTrasy();
            if ((index == 0 && tramwaj.getKierunek() == -1) ||
                    (index == dlugoscTrasy - 1 && tramwaj.getKierunek() == 1)){
                return; // jesli tramwaj dojechal do konca trasy
            }
            while (p != przystanek.getTail() && !tramwaj.czyPelny()) {
                (new PasazerWsiadl(przystanek,
                        tramwaj, getDzien(), getMinuta(), null)).wykonaj();
                p = p.getNext();
                liczbaPrzejazdow++; // ile osob wsiadlo w tym zdarzeniu
            }
        }
    }

    public int getLiczbaPrzejazdow(){
        return liczbaPrzejazdow;
    }
}