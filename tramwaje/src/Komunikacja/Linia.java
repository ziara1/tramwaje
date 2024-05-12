package Komunikacja;

public class Linia {
    private int numerLinii;
    private Trasa trasa;
    private int liczbaTramwajow;
    private Tramwaj[] tramwaje;

    public Linia(int liczbaTramwajow, int dlugoscTrasy, int numerLinii) {
        this.liczbaTramwajow = liczbaTramwajow;
        this.trasa = new Trasa(dlugoscTrasy);
        this.numerLinii = numerLinii;
        this.tramwaje = new Tramwaj[liczbaTramwajow];
    }
    public void setTramwaje(Tramwaj t, int i){
        this.tramwaje[i] = t;
    }
    public void setTrasa(Przystanek przystanek, int czas, int i){
        this.trasa.setTrasa(przystanek, czas, i);
    }
    public int getnumerLinii() {
        return numerLinii;
    }
    public Tramwaj getTramwaj(int i){
        return tramwaje[i];
    }
    public int getLiczbaTramwajow(){
        return liczbaTramwajow;
    }
    public int getCzasTrasy(){
        int res = 0;
        for (int i = 0; i < trasa.getDlugoscTrasy(); i++){
            res += trasa.getPara(i).getCzas();
        }
        return res * 2; // bo trasa w ta i z powrotem
    }
    public int getDlugoscTrasy(){
        return trasa.getDlugoscTrasy();
    }
    public Przystanek getPrzystanek(int i){ // i-ty przystanek
        return trasa.getPara(i).getPrzystanek();
    }
    public int getCzasPrzystanku(int i){ // czas miedzy i-tym a i+1-ym
        return trasa.getPara(i).getCzas();
    }
}
