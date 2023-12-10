package assets;

import java.util.ArrayList;

import static programm.Manager.getCounterShips;

/**
 * Die Klasse ShipSmall definiert ein kleines Schiff-Objekt.
 * Ein kleines Schiff hat eine kleinere Kapazität als ein großes Schiff.
 * Diese Klasse erbt von der abstrakten Klasse Ship.
 *
 * @see Ship
 * @see ShipBig
 */
public class ShipSmall extends Ship{

    /**
     * Konstruktor für ein neues kleines Schiff mit einer maximalen Kapazität von 50 und einer eindeutigen ID.
     */
    public ShipSmall() {
        maxCapacity = 50;
        this.id = getCounterShips();
    }

    /**
     * Alternativer Konstruktor für ein kleines Schiff, der "leere Schiffe" erstellt.
     *
     * @param id eindeutige ID des großen Schiffes
     *            (Negative IDs dienen hier als theoretische Platzhalter und sollten nicht in der Praxis verwendet werden.)
     */
    // Die negative ID stellt klar, dass das nur ein theoretischer Platzhalter ist
    public ShipSmall(int id){
        this.id = -id;
    }

}
