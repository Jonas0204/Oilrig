package assets;

import static programm.Manager.getCounterShips;

/**
 * Die Klasse ShipBig definiert ein großes Schiff-Objekt.
 * Ein großes Schiff hat eine höhere Kapazität als ein kleines Schiff.
 * Diese Klasse erbt von der abstrakten Klasse Ship.
 *
 * @see Ship
 * @see ShipSmall
 */
public class ShipBig extends Ship{

    /**
     * Konstruktor für ein neues großes Schiff. Setzt automatisch die maximale Kapazität auf 100 und weist dem Schiff eine eindeutige ID zu.
     */
    public ShipBig(){
        maxCapacity = 100;
        this.id = getCounterShips();
    }

    /**
     * Alternativer Konstruktor für ein großes Schiff, der "leere Schiffe" erstellt.
     *
     * @param id eindeutige ID des großen Schiffes
     *            (Negative IDs dienen hier als theoretische Platzhalter und sollten nicht in der Praxis verwendet werden.)
     */
    // Die negative ID stellt klar, dass das nur ein theoretischer Platzhalter ist
    public ShipBig(int id){
        this.id = -id;
    }
}
