package Komunikacja;

import Symulacja.ListaPasazerow;

public class Tramwaj extends Pojazd {
    private int pojemnosc;

    public Tramwaj(int pojemnosc, int numerBoczny, Linia linia){
        super(numerBoczny, linia);
        this.pojemnosc = pojemnosc;
    }
    public void dodajPasazera(Pasazer p){
        if (czyPelny())
            return;
        super.dodajPasazera(p);
    }
    public boolean czyPelny(){
        return getZajete() >= pojemnosc;
    }
}
