package ija.projekt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyMap {
    private HashMap<String, MyStreet> streets;
    private HashMap<String, MyStop> stops;
    private List<Bus> buses;
    private List<Line> lines;

    public MyMap() {
        this.streets = new HashMap<>();
        this.stops = new HashMap<>();
        this.buses = new ArrayList<>();
        this.lines = new ArrayList<>();
    }

    public void addStreet(MyStreet street){
        streets.put(street.getId(), street);
    }

    public void addStop(MyStop stop){
        stops.put(stop.getId(), stop);
    }

    public void addBus(Bus bus){
        buses.add(bus);
    }

    public void addLine(MyLine line){
        lines.add(line);
    }

    public void getDrawableElements(){

    }

}
