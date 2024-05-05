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
        if (minuta % 60 < 10)
            return "Dzien " + dzien + ". godzina " +
                    minuta / 60 + ":0" + minuta % 60 + ".";
        return "Dzien " + dzien + ". godzina " +
                minuta / 60 + ":" + minuta % 60 + ".";
    }

    public int getCzas(){
        return dzien * 24 * 60 + minuta;
    }
    public int getDzien(){
        return dzien;
    }
    public int getMinuta(){
        return minuta;
    }
    public Zdarzenie getNext() {
        return next;
    }
    public void setNext(Zdarzenie next) {
        this.next = next;
    }
    public void wykonaj(){
         System.out.println("usun to pozniej");
    }
}
