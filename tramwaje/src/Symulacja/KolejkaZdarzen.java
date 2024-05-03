package Symulacja;

import Zdarzenia.Zdarzenie;

public interface KolejkaZdarzen {
    public void dodajZdarzenie(Zdarzenie z);
    public Zdarzenie getZdarzenie();
    public boolean czyPusta();
}
