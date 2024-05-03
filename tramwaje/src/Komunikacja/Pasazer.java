package Komunikacja;

public class Pasazer {
    private Przystanek bliskiPrzystanek;
    private int numer;
    private Przystanek celPodrozy;

    public Pasazer(Przystanek przystanek, int numer) {
        this.bliskiPrzystanek = przystanek;
        this.numer = numer;
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
}
