package Symulacja;

import Zdarzenia.KolejkaZdarzen;
import Zdarzenia.Zdarzenie;

public class ListaZdarzen implements KolejkaZdarzen {
    private Zdarzenie head;
    private Zdarzenie tail;

    public ListaZdarzen() {
        this.tail = new Zdarzenie(Integer.MAX_VALUE, Integer.MAX_VALUE, null);
        this.head = new Zdarzenie(0, 0, this.tail);
    }
    public void dodajZdarzenie(Zdarzenie z){ // dodaje zdarzenie wedlug czasu
        Zdarzenie current = head;
        while (current.getNext() != tail &&
                current.getNext().getCzas() <= z.getCzas()) {
            current = current.getNext();
        }
        z.setNext(current.getNext());
        current.setNext(z);
    }
    public Zdarzenie getZdarzenie(){ // zdejmuje pierwsze zdarzenie z kolejki
        assert(!czyPusta());
        Zdarzenie z = head.getNext();
        head.setNext(z.getNext());
        return z;
    }
    public boolean czyPusta(){
        return head.getNext() == tail;
    }
    public Zdarzenie getHead(){
        return head;
    }
}
