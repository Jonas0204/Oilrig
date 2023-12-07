package assets;

import java.util.ArrayList;
import static programm.Methods.getCounterShips;

public class ShipBig extends Ship implements Comparable<ShipBig>{

    ArrayList<Worker> crewBig = new ArrayList<>();
    protected final int maxCapacity = 100;

    //@author Jonas, Louis
    // Constructor
    public ShipBig(){
        this.id = getCounterShips();
    }

    // Die negative ID stellt klar, dass das nur ein theoretischer Platzhalter ist
    public ShipBig(int id){
        this.id = -id;
    }

    //@author Matthias
    // fügt Arbeiter der Crew hinzu
    public void receiveWorker(Worker worker) {
        if (crewBig.size() < maxCapacity) {
            crewBig.add(worker);
        }else {
            System.out.println("Ship is full");
        }
    }

    //@author Matthias
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

    //@author Nino, Jonas
    // Sortierung für getInformationOilrig()
    @Override
    public int compareTo(ShipBig o) {
        return Integer.compare(this.getId(), o.getId());
    }
}
