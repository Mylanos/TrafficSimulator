/*
 * Soubor: Coordinate.java
 * Ukol c. 2
 * Autor: Marek Ziska, xziska03@stud.fit.vutbr.cz
 * Skupina: 2BIB
 * Datum 02.04.2020
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

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        return ((Coordinate) obj).getX() == this.getX() && ((Coordinate) obj).getY() == this.getY();
    }

    /**
     * Vypočíta vzdialenosť medzi dvoma súradnicami.
     * @return Vzdialenosť dvoch bodov.
     */
    public double getDistance(Coordinate c) {
        return Math.sqrt((this.diffY(c)) * (this.diffY(c)) + (this.diffX(c)) * (this.diffX(c)));
    }

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

