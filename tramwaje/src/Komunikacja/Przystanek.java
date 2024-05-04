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
    public void dodajPasazera(Pasazer p, int czas){
        if (pojemnosc == zajete)
            return;
        head.dodajPasazera(new ListaPasazerow(p, null));
        zajete++;
        p.dodajCzasCzekania(-czas);
        p.zwiekszLiczbeCzekan();
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
    public void usunPasazera(int czas){
        if (!this.czyPusty()) {
            pierwszyPasazer().dodajCzasCzekania(czas);
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
        for (int i = 0; i < zajete; i++){
            usunPasazera(1380);
        }
    }
}
