package Komunikacja;

public class ParaTrasy {
    private Przystanek przystanek;
    private int czas;

    public ParaTrasy(Przystanek przystanek, int czas) {
        this.przystanek = przystanek;
        this.czas = czas;
    }

    public Przystanek getPrzystanek() {
        return przystanek;
    }

    public int getCzas() {
        return czas;
    }
}
