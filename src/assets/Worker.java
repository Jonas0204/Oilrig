package assets;

/**
 * Die Klasse Worker repräsentiert einen Arbeiter im System.
 * Jeder Arbeiter hat eine eindeutige ID.
 * @see Oilrig
 * @see ShipBig
 * @see ShipSmall
 */
public class Worker {
    private final int id;

    /**
     * Konstruktor für die Erstellung eines neuen Arbeiters mit einer eindeutigen ID.
     *
     * @param id Die eindeutige ID des Arbeiters
     */
    protected Worker(int id) {
        this.id = id;
    }
}
