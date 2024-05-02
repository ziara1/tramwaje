package Zdarzenia;

public class Zdarzenie {
    private int dzien;
    private int minuta;
    private Zdarzenie next;

    public Zdarzenie(int dzien, int minuta, Zdarzenie next) {
        this.dzien = dzien;
        this.minuta = minuta;
        this.next = next;
    }

    @Override
    public String toString(){
        return "Dzien " + dzien + "., godzina " + minuta / 60 + ":" + minuta % 60 + ".";
    }

    public int getCzas(){
        return dzien * 24 * 60 + minuta;
    }
    public Zdarzenie getNext() {
        return next;
    }
    public void setNext(Zdarzenie next) {
        this.next = next;
    }
}
/*
pasazer wsiadl
pasazer wysiadl
pasazer przyszedl na przystanek

takie cos ze kaazdy trwmwaj i kazdy przystanek ma swoja liste pasazerow linked lisste
i zmienna ktora trzyma ilosc miejsc zapelnionych
 */