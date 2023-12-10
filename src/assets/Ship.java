package assets;

import programm.Manager;
import java.util.ArrayList;

/**
 * Die abstrakte Klasse Ship dient als Basisklasse für ShipBig und ShipSmall.
 * Sie enthält grundlegende Methoden und Eigenschaften, die von Schiffen gemeinsam genutzt werden.
 *
 * @author Jonas Hülse, Louis Schadewaldt
 */
public abstract class Ship implements Comparable<Ship>{

    protected int id;
    private final ArrayList<Worker> crew = new ArrayList<>();
    protected int maxCapacity;

    /**
     * Gibt die ID dieses Schiffes zurück.
     *
     * @return Die eindeutige ID des Schiffes
     */
    public int getId() {
        return id;
    }

    /**
     * Fügt der Besatzung eines Schiffes einen Arbeiter hinzu, falls dadurch nicht die maximale Kapazität des
     * Schiffes überschritten wird. Andernfalls wird ein String ausgegeben, der auf die Auslastung des Schiffes
     * hinweist.
     *
     * @param worker Arbeiter-Objekt, das der Besatzung des Schiffes hinzugefügt werden soll
     */
    public void receiveWorker(Worker worker) {
        if (crew.size() < maxCapacity) {
            crew.add(worker);
        }else {
            System.out.println("Ship is full");
        }
    }

    /**
     * Entlädt alle Arbeiter vom Schiff und gibt eine Liste der entlassenen Arbeiter zurück.
     *
     * @return Eine Liste aller entlassenen Arbeiter
     */
    public ArrayList<Worker> departureAll() {
        ArrayList<Worker> temp = new ArrayList<>(crew);
        crew.clear();
        return temp;
    }

    /**
     * Gibt Informationen über das Schiff in einem übersichtlichen Format zurück.
     *
     * @return Zeichenfolge mit Details zur Kapazität des Schiffes
     */
    public String getShipInformation() {
        int freeCapacity = maxCapacity - crew.size();
        String result = "";

        // Output
        result += "Ship ID: " + id + "\n";
        result += "------------------------------\n";
        result += "max.capacity    : " + maxCapacity + "\n";
        result += "used capacity   : " + crew.size() + "\n";
        result += "free capacity   : " + freeCapacity + "\n";

        return result;
    }

    /**
     * Ermittelt die ID der Ölplattform, an der das Schiff mit der angegebenen ID angedockt ist.
     *
     * @param id ID des zu suchenden Schiffes
     * @return ID der Ölplattform, an der das Schiff angedockt ist. Gibt -1 zurück, wenn das Schiff nicht gefunden wurde.
     */
    public static int getShipOriginID(int id){
        for (Oilrig temp : Manager.getAllOilrigs()) {
            Ship tempShipById = temp.getShipById(id);
            if (tempShipById != null && tempShipById.getId() == id){
                return temp.getId();
            }
        }
        return -1; // Steht für fehlgeschlagene Methode, sollte aber nicht vorkommen
    }

    /**
     * Vergleicht die IDs des als Parameter mitgegebenen Schiffes und des aufrufenden Schiffes miteinander.
     *
     * @param o Schiff, dessen ID mit der des aufrufenden Schiffes verglichen wird
     * @return 1, wenn die ID des aufrufenden Schiffes größer als die ID des mitgegebenen Schiffes ist;
     *        -1, wenn die ID des aufrufenden Schiffes kleiner als die ID des mitgegebenen Schiffes ist;
     *         0, wenn die ID des aufrufenden Schiffes gleich der ID des mitgegebenen Schiffes ist
     */
    @Override
    public int compareTo(Ship o) {
        return Integer.compare(this.getId(), o.getId());
    }
}
