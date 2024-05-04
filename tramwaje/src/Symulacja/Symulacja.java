package Symulacja;

import Komunikacja.Przystanek;
import Komunikacja.Pasazer;
import Komunikacja.Linia;
import Komunikacja.Tramwaj;
import Zdarzenia.PasazerPrzyszedl;
import Zdarzenia.PasazerWsiadl;
import Zdarzenia.TramwajPrzyjechal;
import Zdarzenia.Zdarzenie;

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
    private ListaZdarzen kolejka;
    private int sumaPrzejazdow;
    private int przejazdyDnia;
    private int czasCzekania;


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
        kolejka = new ListaZdarzen();
    }
    void rozpocznijSymulacje(){
        sumaPrzejazdow = 0;
        przejazdyDnia = 0;
        int czasCzekaniaDzis = 0;
        int liczbaCzekan = 0;
        for (int i = 0; i < liczbaDni; i++){
            przejazdyDnia = 0;
            for (int j = 0; j < liczbaPasazerow; j++){
                Przystanek p = pasazerowie[j].getPrzystanek();
                Losowanie losujGodzine = new Losowanie();
                int godzina = losujGodzine.losuj(360, 720);
                kolejka.dodajZdarzenie(new PasazerPrzyszedl(p, pasazerowie[j] , i, godzina, kolejka.getHead()));
            }
            dodajTramwaje(i);
            while (!kolejka.czyPusta()){
                Zdarzenie z = kolejka.getZdarzenie();
                z.wykonaj();
                if (z instanceof TramwajPrzyjechal) {
                    przejazdyDnia += ((TramwajPrzyjechal) z).getLiczbaPrzejazdow();
                }
            }
            sumaPrzejazdow += przejazdyDnia;
            System.out.println("Liczba przejazdow dnia " + i + ": " + przejazdyDnia);
            koniecDnia();
            for (int j = 0; j < liczbaPasazerow; j++){
                czasCzekaniaDzis += pasazerowie[j].getCzasCzekania();
                liczbaCzekan += pasazerowie[j].getLiczbaCzekan();
            }
            int srCzas = czasCzekaniaDzis / liczbaCzekan;
            System.out.println("Sredni czas czekania dnia " + i + ": " + srCzas + " minut");
            czasCzekania += czasCzekaniaDzis;
        }
        System.out.println("Laczna liczba przejazdow: " + sumaPrzejazdow);
        System.out.println("Laczny czas czekania: " +
                czasCzekania / 60 + " godzin " + czasCzekania % 60 + " minut");

    }

    void dodajTramwaje(int dzien){
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

    void dodajTramwaj(Tramwaj t, int dzien, int czas, int index){
        int kierunek = 1;// ustawic go dobrze
        boolean pierwszyPrzejazd = true;
        if (index != 0)
            kierunek = -1;
        while (czas <= 1380){
            kolejka.dodajZdarzenie(new TramwajPrzyjechal(t.getLinia().getPrzystanek(index),
                    t, dzien, czas, null));
            if (kierunek == -1){
                if (index == 0){
                    czas += t.getLinia().getCzasPrzystanku(t.getLinia().getDlugoscTrasy() - 1);
                }
                else {
                    czas += t.getLinia().getCzasPrzystanku(index - 1);
                }
            }
            else {
                czas += t.getLinia().getCzasPrzystanku(index);
            }
            index += kierunek;
            if (index == 0 || index == t.getLinia().getDlugoscTrasy() - 1)
                kierunek *= -1;

            if (!pierwszyPrzejazd && ((index == 1 && kierunek == 1) || (
                    index == t.getLinia().getDlugoscTrasy() - 2 && kierunek == -1)))
                czas += t.getCzasPostoju();

            pierwszyPrzejazd = false;
        }
    }

    void koniecDnia(){
        for (int i = 0; i < liczbaPrzystankow; i++){
            przystanki[i].oproznij();
        }
        for (int i = 0; i < liczbaLinii; i++){
            for (int j = 0; j < linie[i].getLiczbaTramwajow(); j++){
                linie[i].getTramwaj(j).oproznij();
            }
        }
    }

}
