/*
 * Soubor: Stop.java
 * Ukol c. 2
 * Autor: Marek Ziska, xziska03@stud.fit.vutbr.cz
 * Skupina: 2BIB
 * Datum 17.05.2020
 */

package ija.projekt;
/**
 * Reprezentuje zastávku. Zastávka má svůj unikátní identifikátor a dále souřadnice umístění a zná ulici,
 * na které je umístěna. Zastávka je jedinečná svým identifikátorem. Reprezentace zastávky může existovat,
 * ale nemusí mít přiřazeno umístění (tj. je bez souřadnic a bez znalosti ulice). Pro shodu objektů platí,
 * že dvě zastávky jsou shodné, pokud mají stejný identifikátor.
 * @author koci
 */
public interface Stop {

    static Stop defaultStop(String id, Coordinate c){
        return new MyStop(id, c);
    }
    /**
     * Vrátí identifikátor zastávky.
     * @return Identifikátor zastávky.
     */
    String getId();

    /**
     * Vrátí pozici zastávky.
     * @return Pozice zastávky. Pokud zastávka existuje, ale dosud nemá umístění, vrací null.
     */
    Coordinate getCoordinate();

}