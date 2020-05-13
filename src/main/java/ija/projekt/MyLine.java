/**
 * Soubor: MyLine.java
 * Ukol c. 2
 * Autor: Marek Ziska, xziska03@stud.fit.vutbr.cz
 * Skupina: 2BIB
 * Datum 02.04.2020
 */

package ija.projekt;


import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Reprezentuje jednu linku hromadné dopravy. Každá linka má svůj jedinečný identifikátor, seznam zastávek
 * a seznam ulic, kterými projíždí. Ulice musí na sebe navazovat, na ulici, kterou linka projíždí, nemusí
 * být zastávka (příp. zastávka není součástí linky).
 */
public class MyLine implements Line {
    private String lineID;
    private List<MyStreet> streets;

    public MyLine(String lineID) {
        this.lineID = lineID;
        this.streets = new ArrayList<>();
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

}