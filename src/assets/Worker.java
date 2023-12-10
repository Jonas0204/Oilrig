package assets;

/**
 * Die Klasse Worker repräsentiert einen Arbeiter im System.
 * Jeder Arbeiter hat eine eindeutige ID.
 * @see Oilrig
 * @see ShipBig
 * @see ShipSmall
 */
public class Worker {

    // ID der Arbeiter wird in dieser Fassung noch nicht genutzt
    private final int id;

    /**
     * Konstruktor für die Erstellung eines neuen Arbeiters mit einer eindeutigen ID.
     *
     * @param id eindeutige ID des Arbeiters
     */
    protected Worker(int id) {
        this.id = id;
    }
}
