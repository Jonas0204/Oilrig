package assets;

import java.util.ArrayList;
import static programm.Methods.getCounterShips;

/**
 *
 */
public class ShipSmall extends Ship implements Comparable<ShipSmall>{

    private ArrayList<Worker> crewSmall = new ArrayList<>();
    protected final int maxCapacity = 50;

    /**
     *
     */
    public ShipSmall() {
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

    /**
     * Fügt der Besatzung eines kleinen Schiffes einen Arbeiter hinzu, falls dadurch nicht die maximale Kapazität des
     * Schiffes überschritten wird. Andernfalls wird ein String ausgegeben, der auf die Auslastung des Schiffes
     * hinweist.
     *
     * @param worker Arbeiter-Objekt, das der Besatzung des kleinen Schiffes hinzugefügt werden soll
     */
    public void receiveWorker(Worker worker) {
        if (crewSmall.size() < maxCapacity) {
            crewSmall.add(worker);
        }else {
            System.out.println("Ship is full");
        }
    }

    //@author Matthias
    public ArrayList<Worker> departureAll() {
        ArrayList<Worker> temp = new ArrayList<>(crewSmall);
        crewSmall.clear();
        return temp;
    }

    //@author Louis
    // Output Infomation Schiff (auch siehe ShipBig)
    public String getShipInformation() {
        int freeCapacity = maxCapacity - crewSmall.size();
        String result = "";

        // Output
        result += "Ship ID: " + id + "\n";
        result += "------------------------------\n";
        result += "max.capacity    : " + maxCapacity + "\n";
        result += "used capacity   : " + crewSmall.size() + "\n";
        result += "free capacity   : " + freeCapacity + "\n";

        return result;
    }

    /**
     * Vergleicht die IDs des als Parameter mitgegebenen kleinen Schiffes und des aufrufenden kleinen Schiffes miteinander.
     *
     * @param o kleines Schiff, dessen ID mit der des aufrufenden großen Schiffes verglichen wird
     * @see ShipBig#compareTo(ShipBig)
     * @return 1, wenn die ID des aufrufenden kleinen Schiffes größer als die ID des mitgegebenen kleinen Schiffes ist;
     *        -1, wenn die ID des aufrufenden kleinen Schiffes kleiner als die ID des mitgegebenen kleinen Schiffes ist;
     *         0, wenn die ID des aufrufenden kleinen Schiffes gleich der ID des mitgegebenen kleinen Schiffes ist
     */
    @Override
    public int compareTo(ShipSmall o) {
        return Integer.compare(this.getId(), o.getId());
    }
}
