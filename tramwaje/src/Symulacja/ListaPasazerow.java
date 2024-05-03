package Symulacja;

import Komunikacja.Pasazer;

public class ListaPasazerow {
    private Pasazer val;
    private ListaPasazerow next;

    public ListaPasazerow(Pasazer pasazer, ListaPasazerow next) {
        this.val = pasazer;
        this.next = next;
    }
    public void dodajPasazera(ListaPasazerow p){
        p.next = this.next;
        this.next = p;
    }
    public void usunNastepnego(){
        this.next = this.next.next;
    }
    public ListaPasazerow getNext(){
        return this.next;
    }
    public Pasazer getVal(){
        return this.val;
    }
}
