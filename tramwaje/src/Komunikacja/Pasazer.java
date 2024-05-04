package Komunikacja;

public class Pasazer {
    private Przystanek bliskiPrzystanek;
    private int numer;
    private Przystanek celPodrozy;
    private int czasCzekania;
    private int liczbaCzekan;

    public Pasazer(Przystanek przystanek, int numer) {
        this.bliskiPrzystanek = przystanek;
        this.numer = numer;
        czasCzekania = 0;
    }
    public int getNumer(){
        return numer;
    }
    public Przystanek getPrzystanek(){
        return bliskiPrzystanek;
    }
    public void ustawCel(Przystanek przystanek){
        celPodrozy = przystanek;
    }
    public Przystanek getCelPodrozy(){
        return celPodrozy;
    }
    public int getCzasCzekania(){
        return czasCzekania;
    }
    public void dodajCzasCzekania(int czasCzekania){
        this.czasCzekania += czasCzekania;
    }
    public int getLiczbaCzekan(){
        return liczbaCzekan;
    }
    public void zwiekszLiczbeCzekan(){
        liczbaCzekan++;
    }
}
