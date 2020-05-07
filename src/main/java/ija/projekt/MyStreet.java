package ija.projekt;

import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Reprezentuje jednu ulici v mapě. Ulice má svůj identifikátor (název) a je
 * definována souřadnicemi. Souřadnice představují body zlomu ulice.
 * Na ulici se mohou nacházet zastávky.
 */
public class MyStreet implements Street, Drawable {
    private String streetID;
    private List<Coordinate> coordinates;
    private List<Stop> stops;

    public MyStreet(String streetID, Coordinate... coordinates) {
        this.streetID = streetID;
        this.coordinates = new ArrayList<>();
        this.coordinates.addAll(Arrays.asList(coordinates));
        this.stops = new ArrayList<>();
    }

    @Override
    public Coordinate end() {
        return coordinates.get(coordinates.size() - 1);
    }

    @Override
    public Coordinate begin() {
        return coordinates.get(0);
    }

    @Override
    public String getId() {
        return streetID;
    }

    @Override
    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    @Override
    public List<Stop> getStops() {
        return stops;
    }

    @Override
    public boolean addStop(Stop stop) {
        for (int i = 0; i < coordinates.size() - 1; i++) {
            if (stop.getCoordinate().getDistance(coordinates.get(i)) +
                    coordinates.get(i + 1).getDistance(stop.getCoordinate()) ==
                    coordinates.get(i + 1).getDistance(coordinates.get(i))
            ) {
                stop.setStreet(this);
                stops.add(stop);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean follows(Street s) {
        if(this.end().equals(s.begin())){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public double getStreetLength() {
        double length = 0;
        for(int i = 0; i < coordinates.size() - 1; i++){
            length += coordinates.get(i).getDistance(coordinates.get(i+1));
        }
        return length;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public List<Shape> getGUI() {
        List<Shape> shapes = new ArrayList<>();
        for(int i = 0; i < coordinates.size() - 1; i++){
            Coordinate a = coordinates.get(i);
            Coordinate b = coordinates.get(i + 1);
            Coordinate middle = a.getMiddleCoords(b);
            shapes.add(new Text(middle.getX(), middle.getY(), streetID));
            shapes.add(new Line( a.getX(), a.getY(), b.getX(), b.getY()));
        }

        return shapes;
    }
}
