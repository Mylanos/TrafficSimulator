/*
 * Soubor: Drawable.java
 * Ukol c. 2
 * Autor: Marek Ziska, xziska03@stud.fit.vutbr.cz
 * Skupina: 2BIB
 * Datum 17.05.2020
 */

package ija.projekt;
import javafx.scene.shape.Shape;

import java.util.List;

/**
 * Rozhranie pre triedy ktoré sa budú vykreslovať
 */
public interface Drawable {
    List<Shape> getGUI();
}
