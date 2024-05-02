package Komunikacja;

public class Trasa {
    private ParaTrasy trasa[];
    private int dlugoscTrasy;

    public Trasa(int dlugoscTrasy) {
        this.dlugoscTrasy = dlugoscTrasy;
        trasa = new ParaTrasy[dlugoscTrasy];
    }
    public void setTrasa(Przystanek przystanek, int czas, int i){
        trasa[i] = new ParaTrasy(przystanek, czas);
    }
}
