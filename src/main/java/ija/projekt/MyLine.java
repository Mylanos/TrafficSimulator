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
import java.util.List;

/**
 * Reprezentuje jednu linku hromadné dopravy. Každá linka má svůj jedinečný identifikátor, seznam zastávek
 * a seznam ulic, kterými projíždí. Ulice musí na sebe navazovat, na ulici, kterou linka projíždí, nemusí
 * být zastávka (příp. zastávka není součástí linky).
 */
public class MyLine implements Line {
    private String lineID;
    private List<Street> streets;
    private List<Stop> stops;
    private List<AbstractMap.SimpleImmutableEntry<Street, Stop>> routes;

    public MyLine(String lineID) {
        this.lineID = lineID;
        this.streets = new ArrayList<>();
        this.stops = new ArrayList<>();
        this.routes = new ArrayList<>();
    }

    @Override
    public boolean addStop(Stop stop) {
        if (stops.isEmpty()) {
            stops.add(stop);
            streets.add(stop.getStreet());
            routes.add(new AbstractMap.SimpleImmutableEntry<>(stop.getStreet(), stop));
            return true;
        }
        if(streets.get(streets.size()-1).follows(stop.getStreet())){
            stops.add(stop);
            streets.add(stop.getStreet());
            routes.add(new AbstractMap.SimpleImmutableEntry<>(stop.getStreet(), stop));
            return true;
        }
        return false;
    }

    @Override
    public boolean addStreet(Street s) {
        if (streets.isEmpty()) {
            streets.add(s);
            routes.add(new AbstractMap.SimpleImmutableEntry<>(s, null));
            return true;
        }
        if(streets.get(streets.size()-1).follows(s)){
            routes.add(new AbstractMap.SimpleImmutableEntry<>(s, null));
            streets.add(s);
            return true;
        }
        return false;
    }

    @Override
    public List<Street> getStreets() {
        return streets;
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
    public List<AbstractMap.SimpleImmutableEntry<Street, Stop>> getRoute() {
        return new ArrayList<AbstractMap.SimpleImmutableEntry<Street, Stop>>(routes);
    }
}