package Komunikacja;

public class Pasazer {
    private Przystanek bliskiPrzystanek;
    private int numer;
    private Przystanek celPodrozy;
    private int czasCzekania; // ile czasu w sumie czekal
    private int liczbaCzekan; // ile razy pasazer czekal

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
    // po wywolaniu zeruje, bo wywoluje sie tylko raz na koniec dnia
    public int getCzasCzekania(){
        int res = czasCzekania;
        czasCzekania = 0;
        return res;
    }
    public void dodajCzasCzekania(int czasCzekania){
        this.czasCzekania += czasCzekania;
    }
    // po wywolaniu zeruje, bo wywoluje sie tylko raz na koniec dnia
    public int getLiczbaCzekan(){
        int res = liczbaCzekan;
        liczbaCzekan = 0;
        return res;
    }
    public void zwiekszLiczbeCzekan(){
        liczbaCzekan++;
    }
}
