package assets;

/**
 * Die Klasse EvacuationPlanerItem definiert das gleichnamige Objekt, welches die Schiffe speichert, die zur
 * Evakuierung einer Plattform benötigt werden und speichert diese als Kopie ab. Dies ist notwendig, um die Schiffe für
 * die Planerstellung nutzen zu können, ohne sie dabei tatsächlich schon zwischen den Ölplattformen zu verschieben.
 *
 * @see Oilrig#checkEvacuationSpace()
 * @author Jonas Hülse
 */
public class EvacuationPlanerItem {

    public final int shipId;
    public int usedCrew;
    public final String type;
    public int destinationOr;
    public boolean toCall = false;
    public int[] toCallID_S = {0,0};

    /**
     * Erstellt ein EvacuationPlanerItem-Objekt.
     *
     * @param shipId Integer, der die ID des Schiffes speichert
     * @param type String, der beschreibt, ob es sich um ein großes oder kleines Schiff handelt
     */
    public EvacuationPlanerItem(int shipId, String type){
        this.shipId = shipId;
        this.usedCrew = 0;
        this.type = type;
        this.destinationOr = 0;
    }
}
