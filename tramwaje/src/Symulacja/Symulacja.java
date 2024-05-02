package Symulacja;

import Komunikacja.Przystanek;
import Komunikacja.Pasazer;
import Komunikacja.Linia;
import Komunikacja.Tramwaj;

import java.util.Scanner;

public class Symulacja {
    private Pasazer pasazerowie[];
    private Przystanek przystanki[];
    private Linia linie[];
    private int liczbaDni;
    private int pojemnoscPrzystanku;
    private int pojemnoscTramwaju;
    private int liczbaLinii;
    private int liczbaPrzystankow;
    private int liczbaPasazerow;


    public void wczytajDane(){

        Scanner scanner = new Scanner(System.in);
        liczbaDni = scanner.nextInt();
        pojemnoscPrzystanku = scanner.nextInt();
        liczbaPrzystankow = scanner.nextInt();
        przystanki = new Przystanek[liczbaPrzystankow];

        for (int i = 0; i < liczbaPrzystankow; i++){
            String nazwa = scanner.next();
            przystanki[i] = new Przystanek(nazwa, pojemnoscPrzystanku);
        }

        liczbaPasazerow = scanner.nextInt();
        pasazerowie = new Pasazer[liczbaPasazerow];
        Losowanie losujPrzystanek = new Losowanie();

        for (int i = 0; i < liczbaPasazerow; i++){
            int x = losujPrzystanek.losuj(0, liczbaPrzystankow - 1);
            pasazerowie[i] = new Pasazer(przystanki[x], i);
        }

        pojemnoscTramwaju = scanner.nextInt();
        liczbaLinii = scanner.nextInt();
        linie = new Linia[liczbaLinii];
        int numerPojazdu = 0;
        for (int i = 0; i < liczbaLinii; i++){
            int liczbaTramwajowLinii = scanner.nextInt();
            int dlugoscTrasy = scanner.nextInt();
            linie[i] = new Linia(liczbaTramwajowLinii, dlugoscTrasy, i);

            for (int j = 0; j < liczbaTramwajowLinii; j++){
                linie[i].setTramwaje((new Tramwaj(pojemnoscTramwaju, numerPojazdu++, linie[i])), j);
            }

            for (int j = 0; j < dlugoscTrasy; j++){
                String nazwa = scanner.next();
                int czas = scanner.nextInt();
                int index = 0;
                for (int k = 0; k < liczbaPrzystankow; k++){
                    if (nazwa.equals(przystanki[k].getNazwa()))
                        index = k;
                }

                linie[i].setTrasa(przystanki[index], czas, j);
            }
        }
    }


}
