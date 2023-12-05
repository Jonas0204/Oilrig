package assets;

public class EvacuationPlanerItem {

    public final int shipId;
    public int usedCrew;
    public final String type;
    public int destinationOr;

    //@author Jonas
    //Constructor
    public EvacuationPlanerItem(int shipId, String type){
        this.shipId = shipId;
        this.usedCrew = 0;
        this.type = type;
        this.destinationOr = 0;
    }

    //@author Jonas
    public EvacuationPlanerItem clone(){
        EvacuationPlanerItem item = new EvacuationPlanerItem(this.shipId, this.type);
        item.destinationOr = this.destinationOr;
        item.usedCrew = this.usedCrew;
        return item;
    }
}
