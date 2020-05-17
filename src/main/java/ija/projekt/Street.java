/*
 * Soubor: Street.java
 * Ukol c. 2
 * Autor: Marek Ziska, xziska03@stud.fit.vutbr.cz
 * Skupina: 2BIB
 * Datum 17.05.2020
 */

package ija.projekt;

import javafx.scene.shape.Shape;

import java.util.List;

/**
 * Reprezentuje jednu ulici v mapě. Ulice má svůj identifikátor (název) a je definována souřadnicemi.
 * Souřadnice představují body zlomu ulice. Na ulici se mohou nacházet zastávky.
 */
public interface Street {

    /**
     * Vracia oneskorenie na ulicy.
     * @return veľkosť oneskorenia
     */
    int getDelay();

    /**
     * Nastaví oneskorenie na ulici.
     * @param delay oneskorenie
     */
    void setDelay(int delay);

    /**
     * Vrací souřadnice koncu ulice.
     * @return Coordinate koncu ulice.
     */
    Coordinate end();

    /**
     * Vrací souřadnice začátku ulice.
     * @return Coordinate začátku ulice.
     */
    Coordinate begin();

    /**
     * Vrátí identifikátor ulice.
     * @return Identifikátor ulice.
     */
    String getId();

    /**
     * Vrátí seznam souřadnic definujících ulici. První v seznamu je vždy počátek a poslední v seznamu konec ulice.
     * @return Seznam souřadnic ulice.
     */
    List<Coordinate> getCoordinates();

    /**
     * Vrátí seznam zastávek na ulici.
     * @return Seznam zastávek na ulici. Pokud ulize nemá žádnou zastávku, je seznam prázdný.
     */
    List<MyStop> getStops();

    /**
     * Vráti polohu na ulicy v danej vzdialenosti
     * @param distance vzdialenosť
     * @return poloha
     */
    public Coordinate getCoordinateOnStreet(double distance);

    /**
     * Přidá do seznamu zastávek novou zastávku.
     * @param distance vzdialenosť v akej bude zastávka.
     * @param name názov zastávky
     */
    void addStop(double distance, String name);

    /**
     * Testuje, zda ulice navazuje na zadanou ulici. Ulice na sebe navazují, pokud dva libovolné konce ulice this a s mají stejné souřadnice.
     * @param s Testovaná ulice
     * @return True, pokud na sebe ulice navazují, jinak false.
     */
    boolean follows(MyStreet s);

    /**
     * Počíta dĺžku ulice
     * @return dľžká ulice
     */
    double getStreetLength();

    /**
     * Vráti element ulice, zvýraznený farbou
     * @return element ulice
     */
    Shape getHighlightedGUI();

    /**
     * Vrati elementy reprezentujuce ulicu, tvoriace GUI.
     * @return GUI elementy
     */
    List<Shape> getGUIstreet();

}
