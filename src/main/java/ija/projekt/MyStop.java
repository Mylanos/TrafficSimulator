/*
 * Soubor: MyStop.java
 * Ukol c. 2
 * Autor: Marek Ziska, xziska03@stud.fit.vutbr.cz
 * Skupina: 2BIB
 * Datum 02.04.2020
 */

package ija.projekt;

import javafx.scene.shape.Shape;

import java.util.List;

public class MyStop implements Stop, Drawable {
    private String stopID;
    private Coordinate coordinateStop;
    private Street street;

    public MyStop(String stopID, Coordinate coordinateStop) {
        this.stopID = stopID;
        this.coordinateStop = coordinateStop;
    }

    public MyStop(String stopID) {
        this.stopID = stopID;
    }

    @Override
    public String getId() {
        return stopID;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinateStop;
    }

    @Override
    public void setStreet(Street street) {
        this.street = street;
    }

    @Override
    public Street getStreet() {
        return street;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        return ((Stop) obj).getId().equals(this.getId());
    }

    @Override
    public String toString() {
        return "stop(" + stopID + ")";
    }

    @Override
    public List<Shape> getGUI() {
        return null;
    }
}