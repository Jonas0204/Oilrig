package assets;

public class EvacuationPlanerItem {
    /** Mögliche visuelle Darstellung vom Evakuierungsplan
     *
     * Evakuierungsplan:
     * Ship 1:  crew 50/50, => Oilrig 2
     * Ship 7:  crew 88/100, => Oilrig 3
     */

    public final int shipId;
    public final int usedCrew;
    public final String type;
    public int destinationOr;

    public EvacuationPlanerItem(int shipId, int usedCrew, String type){
        this.shipId = shipId;
        this.usedCrew = usedCrew;
        this.type = type;
        this.destinationOr = 0;
    }

    public EvacuationPlanerItem clone(){
        EvacuationPlanerItem item = new EvacuationPlanerItem(this.shipId, this.usedCrew, this.type);
        item.destinationOr= this.destinationOr;
        return item;
    }



}
