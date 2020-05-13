/**
 * Soubor: Line.java
 * Ukol c. 2
 * Autor: Marek Ziska, xziska03@stud.fit.vutbr.cz
 * Skupina: 2BIB
 * Datum 02.04.2020
 */

package ija.projekt;

import java.util.AbstractMap;
import java.util.List;

/**
 * Reprezentuje jednu linku hromadné dopravy. Každá linka má svůj jedinečný identifikátor,
 * seznam zastávek a seznam ulic, kterými projíždí. Ulice musí na sebe navazovat, na ulici,
 * kterou linka projíždí, nemusí být zastávka (příp. zastávka není součástí linky).
 */
public interface Line {

    static Line defaultLine(String id){
        return new MyLine(id);
    }

    /**
     * Přidá ulici do mapy.
     * @param s Objekt reprezentující ulici.
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

    public boolean containsStreet(MyStreet searchedStreet);

    public List<Stop> getStops();

    /**
     * vráti dĺžku celej linky
     * @return dlžka linky
     */
    public double getLength();

}