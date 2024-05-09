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

    public void wczytajDane(){/////////////////////////////////////////////////////////////////////////////////////////////

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
                linie[i].setTramwaje((new Tramwaj
                        (pojemnoscTramwaju, numerPojazdu++, linie[i])), j);
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
        for (int i = 1; i <= liczbaDni; i++){
            przejazdyDnia = 0;
            czasCzekaniaDzis = 0;
            dodajPasazerow(i);
            dodajTramwaje(i);
            while (!kolejka.czyPusta()){
                Zdarzenie z = kolejka.getZdarzenie();
                z.wykonaj();
                if (z instanceof TramwajPrzyjechal) {
                    // liczy ile osob wsiadlo (przejechalo) w kazdym zdarzeniu
                    przejazdyDnia +=
                            ((TramwajPrzyjechal) z).getLiczbaPrzejazdow();
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
            // losuje godzine o ktorej pasazer przychodzi rano
            int godzina = Losowanie.losuj(360, 720);
            kolejka.dodajZdarzenie(new PasazerPrzyszedl
                    (p, pasazerowie[j] , dzien, godzina, kolejka.getHead()));
        }
    }

    public void dodajTramwaje(int dzien){
        for (int i = 0; i < liczbaLinii; i++){
            Linia l = linie[i];
            // odstep miedzy wyjazdem z zajezdni kolejnych tramwajow
            int odstep = l.getCzasTrasy() / l.getLiczbaTramwajow();
            for (int j = 0; j < l.getLiczbaTramwajow(); j++){
                if (j % 2 == 0) { // zaczyna na jednym koncu trasy
                    l.getTramwaj(j).ustawKierunek(1);
                    dodajTramwaj
                            (l.getTramwaj(j), dzien, 360 + (odstep * j / 2));
                }
                else { // zaczyna na drugim koncu trasy
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

        while(czas <= 1440 && !(czas > 1380 && index == poczatek)){
            kolejka.dodajZdarzenie(new TramwajPrzyjechal
                    (l.getPrzystanek(index), t, dzien, czas, null, index, t.getKierunek()));
            int indexCzasu = index;
            if (t.getKierunek() == -1){
                indexCzasu--;
                if (indexCzasu == -1)
                    indexCzasu = l.getDlugoscTrasy() - 1;
            }
            if ((index == 0 && t.getKierunek() == -1) ||
                    (index == l.getDlugoscTrasy() - 1 && t.getKierunek() == 1)) {
                t.zmienKierunek();
                index -= t.getKierunek();
            }
            index += t.getKierunek();
            czas += l.getCzasPrzystanku(indexCzasu);
        }
        kolejka.dodajZdarzenie(new TramwajPrzyjechal
                (l.getPrzystanek(index), t, dzien, czas, null, index, t.getKierunek()));
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
        System.out.println("Łączna liczba przejazdów: " + sumaPrzejazdow);
        int srCzas = 0;
        if (liczbaCzekan != 0) {
            srCzas = (60 * czasCzekania) / liczbaCzekan;
        }
        int godziny = srCzas / 3600; // liczba sekund w godzinie
        int minuty = (srCzas % 3600) / 60; // reszta po odjęciu godzin, podzielona na minuty
        int sekundy = srCzas % 60; // reszta po podzieleniu przez 60, czyli pozostałe sekundy
        if (godziny > 0)
            System.out.println("Średni czas czekania: " + godziny + "h " + minuty + "m " + sekundy + "s");
        else if (minuty > 0)
            System.out.println("Średni czas czekania: " + minuty + "m " + sekundy + "s");
        else
            System.out.println("Średni czas czekania: " + sekundy + "s");
    }


}
