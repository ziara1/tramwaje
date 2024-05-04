package Komunikacja;

import Symulacja.ListaPasazerow;

public class Tramwaj extends Pojazd {
    private int pojemnosc;
    private int zajete;

    public Tramwaj(int pojemnosc, int numerBoczny, Linia linia){
        super(numerBoczny, linia);
        this.pojemnosc = pojemnosc;
        zajete = 0;
    }
    public void dodajPasazera(Pasazer p){
        if (pojemnosc == zajete)
            return;
        super.dodajPasazera(p);
        zajete++;
    }
    public void usunPasazera(){
        if (zajete > 0){
            super.usunPasazera();
            zajete--;
        }
    }
    public void usunPasazera(Pasazer p){
        if (zajete > 0){
            super.usunPasazera(p);
            zajete--;
        }
    }
    public boolean czyPelny(){
        return zajete >= pojemnosc;
    }
    public int ileZajetych(){
        return zajete;
    }
    public void oproznij(){
        super.oproznij();
        zajete = 0;
    }
}
