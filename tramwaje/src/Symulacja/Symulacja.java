package Symulacja;

import Komunikacja.Przystanek;
import Komunikacja.Pasazer;
import Komunikacja.Linia;
import Komunikacja.Tramwaj;
import Zdarzenia.PasazerPrzyszedl;
import Zdarzenia.TramwajPrzyjechal;
import Zdarzenia.Zdarzenie;

import java.util.Scanner;

public class Symulacja {
    private Pasazer[] pasazerowie;
    private Przystanek[] przystanki;
    private Linia[] linie;
    private int liczbaDni;
    private int liczbaLinii;
    private int liczbaPrzystankow;
    private int liczbaPasazerow;
    private ListaZdarzen kolejka;
    private int sumaPrzejazdow;
    private int czasCzekania;
    private int liczbaCzekan;

    public void wczytajDane(){

        Scanner scanner = new Scanner(System.in);
        liczbaDni = scanner.nextInt();
        int pojemnoscPrzystanku = scanner.nextInt();
        liczbaPrzystankow = scanner.nextInt();
        przystanki = new Przystanek[liczbaPrzystankow];

        for (int i = 0; i < liczbaPrzystankow; i++){
            String nazwa = scanner.next();
            przystanki[i] = new Przystanek(nazwa, pojemnoscPrzystanku);
        }

        liczbaPasazerow = scanner.nextInt();
        pasazerowie = new Pasazer[liczbaPasazerow];

        for (int i = 0; i < liczbaPasazerow; i++){
            int x = Losowanie.losuj(0, liczbaPrzystankow - 1);
            pasazerowie[i] = new Pasazer(przystanki[x], i);
        }

        int pojemnoscTramwaju = scanner.nextInt();
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
        kolejka = new ListaZdarzen();
    }

    public void rozpocznijSymulacje(){
        sumaPrzejazdow = 0;
        czasCzekania = 0;
        liczbaCzekan = 0;
        int przejazdyDnia = 0;
        int czasCzekaniaDzis = 0;
        for (int i = 0; i < liczbaDni; i++){
            przejazdyDnia = 0;
            dodajPasazerow(i);
            dodajTramwaje(i);
            while (!kolejka.czyPusta()){
                Zdarzenie z = kolejka.getZdarzenie();
                z.wykonaj();
                if (z instanceof TramwajPrzyjechal) {
                    przejazdyDnia += ((TramwajPrzyjechal) z).getLiczbaPrzejazdow();
                }
            }
            koniecDnia();
            for (int j = 0; j < liczbaPasazerow; j++){
                czasCzekaniaDzis += pasazerowie[j].getCzasCzekania();
                liczbaCzekan += pasazerowie[j].getLiczbaCzekan();
            }
            wypiszStatystykiDnia(przejazdyDnia, czasCzekaniaDzis, i);
            sumaPrzejazdow += przejazdyDnia;
            czasCzekania += czasCzekaniaDzis;
        }
        wypiszStatystyki();
    }

    public void dodajPasazerow(int dzien){
        for (int j = 0; j < liczbaPasazerow; j++){
            Przystanek p = pasazerowie[j].getPrzystanek();
            int godzina = Losowanie.losuj(360, 720);
            kolejka.dodajZdarzenie(new PasazerPrzyszedl(p, pasazerowie[j] , dzien, godzina, kolejka.getHead()));
        }
    }

    public void dodajTramwaje(int dzien){
        for (int i = 0; i < liczbaLinii; i++){
            int odstep = linie[i].getCzasTrasy() / linie[i].getLiczbaTramwajow();
            for (int j = 0; j < linie[i].getLiczbaTramwajow(); j++){
                if (j % 2 == 0) {
                    dodajTramwaj(linie[i].getTramwaj(j), dzien, 360 + (odstep * j), 0);
                }
                else {
                    dodajTramwaj(linie[i].getTramwaj(j), dzien,
                            360 + (odstep * j), linie[i].getDlugoscTrasy() - 1);
                }
            }
        }
    }

    public void dodajTramwaj(Tramwaj t, int dzien, int czas, int index){
        int kierunek = 1;
        Linia l = t.getLinia();
        boolean pierwszyPrzejazd = true;
        if (index != 0)
            kierunek = -1;
        while (czas <= 1380){
            kolejka.dodajZdarzenie(new TramwajPrzyjechal(l.getPrzystanek(index),
                    t, dzien, czas, null));
            if (kierunek == -1){
                if (index == 0){
                    czas += l.getCzasPrzystanku(l.getDlugoscTrasy() - 1);
                }
                else {
                    czas += l.getCzasPrzystanku(index - 1);
                }
            }
            else {
                czas += l.getCzasPrzystanku(index);
            }
            index += kierunek;
            if (index == 0 || index == l.getDlugoscTrasy() - 1)
                kierunek *= -1;

            if (!pierwszyPrzejazd && ((index == 1 && kierunek == 1) || (
                    index == l.getDlugoscTrasy() - 2 && kierunek == -1)))
                czas += t.getCzasPostoju();

            pierwszyPrzejazd = false;
        }
    }

    public void koniecDnia(){
        for (int i = 0; i < liczbaPrzystankow; i++){
            przystanki[i].oproznij();
        }
        for (int i = 0; i < liczbaLinii; i++){
            for (int j = 0; j < linie[i].getLiczbaTramwajow(); j++){
                linie[i].getTramwaj(j).oproznij();
            }
        }
    }

    public void wypiszStatystykiDnia(int przejazdyDnia,
                                     int czasCzekaniaDzis, int dzien){
        System.out.println("Liczba przejazdow dnia "
                + dzien + ": " + przejazdyDnia);
        int godziny = czasCzekaniaDzis / 60;
        int minuty = czasCzekaniaDzis % 60;
        if (godziny > 0)
            System.out.println("Laczny czas czekania dnia " + dzien + ": "
                + godziny + "h " + minuty + "m");
        else
            System.out.println("Laczny czas czekania dnia " + dzien +
                    ": " + minuty + "m");

    }

    public void wypiszStatystyki() {
        System.out.println("Laczna liczba przejazdow: " + sumaPrzejazdow);
        int srCzas = 0;
        if (liczbaCzekan != 0) {
            srCzas = czasCzekania / liczbaCzekan;
        }
        int godziny = srCzas / 60;
        int minuty = srCzas % 60;
        if (godziny > 0)
            System.out.println("Sredni czas czekania " + ": "
                    + godziny + "h " + minuty + "m");
        else
            System.out.println("Sredni czas czekania " +
                    ": " + minuty + "m");
    }

}
