/*
 * Soubor: MyLine.java
 * Ukol c. 2
 * Autor: Marek Ziska, xziska03@stud.fit.vutbr.cz
 * Skupina: 2BIB
 * Datum 02.04.2020
 */

package ija.projekt;


import java.util.*;

/**
 * Reprezentuje jednu linku hromadné dopravy. Každá linka má svůj jedinečný identifikátor, seznam zastávek
 * a seznam ulic, kterými projíždí. Ulice musí na sebe navazovat, na ulici, kterou linka projíždí, nemusí
 * být zastávka (příp. zastávka není součástí linky).
 */
public class MyLine implements Line {
    private String lineID;
    private List<MyStreet> streets;
    private int busCountQueue;

    public MyLine(String lineID, int busCount) {
        this.lineID = lineID;
        this.streets = new ArrayList<>();
        this.busCountQueue = busCount;
    }

    @Override
    public void replaceStreet(MyStreet targetStreet){
        List<MyStreet> replacementStreets = new ArrayList<>();
        for(MyStreet street: streets){
            if(street.equals(targetStreet)){
               replacementStreets.add(targetStreet);
            }
            else{
                replacementStreets.add(street);
            }
        }
        streets.clear();
        streets.addAll(replacementStreets);
    }

    @Override
    public boolean lineContainsStreet(MyStreet street){
        if(streets.contains(street)){
            return true;
        }
        return false;
    }

    @Override
    public void decreaseBusQueue(){
        busCountQueue -= 1;
    }

    @Override
    public int getBusCount() {
        return busCountQueue;
    }

    @Override
    public boolean addStreet(MyStreet s) {
        if (streets.isEmpty()) {
            streets.add(s);
            return true;
        }
        if(streets.get(streets.size()-1).follows(s)){
            streets.add(s);
            return true;
        }
        return false;
    }

    @Override
    public List<MyStreet> getStreets() {
        return streets;
    }

    @Override
    public String getID() {
        return lineID;
    }

    @Override
    public boolean containsStreet(MyStreet searchedStreet) {
        for (MyStreet street : streets){
            if(street.equals(searchedStreet)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Stop> getStops() {
        List<Stop> stops = new ArrayList<>();
        for(Street street : streets){
            stops.addAll(street.getStops());
        }
        return stops;
    }

    @Override
    public double getLength() {
        double length = 0;
        for(int k = 0; k < streets.size(); k++){
            length += streets.get(k).getStreetLength();
        }
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyLine myLine = (MyLine) o;
        return busCountQueue == myLine.busCountQueue &&
                Objects.equals(lineID, myLine.lineID) &&
                Objects.equals(streets, myLine.streets);
    }

}