package Komunikacja;

public class Linia {
    private int numerLinii;
    private Trasa trasa;
    private int liczbaTramwajow;
    private Tramwaj tramwaje[];

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
}
