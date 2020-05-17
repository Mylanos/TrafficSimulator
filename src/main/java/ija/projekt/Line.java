/*
 * Soubor: Line.java
 * Ukol c. 2
 * Autor: Marek Ziska, xziska03@stud.fit.vutbr.cz
 * Skupina: 2BIB
 * Datum 17.05.2020
 */

package ija.projekt;

import java.util.List;

/**
 * Reprezentuje jednu linku hromadné dopravy. Každá linka má svůj jedinečný identifikátor,
 * seznam zastávek a seznam ulic, kterými projíždí. Ulice musí na sebe navazovat, na ulici,
 * kterou linka projíždí, nemusí být zastávka (příp. zastávka není součástí linky).
 */
public interface Line {

    /**
     * Nahrádza jednu ulicu v linke
     * @param targetStreet nahradzujúca ulica
     */
    void replaceStreet(MyStreet targetStreet);

    /**
     * Zisťuje či linka obsahuje danú ulicu
     * @param street hľadaná ulica
     * @return True ak sa našla, false inak
     */
    boolean lineContainsStreet(MyStreet street);

    /**
     * Zníži počet autobusov čakajúcich na začatie cesty o jedna. Autobus sa vydal na linku
     */
    void decreaseBusQueue();

    /**
     * Vráti počet zástavok v linke
     * @return počet zástavok v linke
     */
    int getBusCount();

    /**
     * Přidá ulici do mapy.
     * @param s Objekt reprezentující ulici.
     * @return True ak bola pridaná ulica, inak false
     */
    boolean addStreet(MyStreet s);

    /**
     * Vráti zoznam ulíc v linke
     * @return zoznam ulíc
     */
    public List<MyStreet> getStreets();

    /**
     * Vráti názov linky
     * @return názov linky
     */
    public String getID();

    /**
     * Hľadá ulicu v linke
     * @param searchedStreet hľadaná ulica
     * @return True ak linka obsahuje ulicu, inak false
     */
    public boolean containsStreet(MyStreet searchedStreet);

    /**
     * Vráti počet zástavok
     * @return počet zástavok
     */
    public List<Stop> getStops();

    /**
     * vráti dĺžku celej linky
     * @return dlžka linky
     */
    public double getLength();

}