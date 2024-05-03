package Komunikacja;

import Symulacja.ListaPasazerow;

abstract public class Pojazd {
    private int numerBoczny;
    private Linia linia;
    private ListaPasazerow head;
    private ListaPasazerow tail;

    public Pojazd(int numerBoczny, Linia linia) {
        this.numerBoczny = numerBoczny;
        this.linia = linia;
        tail = new ListaPasazerow(null, null);
        head = new ListaPasazerow(null, tail);
    }
    public int getNumer(){
        return numerBoczny;
    }
    public Linia getLinia(){
        return linia;
    }
    public void dodajPasazera(Pasazer p){
        head.dodajPasazera(new ListaPasazerow(p, null));
    }
    public boolean czyPusty(){
        return head.getNext() == tail;
    }
    //usuwa puerwszwego z brzegu
    public void usunPasazera(){
        if (!this.czyPusty())
            head.usunNastepnego();
    }
    public void usunPasazera(Pasazer p){
        ListaPasazerow find = head;
        while (find.getNext().getVal() != p){
            find = find.getNext();
        }
        find.usunNastepnego();
    }
    public Pasazer pierwszyPasazer(){
        return head.getNext().getVal();
    }
    public ListaPasazerow getHead(){
        return head;
    }
    public ListaPasazerow getTail(){
        return tail;
    }
}
// mozna przelozyc tu zajete