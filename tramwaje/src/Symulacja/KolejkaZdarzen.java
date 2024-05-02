package Symulacja;

import Zdarzenia.Zdarzenie;

public interface KolejkaZdarzen {
    public void dodajZdarzenie(Zdarzenie z);
    public void getZdarzenie();
    public boolean czyPusta();
}
