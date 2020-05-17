/*
 * Soubor: Coordinate.java
 * Ukol c. 2
 * Autor: Marek Ziska, xziska03@stud.fit.vutbr.cz
 * Skupina: 2BIB
 * Datum 17.05.2020
 */

package ija.projekt;

/**
 * Reprezentuje pozici (souřadnice) v mapě. Souřadnice je dvojice (x,y), počátek mapy je vždy
 * na pozici (0,0). Nelze mít pozici se zápornou souřadnicí.
 */
public class Coordinate {
    private double x;
    private double y;

    Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Vytvoří instanci Coordinate podle zadaných parametrů.
     * @param x souřadnice x
     * @param y souřadnice y
     * @return Vytvořený objekt Coordinate. Pokud souřadnice nevyhovují podmínce objektu, vrací null.
     */
    public static Coordinate create(double x, double y) {
        if (x < 0 || y < 0) {
            return null;
        }
        return new Coordinate(x, y);
    }

    /**
     * Zisťuje či bod leži medzi dvoma bodmi, pracuje s celými číslami
     * @param a prvý bod
     * @param b druhý bod
     * @return True ak tam leží, inak false
     */
    public boolean isOnLineBetweenInt(Coordinate a, Coordinate b){
        int distanceBeginningMiddle = (int) a.getDistance(this);
        int distanceMiddleEnd = (int) this.getDistance(b);
        int distanceBeginningEnd = (int) a.getDistance(b);
        if(distanceBeginningMiddle + distanceMiddleEnd ==  distanceBeginningEnd){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Zisťuje či bod leži medzi dvoma bodmi, pracuje s desatinnými číslami
     * @param a prvý bod
     * @param b druhý bod
     * @return True ak tam leží, inak false
     */
    public boolean isOnLineBetweenDouble(Coordinate a, Coordinate b){
        double distanceBeginningMiddle = a.getDistance(this);
        double distanceMiddleEnd = this.getDistance(b);
        double distanceBeginningEnd = a.getDistance(b);
        if(distanceBeginningMiddle + distanceMiddleEnd ==  distanceBeginningEnd){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Počíta pozíciu medzi dvoma bodmi v danej zdialenosti
     * @param distance vzdialenosť na určenie pozície
     * @param coord pozícia druhého bodu
     * @return Vráti vypočítanú vzdialenosť
     */
    public Coordinate getCoordinateByDistance(double distance, Coordinate coord){
        double x1 = x + (coord.getX() - x) * distance;
        double y2 = y + (coord.getY() - y) * distance;
        return new Coordinate(x1, y2);
    }

    /**
     * Vrací hodnotu souřadnice x.
     * @return souřadnice x
     */
    public double getX() {
        return this.x;
    }

    /**
     * Vrací hodnotu souřadnice y.
     * @return souřadnice y
     */
    public double getY() {
        return this.y;
    }

    /**
     * Equals
     * @param obj porovnávaný objekt
     * @return True ak sú rovnaké, inak false
     */
    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        return ((Coordinate) obj).getX() == this.getX() && ((Coordinate) obj).getY() == this.getY();
    }

    /**
     * Vypočíta vzdialenosť medzi dvoma súradnicami.
     * @param c druhý hraničný bod
     * @return Vzdialenosť dvoch bodov.
     */
    public double getDistance(Coordinate c) {
        return Math.sqrt((this.diffY(c)) * (this.diffY(c)) + (this.diffX(c)) * (this.diffX(c)));
    }

    /**
     * Vypočíta pozíciu v strede medzi dvoma bodmi
     * @param c druhý bod
     * @return pozícia v strede
     */
    public Coordinate getMiddleCoords(Coordinate c) {
        double beginXCoord = x;
        double beginYCoord = y;
        if(c.getX() < x){
            beginXCoord = c.getX();
        }
        if (c.getY() < y){
            beginYCoord = c.getY();
        }

        return new Coordinate(beginXCoord + (Math.abs(x - c.getX()) / 2), beginYCoord + (Math.abs(y - c.getY()) /2));
    }

    /**
     * Vrací rozdíl souřadnic X : this.x - c.x.
     * @param c Porovnávaná pozice
     * @return Rozdíl hodnot souřadnic X.
     */
    public double diffX(Coordinate c){
        return this.x - c.getX();
    }

    /**
     * Vrací rozdíl souřadnic Y : this.y - c.y.
     * @param c Porovnávaná pozice
     * @return Rozdíl hodnot souřadnic Y.
     */
    public double diffY(Coordinate c) {
        return this.y - c.getY();
    }

}

