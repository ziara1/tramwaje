package Komunikacja;

import Symulacja.ListaPasazerow;

public class Przystanek {
    private String nazwa;
    private int pojemnosc;
    private int zajete;
    private ListaPasazerow head;
    private ListaPasazerow tail;

    public Przystanek(String nazwa, int pojemnosc) {
        this.nazwa = nazwa;
        this.pojemnosc = pojemnosc;
        zajete = 0;
        tail = new ListaPasazerow(null, null);
        head = new ListaPasazerow(null, tail);
    }
    public String getNazwa(){
        return nazwa;
    }
    public void dodajPasazera(Pasazer p){
        if (pojemnosc == zajete)
            return;
        head.dodajPasazera(new ListaPasazerow(p, null));
        zajete++;
    }
    public boolean czyPelny(){
        return zajete >= pojemnosc;
    }
    public boolean czyPusty(){
        return head.getNext() == tail;
    }
    public Pasazer pierwszyPasazer(){
        return head.getNext().getVal();
    }
    public void usunPasazera(){
        if (!this.czyPusty()) {
            head.usunNastepnego();
            zajete--;
        }
    }
    public int ileZajetych(){
        return zajete;
    }
    public ListaPasazerow getHead(){
        return head;
    }
    public ListaPasazerow getTail(){
        return tail;
    }
    public void oproznij(){
        zajete = 0;
        head = new ListaPasazerow(null, tail);
    }
}
