/*
 * Soubor: MyMap.java
 * Ukol c. 2
 * Autor: Marek Ziska, xziska03@stud.fit.vutbr.cz
 * Skupina: 2BIB
 * Datum 17.05.2020
 */

package ija.projekt;

import java.util.ArrayList;
import java.util.List;

/**
 * Trieda obsahujúca všetky hlavné triedy predstavujúce mapu hromadnej dopravy, pozostávajúcej z ulíc, zastávok, autobusou
 * a liniek.
 */
public class MyMap {
    private List<MyStreet> streets;
    private List<MyStop> stops;
    private List<Bus> buses;
    private List<MyLine> lines;

    public MyMap() {
        this.streets = new ArrayList<>();
        this.stops = new ArrayList<>();
        this.buses = new ArrayList<>();
        this.lines = new ArrayList<>();
    }

    /**
     * Přidá ulici do zoznamu všetkých ulíc na mape
     * @param street Objekt reprezentující ulici.
     */
    public void addStreet(MyStreet street){
        streets.add(street);
        List<MyStop> stops = street.getStops();
        for(Stop stop: stops){
            this.addStop((MyStop) stop);
        }
    }

    /**
     * Přidá zástavku do zoznamu všetkých zástavok na mape
     * @param stop Objekt reprezentující zastávku.
     */
    public void addStop(MyStop stop){
        stops.add(stop);
    }

    /**
     * Pridá autobus do zoznamu všetkých autobusov na mape
     * @param line Objekt linky, po ktorej sa bude autobus premávať.
     * @param busSpeed Rýchlosť autobusu
     * @param workShift Počet jázd autobusu po linke
     */
    public void addBus(MyLine line, int busSpeed, int workShift){
        Bus bus = new Bus(busSpeed, line, workShift);
        buses.add(bus);
    }

    /**
     * Pridá linku do zoznamu všetkých liniek na mape
     * @param lineID Názov linky
     * @param busCount Počet autobusov jazdiacich po linke
     * @param lineStreetsNames Názvy ulíc, z ktorých pozostáva linka
     */
    public void addLine(String lineID, int busCount, String... lineStreetsNames){
        MyLine line = new MyLine(lineID, busCount);
        for(String streetID: lineStreetsNames){
            for(MyStreet street: streets){
                if(street.getId().equals(streetID)){
                    line.addStreet(street);
                }
            }
        }
        lines.add(line);
        for (int i = 0; i < busCount; i++){
            this.addBus(line, 15, 8);
        }
    }

    /**
     * Vráti zoznam zastávok
     * @return zoznam zastávok
     */
    public List<MyStop> getStops() {
        return stops;
    }

    /**
     * Vráti zoznam autobusov
     * @return zoznam autobusov
     */
    public List<Bus> getBuses() {
        return buses;
    }

    /**
     * Vráti zoznam liniek
     * @return zoznam liniek
     */
    public List<MyLine> getLines() {
        return lines;
    }

    /**
     * Vráti zoznam ulíc
     * @return zoznam ulíc
     */
    public List<MyStreet> getStreets() {
        return streets;
    }

    /**
     * Inicializuje triedne premenné, ktoré neboli inicializované z json súboru. Boli to premenné označené ako transient,
     * ktoré sa nachádzajú len v triede autobusu
     */
    public void initTransientParts(){
        for(Bus bus:buses){
            bus.initTransientVars();
        }
    }

}
