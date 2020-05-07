package ija.projekt;

import java.util.List;

/**
 * Reprezentuje jednu ulici v mapě. Ulice má svůj identifikátor (název) a je definována souřadnicemi.
 * Souřadnice představují body zlomu ulice. Na ulici se mohou nacházet zastávky.
 */
public interface Street {

    static Street defaultStreet(String id, Coordinate... coordinates) {
        for (int i = 0; i < coordinates.length - 1; i++) {
            if (!((coordinates[i + 1].getX() - coordinates[i].getX()) == 0 ||
                    coordinates[i + 1].getY() - coordinates[i].getY() == 0)) {
                return null;
            }
        }
        return new MyStreet(id, coordinates);
    }

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
    List<Stop> getStops();

    /**
     * Přidá do seznamu zastávek novou zastávku.
     * @param stop Nově přidávaná zastávka.
     */
    boolean addStop(Stop stop);

    /**
     * Testuje, zda ulice navazuje na zadanou ulici. Ulice na sebe navazují, pokud dva libovolné konce ulice this a s mají stejné souřadnice.
     * @param s Testovaná ulice
     * @return True, pokud na sebe ulice navazují, jinak false.
     */
    boolean follows(Street s);

    public double getStreetLength();

}
