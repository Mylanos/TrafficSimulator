/*
 * Soubor: MyStop.java
 * Ukol c. 2
 * Autor: Marek Ziska, xziska03@stud.fit.vutbr.cz
 * Skupina: 2BIB
 * Datum 17.05.2020
 */

package ija.projekt;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class MyStop implements Stop, Drawable {
    private String stopID;
    private Coordinate coordinateStop;

    public MyStop(String stopID, Coordinate coordinateStop) {
        this.stopID = stopID;
        this.coordinateStop = coordinateStop;
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
    public boolean equals(Object obj) {
        if ((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        return ((Stop) obj).getId().equals(this.getId());
    }

    @Override
    public List<Shape> getGUI() {
        List<Shape> shape = new ArrayList<>();
        shape.add(new Circle(coordinateStop.getX(), coordinateStop.getY(), 8, javafx.scene.paint.Color.BLUE));
        shape.add(new Text(coordinateStop.getX()-20, coordinateStop.getY()-13, stopID));
        return shape;
    }
}