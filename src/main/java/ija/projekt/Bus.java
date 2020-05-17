/*
 * Soubor: Bus.java
 * Ukol c. 2
 * Autor: Marek Ziska, xziska03@stud.fit.vutbr.cz
 * Skupina: 2BIB
 * Datum 17.05.2020
 */

package ija.projekt;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Bus implements Drawable, TimeUpdate{
    private Coordinate position;
    private double distance;
    private double speed;
    private transient double originalSpeed;
    private MyLine line;
    private int workShiftLength;
    private transient List<Shape> gui;
    private transient Coordinate closestStop;
    private transient MyStreet currentStreet;
    private transient int timeAtStop;
    private transient LinkedHashMap<Integer, Coordinate> moveSchedule;
    private transient LinkedHashMap<Integer, Stop> stopSchedule;
    private transient int dispatchTime;

    public Bus(double speed, MyLine busLine, int workShift){
        this.distance = 0;
        this.position = busLine.getStreets().get(0).getCoordinates().get(0);
        this.currentStreet = busLine.getStreets().get(0);
        this.speed = speed;
        this.originalSpeed = speed;
        this.line = busLine;
        this.moveSchedule = new LinkedHashMap<>();
        this.stopSchedule = new LinkedHashMap<>();
        this.workShiftLength = workShift;
        gui = new ArrayList<>();
        gui.add(new Circle(position.getX(), position.getY(), 7, javafx.scene.paint.Color.RED));
    }

    public void initTransientVars(){
        this.originalSpeed = speed;
        gui = new ArrayList<>();
        gui.add(new Circle(position.getX(), position.getY(), 7, javafx.scene.paint.Color.RED));
        this.currentStreet = line.getStreets().get(0);
        this.moveSchedule = new LinkedHashMap<>();
        this.stopSchedule = new LinkedHashMap<>();
    }

    public void restartPosition(){
        speed = originalSpeed;
        Coordinate coords;
        while(line.getLength()-(speed+5) < distance){
            coords = this.determineNextPosition();
        }
        modifyGui(line.getStreets().get(0).getCoordinates().get(0));
        this.setPosition(line.getStreets().get(0).getCoordinates().get(0));
        distance = 0;
    }

    public void clearSchedule(){
        moveSchedule.clear();
        stopSchedule.clear();
        this.moveSchedule = new LinkedHashMap<>();
        this.stopSchedule = new LinkedHashMap<>();
    }

    public void setDispatchTime(int dispatchTime){
        this.dispatchTime = dispatchTime;
    }

    /**
     * calculates schedule times of all bus moves (used when specific time given) and all bus stops times
     */
    public void calculateScheduleTimes(){
        double originalDistance = distance;
        Coordinate originalPosition = position;
        Coordinate recentPosition = null;
        distance = 0;
        int counter = this.dispatchTime;
        int shift = 0;
        moveSchedule.put(counter, position);
        while(shift <= workShiftLength){
            if(line.getLength() < distance){
                shift++;
                distance = distance - line.getLength();
            }
            this.findClosestBusStation();
            Coordinate coords = determineNextPosition();
            counter++;
            if(!coords.equals(recentPosition)){
                moveSchedule.put(counter, coords);
            }
            position = coords;
            recentPosition = coords;
            this.findCurrentStreet();
        }
        /* compare coordinates of all moves to coordinates of all bus stops
         * when coordinates are equal store the bus stop with its "time"(int counter)
         * */
        moveSchedule.forEach((key, value) -> {
            for (Stop stop : line.getStops()) {
                if (value.equals(stop.getCoordinate())) {
                   stopSchedule.put(key, stop);
                }
            }
        });
        distance = originalDistance;
        position = originalPosition;
    }

    public String getFormattedBusSchedule(StopWatch stopwatch){
        int columnWidth = 23;
        StringBuilder scheduleString = new StringBuilder();
        scheduleString.append(String.format("\t\t\t%s\n\n", "Schedule of line " + line.getID()));
        scheduleString.append(String.format("%1$12s %2$13s %3$13s\n", "BUS STOP", "ARRIVES", "DEPARTS"));
        stopSchedule.forEach((key, value) -> {
            String arrival = stopwatch.getSpecificTimeInString(key);
            String departure = stopwatch.getSpecificTimeInString(key+3);
            scheduleString.append(String.format("%1$"+(columnWidth-value.getId().length())+
                                                "s %2$"+(columnWidth-arrival.length())+
                                                "s %3$"+(columnWidth-departure.length())+"s\n", value.getId(),
                                                                    arrival,
                                                                    departure));
        });
        return scheduleString.toString();
    }

    public void modifyGui(Coordinate coordinate){
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
            street = line.getStreets().get(i);
            for (int n = 0; n < street.getCoordinates().size() - 1; n++){
                if(i != 0){
                    Street street2 = line.getStreets().get(i-1);
                    if((street2.getCoordinates().get(0).equals(street.getCoordinates().get(n+1))) ||
                            (street2.getCoordinates().get(1).equals(street.getCoordinates().get(n+1))) ||
                            (street2.getCoordinates().get(0).equals(street.getCoordinates().get(n)))){
                        a = street.getCoordinates().get(n + 1);
                        b = street.getCoordinates().get(n);
                    }
                    else{
                        a = street.getCoordinates().get(n);
                        b = street.getCoordinates().get(n + 1);
                    }
                }
                else{
                    a = street.getCoordinates().get(n);
                    b = street.getCoordinates().get(n + 1);
                }

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

    public void setPosition(Coordinate coords){
        position = coords;
        this.findCurrentStreet();
    }

    @Override
    public List<Shape> getGUI() {
        return gui;
    }

    public Coordinate getCoords(){
        return position;
    }

    public MyLine getLine(){
        return line;
    }

    public void findClosestBusStation(){
        int closestDistance = Integer.MAX_VALUE;
        closestStop = null;
        for (Stop stop : currentStreet.getStops()){
            if(position.getDistance(stop.getCoordinate()) < closestDistance){
                if(stop.getCoordinate().isOnLineBetweenDouble(currentStreet.getCoordinates().get(0), currentStreet.getCoordinates().get(1))){
                    closestStop = stop.getCoordinate();
                }
            }

        }
    }

    public void findCurrentStreet(){
        for (MyStreet street: line.getStreets()){
            if(position.isOnLineBetweenDouble(street.getCoordinates().get(0), street.getCoordinates().get(1))){
                currentStreet = street;
            }
        }
    }

    public Coordinate determineNextPosition(){
        if(closestStop != null){
            double rangeOfStop = Math.abs(currentStreet.getCoordinates().get(0).getDistance(closestStop) -
                    currentStreet.getCoordinates().get(0).getDistance(position));
            if(rangeOfStop < originalSpeed){
                if(timeAtStop < 3){
                    speed = 0;
                    timeAtStop++;
                    distance += position.getDistance(closestStop);
                    return closestStop;
                }
                else{
                    speed = originalSpeed;
                }
            }
            else{
                timeAtStop = 0;
                speed = originalSpeed;
            }
        }
        distance += (speed/currentStreet.getDelay());
        return this.getNextPosition();
    }
    @Override
    public void update(LocalTime time) {
        this.findClosestBusStation();
        Coordinate coords = this.determineNextPosition();
/*        System.out.print("Distance: ");
        System.out.print(distance);
        System.out.print("   LineLength: ");
        System.out.println(line.getLength());
        System.out.print("Current street: ");
        System.out.println(currentStreet.getId());
        System.out.print("Current position");
        System.out.println(" X : " + position.getX() + " Y " + position.getY());
        if(closestStop != null){
            System.out.print("Closest stop: ");
        }*/
        if(line.getLength() > distance){
            modifyGui(coords);
        }
        else{
            distance = distance - line.getLength();
            coords = this.determineNextPosition();
            modifyGui(coords);
        }
        this.setPosition(coords);
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        return super.equals(obj);
    }
}
