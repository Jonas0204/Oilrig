package assets;

import programm.Methods;

/**
 * @author Jonas Hülse, Louis Schadewaldt
 */
public abstract class Ship{

    protected int id;

    /**
     * Getter-Methode für die ID eines Schiffes
     *
     * @return gibt die ID des Schiffes zurück
     */
    public int getId() {
        return id;
    }

    /**
     *
     *
     * @param id Integer, der die ID des Schiffes angibt
     * @return
     */
    public static int getShipOriginID(int id){
        for (Oilrig temp : Methods.getAllOilrigs()) {
            Ship tempShipById = temp.getShipById(id);
            if (tempShipById != null && tempShipById.getId() == id){
                return temp.getId();
            }
        }
        return -1; // Steht für fehlgeschlagene Methode, sollte aber nicht vorkommen
    }
}
