package assets;

import java.util.ArrayList;
import static programm.Methods.getCounterShips;

/**
 *
 */
public class ShipBig extends Ship implements Comparable<ShipBig>{

    private ArrayList<Worker> crewBig = new ArrayList<>();
    protected final int maxCapacity = 100;

    /**
     *
     */
    public ShipBig(){
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

    /**
     * Fügt der Besatzung eines großen Schiffes einen Arbeiter hinzu, falls dadurch nicht die maximale Kapazität des
     * Schiffes überschritten wird. Andernfalls wird ein String ausgegeben, der auf die Auslastung des Schiffes
     * hinweist.
     *
     * @param worker Arbeiter-Objekt, das der Besatzung des großen Schiffes hinzugefügt werden soll
     */
    public void receiveWorker(Worker worker) {
        if (crewBig.size() < maxCapacity) {
            crewBig.add(worker);
        }else {
            System.out.println("Ship is full");
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<Worker> departureAll() {
        ArrayList<Worker> temp = new ArrayList<>(crewBig);
        crewBig.clear();
        return temp;
    }

    //@author Louis
    // Output Infomation Schiff (auch siehe ShipSmall)
    public String getShipInformation() {
        int freeCapacity = maxCapacity - crewBig.size();
        String result = "";

        // Output
        result += "Ship ID: " + id + "\n";
        result += "------------------------------\n";
        result += "max.capacity    : " + maxCapacity + "\n";
        result += "used capacity   : " + crewBig.size() + "\n";
        result += "free capacity   : " + freeCapacity + "\n";

        return result;
    }

    /**
     * Vergleicht die IDs des als Parameter mitgegebenen großen Schiffes und des aufrufenden großen Schiffes miteinander.
     *
     * @param o großes Schiff, dessen ID mit der des aufrufenden großen Schiffes verglichen wird
     * @see ShipSmall#compareTo(ShipSmall)
     * @return 1, wenn die ID des aufrufenden großen Schiffes größer als die ID des mitgegebenen großen Schiffes ist;
     *        -1, wenn die ID des aufrufenden großen Schiffes kleiner als die ID des mitgegebenen großen Schiffes ist;
     *         0, wenn die ID des aufrufenden großen Schiffes gleich der ID des mitgegebenen großen Schiffes ist
     */
    @Override
    public int compareTo(ShipBig o) {
        return Integer.compare(this.getId(), o.getId());
    }
}
