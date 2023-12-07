package assets;

import programm.Methods;
public abstract class Ship{

    protected int id;
    // int shipIsOnOilrigId oder Oilrig shipIsOnOilrig

    public int getId() {
        return id;
    }

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
