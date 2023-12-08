package assets;

import programm.Methods;

/**
 * Die abstrakte Klasse Ship dient als Basisklasse für ShipBig und ShipSmall.
 * Sie enthält grundlegende Methoden und Eigenschaften, die von Schiffen gemeinsam genutzt werden.
 * Was leider nicht viele sind.
 *
 * @author Jonas Hülse, Louis Schadewaldt
 */
public abstract class Ship{

    protected int id;

    /**
     * Gibt die ID dieses Schiffes zurück.
     *
     * @return Die eindeutige ID des Schiffes
     */
    public int getId() {
        return id;
    }

    /**
     * Ermittelt die ID der Ölplattform, an der das Schiff mit der angegebenen ID ursprünglich angedockt ist.
     *
     * @param id Die ID des zu suchenden Schiffs
     * @return Die ID der Ölplattform, an der das Schiff angedockt ist. Gibt -1 zurück, wenn das Schiff nicht gefunden wurde.
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
