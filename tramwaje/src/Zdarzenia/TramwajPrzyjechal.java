package Zdarzenia;

import Komunikacja.Przystanek;
import Komunikacja.Tramwaj;
import Symulacja.ListaPasazerow;

public class TramwajPrzyjechal extends Zdarzenie{
    private Tramwaj tramwaj;
    private Przystanek przystanek;
    private int liczbaPrzejazdow;   // ile osob wsiadlo w tym zdarzeniu
    private int index;              // index aktualnego przystanku
    private int kierunek;       // kierunek w ktorym jedzie tramwaj (1 lub -1)

    public TramwajPrzyjechal(Przystanek przystanek, Tramwaj tramwaj, int d,
                             int m, Zdarzenie z, int index, int kierunek) {
        super(d, m, z);
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
            return; // znaczy ze tramwaj dojechal do konca trasy
        }
        ListaPasazerow p = przystanek.getHead().getNext();
        // wsiadaja pierwsi z brzegu pasazerowie z przystanku
        while (p != przystanek.getTail() && !tramwaj.czyPelny()) {
            (new PasazerWsiadl(przystanek, tramwaj, getDzien(),
                    getMinuta(), null, kierunek, index)).wykonaj();
            p = p.getNext();
            liczbaPrzejazdow++; // ile osob wsiadlo w tym zdarzeniu
        }
    }

    public int getLiczbaPrzejazdow(){
        return liczbaPrzejazdow;
    }
}