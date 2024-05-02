package Komunikacja;

import Symulacja.ListaPasazerow;

public class Tramwaj extends Pojazd {
    private int pojemnosc;
    private int zajete;
    private ListaPasazerow head;
    private ListaPasazerow tail;

    public Tramwaj(int pojemnosc, int numerBoczny, Linia linia){
        super(numerBoczny, linia);
        this.pojemnosc = pojemnosc;
        zajete = 0;
        tail = new ListaPasazerow(null, null);
        head = new ListaPasazerow(null, tail);
    }
    public void dodajPasazera(Pasazer p){
        if (pojemnosc == zajete)
            return;
        head.dodajPasazera(new ListaPasazerow(p, null));
        zajete++;
    }
}
