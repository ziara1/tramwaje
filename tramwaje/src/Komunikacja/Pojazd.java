package Komunikacja;

import Symulacja.ListaPasazerow;

abstract public class Pojazd {
    private int numerBoczny;
    private Linia linia;
    private ListaPasazerow head;
    private ListaPasazerow tail;
    private int zajete;
    private int kierunek;

    public Pojazd(int numerBoczny, Linia linia) {
        this.numerBoczny = numerBoczny;
        this.linia = linia;
        this.zajete = 0;
        tail = new ListaPasazerow(null, null);
        head = new ListaPasazerow(null, tail);
        kierunek = 1;
    }
    public int getNumer(){
        return numerBoczny;
    }
    public Linia getLinia(){
        return linia;
    }
    public void dodajPasazera(Pasazer p){
        head.dodajPasazera(new ListaPasazerow(p, null));
        zajete++;
    }
    public void usunPasazera(Pasazer p){
        if (zajete > 0) {
            ListaPasazerow find = head;
            while (find.getNext().getVal() != p) {
                find = find.getNext();
            }
            find.usunNastepnego();
            zajete--;
        }
    }
    public ListaPasazerow getHead(){
        return head;
    }
    public ListaPasazerow getTail(){
        return tail;
    }
    public int getCzasPostoju(){ // czas w ostatniej parze trasy
        return linia.getCzasPrzystanku(linia.getDlugoscTrasy() - 1);
    }
    public void oproznij(){
        head = new ListaPasazerow(null, tail);
        zajete = 0;
    }
    public int getZajete(){
        return zajete;
    }
    public boolean czyPusty(){
        return zajete == 0;
    }
    public int getKierunek(){
        return kierunek;
    }
    public void zmienKierunek(){
        kierunek *= -1;
    }
    public void ustawKierunek(int k){
        if (k < 0)
            kierunek = -1;
        else
            kierunek = 1;
    }
}
