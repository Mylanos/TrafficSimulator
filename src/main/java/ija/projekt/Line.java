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
     * Přidá do seznamu zastávek novou zastávku.
     * @param stop Nově přidávaná zastávka.
     */
    boolean addStop(Stop stop);
    /**
     * Přidá ulici do mapy.
     * @param s Objekt reprezentující ulici.
     */
    boolean addStreet(Street s);

    /**
     * Vráti zoznam ulíc v linke
     * @return zoznam ulíc
     */
    public List<Street> getStreets();

    /**
     * vráti dĺžku celej linky
     * @return dlžka linky
     */
    public double getLength();

    /**
     * Vrací trasu linky (seznam ulic, kterými linka projíždí, a její zastávky).
     * Každý průjezd je vždy dvojice uložená s využitím AbstractMap.SimpleImmutableEntry.
     * Pokud linka ulicí jen projíždí, je v příslušné dvojici na pozici zastávky hodnota null.
     *
     * @returns Trasa linky. Vrací defenzivní kopii trasy nebo nemodifikovatelný seznam - úpravy
     * vráceného seznamu nemají žádny vliv na vnitřní informace o lince, příp. nejsou možné.
     */
    List<AbstractMap.SimpleImmutableEntry<Street,Stop>> getRoute();
}