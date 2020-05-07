package ija.projekt;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Bus implements Drawable, TimeUpdate{
    private Coordinate position;
    private double distance = 0;
    private double speed;
    private Coordinate beginPosition;
    private Coordinate endPosition;
    private Line line;
    private List<Shape> gui;

    public Bus(double speed, Line busLine){
        this.beginPosition = busLine.getStreets().get(0).getCoordinates().get(0);
        this.position = beginPosition;
        Street lastStreet = busLine.getStreets().get(busLine.getStreets().size()-1);
        this.endPosition = lastStreet.getCoordinates().get(lastStreet.getCoordinates().size() - 1);
        this.speed = speed;
        this.line = busLine;
        gui = new ArrayList<>();
        gui.add(new Circle(position.getX(), position.getY(), 10, javafx.scene.paint.Color.RED));
        //gui.add(); ak bude treba pridavat nieco ine okrem circel
    }

    private void modifyGui(Coordinate coordinate){
        for (Shape shape : gui){
            shape.setTranslateX(coordinate.getX() - position.getX() + shape.getTranslateX());
            shape.setTranslateY(coordinate.getY() - position.getY() + shape.getTranslateY());
        }
    }

    public Coordinate getNextPosition(){
        double length = 0;
        Coordinate a = null, b = null;
        Street street = null;
        for (int i = 0; i < line.getStreets().size(); i++){
            street = line.getRoute().get(i).getKey();
            for (int n = 0; n < street.getCoordinates().size() - 1; n++){
                a = street.getCoordinates().get(n);
                b = street.getCoordinates().get(n + 1);

                if(length + a.getDistance(b) >= distance){
                    double driven = (distance - length) / a.getDistance(b);
                    return new Coordinate( a.getX() + (b.getX() - a.getX()) * driven, a.getY() + (b.getY() - a.getY()) * driven);
                }

                length += a.getDistance(b);
            }
        }
        if(a == null || b == null){
            return null;
        }

        double driven = (distance - length) / a.getDistance(b);
        return new Coordinate( a.getX() + (b.getX() - a.getX()) * driven, a.getY() + (b.getY() - a.getY()) * driven);
    }

    @Override
    public List<Shape> getGUI() {
        return gui;
    }

    @Override
    public void update(LocalTime time) {
        distance += speed;
        Coordinate coords = this.getNextPosition();
        System.out.print("Distance: ");
        System.out.println(distance);
        System.out.print("LineLength: ");
        System.out.println(line.getLength());
        if(line.getLength() > distance){
            modifyGui(coords);
            position = coords;
        }

    }
}
