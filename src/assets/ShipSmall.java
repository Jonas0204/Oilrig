package assets;

import static programm.Manager.getCounterShips;

/**
 * Die Klasse ShipSmall repräsentiert ein kleines Schiff im System.
 * Kleine Schiffe haben eine maximale Kapazität von 50.
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
     * Konstruktor für ein neues kleines Schiff mit einer spezifischen ID.
     *
     * @param id Die ID des kleinen Schiffs (negativer Wert als Platzhalter)
     */
    // Die negative ID stellt klar, dass das nur ein theoretischer Platzhalter ist
    public ShipSmall(int id){
        this.id = -id;
    }

}
