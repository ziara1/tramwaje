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
    private ListaZdarzen kolejka;
    private int[] liczbaPrzejazdow;
    private int[] czasCzekania;
    private int liczbaCzekan;


    public void wczytajDane(){
        Scanner scanner = new Scanner(System.in);
        liczbaDni = scanner.nextInt();
        liczbaPrzejazdow = new int[liczbaDni + 1];
        czasCzekania = new int[liczbaDni + 1];
        int pojemnoscPrzystanku = scanner.nextInt();
        przystanki = new Przystanek[scanner.nextInt()];
        for (int i = 0; i < przystanki.length; i++)
            przystanki[i] = new Przystanek(scanner.next(), pojemnoscPrzystanku);
        pasazerowie = new Pasazer[scanner.nextInt()];
        for (int i = 0; i < pasazerowie.length; i++)
            pasazerowie[i] = new Pasazer
                    (przystanki[Losowanie.losuj(0, przystanki.length - 1)], i);
        int pojemnoscTramwaju = scanner.nextInt();
        linie = new Linia[scanner.nextInt()];
        int numerPojazdu = 0;
        for (int i = 0; i < linie.length; i++){
            int liczbaTramwajowLinii = scanner.nextInt();
            int dlugoscTrasy = scanner.nextInt();
            linie[i] = new Linia(liczbaTramwajowLinii, dlugoscTrasy, i);
            for (int j = 0; j < liczbaTramwajowLinii; j++)
                linie[i].setTramwaje((new Tramwaj
                        (pojemnoscTramwaju, numerPojazdu++, linie[i])), j);
            for (int j = 0; j < dlugoscTrasy; j++){
                String nazwa = scanner.next();
                int czas = scanner.nextInt();
                for (Przystanek przystanek : przystanki)
                    if (nazwa.equals(przystanek.getNazwa()))
                        linie[i].setTrasa(przystanek, czas, j);
            }
        }
        kolejka = new ListaZdarzen();
    }

    public void rozpocznijSymulacje(){
        liczbaCzekan = 0;
        for (int i = 1; i <= liczbaDni; i++){
            liczbaPrzejazdow[i] = 0;
            czasCzekania[i] = 0;
            dodajPasazerow(i);
            dodajTramwaje(i);
            while (!kolejka.czyPusta()){
                Zdarzenie z = kolejka.getZdarzenie();
                z.wykonaj();
                if (z instanceof TramwajPrzyjechal) {
                    // liczy ile osob wsiadlo (przejechalo) w kazdym zdarzeniu
                    liczbaPrzejazdow[i] +=
                            ((TramwajPrzyjechal) z).getLiczbaPrzejazdow();
                }
            }
            koniecDnia();
            for (Pasazer pasazer : pasazerowie) {
                czasCzekania[i] += pasazer.getCzasCzekania();
                liczbaCzekan += pasazer.getLiczbaCzekan();
            }
        }
        wypiszStatystyki();
    }

    public void dodajPasazerow(int dzien){
        for (Pasazer pasazer : pasazerowie) {
            Przystanek p = pasazer.getPrzystanek();
            // losuje godzine o ktorej pasazer przychodzi rano
            // 360 - 6:00, 720 - 12:00
            int godzina = Losowanie.losuj(360, 720);
            kolejka.dodajZdarzenie(new PasazerPrzyszedl
                    (p, pasazer, dzien, godzina, kolejka.getHead()));
        }
    }

    public void dodajTramwaje(int dzien){
        for (Linia l : linie) {
            // odstep miedzy wyjazdem z zajezdni kolejnych tramwajow
            int odstep = l.getCzasTrasy() / l.getLiczbaTramwajow();
            //360 - 6:00, godzina, o ktorej tramwaje zaczynaja jezdzic
            for (int j = 0; j < l.getLiczbaTramwajow(); j++) {
                if (j % 2 == 0) { // zaczyna na jednym koncu trasy
                    l.getTramwaj(j).ustawKierunek(1);
                    dodajTramwaj
                            (l.getTramwaj(j), dzien, 360 + (odstep * j / 2));
                } else { // zaczyna na drugim koncu trasy
                    l.getTramwaj(j).ustawKierunek(-1);
                    dodajTramwaj
                            (l.getTramwaj(j), dzien, 360 + (odstep * (j - 1) / 2));
                }
            }
        }
    }

    // dodaje zatrzymania sie poszczegolnego tramwaju w danym dniu do kolejki
    public void dodajTramwaj(Tramwaj t, int dzien, int czas){
        Linia l = t.getLinia();
        int index = 0, poczatek = 0;
        if (t.getKierunek() == -1)
            index = poczatek = l.getDlugoscTrasy() - 1;
        // 1440 - 0:00, koniec dnia
        // 1380 - 23:00, po tej godzinie tramwaje koncza tylko ostatnie kolko
        while(czas <= 1440 && !(czas > 1380 && index == poczatek)){
            kolejka.dodajZdarzenie(new TramwajPrzyjechal(l.getPrzystanek(index)
                           , t, dzien, czas, null, index, t.getKierunek()));
            int indexCzasu = index; // index, z ktorej pary trasy pobrac czas
            // jesli jedziemy w kierunku malejacym, to pobieramy z poprzedniego
            // bo pod indexem i jest czas miedzy i a i+1 przystankiem
            if (t.getKierunek() == -1){
                indexCzasu--;
                if (indexCzasu == -1)
                    indexCzasu = l.getDlugoscTrasy() - 1;
            }
            // tramwaj zawraca. odejmujemy od indeksu kierunek, zeby tramwaj
            // jeszcze "zostal" na tym przystanku (po zawroceniu na petli)
            if ((index == l.getDlugoscTrasy() - 1 && t.getKierunek() == 1)
                    || (index == 0 && t.getKierunek() == -1)) {
                t.zmienKierunek();
                index -= t.getKierunek();
            }
            index += t.getKierunek();
            czas += l.getCzasPrzystanku(indexCzasu);
        } // ostatnie zdarzenie dnia, tramwaj przyjezdza na ostatni przystanek
        kolejka.dodajZdarzenie(new TramwajPrzyjechal(l.getPrzystanek(index),
                t, dzien, czas, null, index, t.getKierunek()));
    }

    public void koniecDnia(){
        for (Przystanek przystanek : przystanki) {
            przystanek.oproznij();
        }
        for (Linia linia : linie) {
            for (int j = 0; j < linia.getLiczbaTramwajow(); j++) {
                linia.getTramwaj(j).oproznij();
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
        int sumaPrzejazdow = 0;
        int lacznyCzasCzekania = 0;
        for (int i = 1; i <= liczbaDni; i++){
            wypiszStatystykiDnia(liczbaPrzejazdow[i], czasCzekania[i], i);
            sumaPrzejazdow += liczbaPrzejazdow[i];
            lacznyCzasCzekania += czasCzekania[i];
        }
        System.out.println("Łączna liczba przejazdów: " + sumaPrzejazdow);
        int srCzas = 0;
        // czas czekania jest wyrazony w minutach, *60, zeby bylo w sekundach
        if (liczbaCzekan != 0) {
            srCzas = (60 * lacznyCzasCzekania) / liczbaCzekan;
        }
        int godziny = srCzas / 3600;
        int minuty = (srCzas % 3600) / 60;
        int sekundy = srCzas % 60;
        if (godziny > 0)
            System.out.println("Średni czas czekania: "
                    + godziny + "h " + minuty + "m " + sekundy + "s");
        else if (minuty > 0)
            System.out.println("Średni czas czekania: "
                    + minuty + "m " + sekundy + "s");
        else
            System.out.println("Średni czas czekania: " + sekundy + "s");
    }


}
