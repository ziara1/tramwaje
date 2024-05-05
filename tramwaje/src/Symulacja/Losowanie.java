package Symulacja;

import java.util.Random;

public class Losowanie {

    public static int losuj(int dolna, int gorna) {
        Random rand = new Random();
        return rand.nextInt(gorna - dolna + 1) + dolna;
    }
}
