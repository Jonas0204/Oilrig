package assets;

import java.util.ArrayList;
import static programm.Methods.getCounterShips;

/**
 *
 */
public class ShipSmall extends Ship{

    /**
     *
     */
    public ShipSmall() {
        maxCapacity = 50;
        this.id = getCounterShips();
    }

    /**
     *
     * @param id
     */
    // Die negative ID stellt klar, dass das nur ein theoretischer Platzhalter ist
    public ShipSmall(int id){
        this.id = -id;
    }

}
