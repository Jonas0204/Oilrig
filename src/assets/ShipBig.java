package assets;

import java.util.ArrayList;
import static programm.Methods.getCounterShips;

/**
 *
 */
public class ShipBig extends Ship{


    /**
     *
     */
    public ShipBig(){
        maxCapacity = 100;
        this.id = getCounterShips();
    }

    /**
     *
     * @param id
     */
    // Die negative ID stellt klar, dass das nur ein theoretischer Platzhalter ist
    public ShipBig(int id){
        this.id = -id;
    }
}
