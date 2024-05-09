package Zdarzenia;

import Komunikacja.Pasazer;
import Komunikacja.Przystanek;
import Komunikacja.Tramwaj;
import Symulacja.ListaPasazerow;

public class TramwajPrzyjechal extends Zdarzenie{
    private Tramwaj tramwaj;
    private Przystanek przystanek;
    private int liczbaPrzejazdow;
    private int index;
    private int kierunek;

    public TramwajPrzyjechal(Przystanek przystanek, Tramwaj tramwaj,
                         int dzien, int minuta, Zdarzenie next, int index, int kierunek) {
        super(dzien, minuta, next);
        this.przystanek = przystanek;
        this.tramwaj = tramwaj;
        liczbaPrzejazdow = 0;
        this.index = index;
        this.kierunek = kierunek;
    }

    @Override
    public String toString() {
        return super.toString() + " Tramwaj nr " + tramwaj.getNumer() +
                " linii " + tramwaj.getLinia().getnumerLinii() +  " " +
                " zatrzymal sie na przystanku " + przystanek.getNazwa();
    }

    public void wykonaj(){
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
            int dlugoscTrasy = tramwaj.getLinia().getDlugoscTrasy();
            if ((index == 0 && kierunek == -1) ||
                    (index == dlugoscTrasy - 1 && kierunek == 1)){
                return; // jesli tramwaj dojechal do konca trasy
            }
            ListaPasazerow p = przystanek.getHead().getNext();
            while (p != przystanek.getTail() && !tramwaj.czyPelny()) {
                (new PasazerWsiadl(przystanek,
                        tramwaj, getDzien(), getMinuta(), null, kierunek)).wykonaj();
                p = p.getNext();
                liczbaPrzejazdow++; // ile osob wsiadlo w tym zdarzeniu
            }
        }
    }

    public int getLiczbaPrzejazdow(){
        return liczbaPrzejazdow;
    }
}