package assets;

import java.util.ArrayList;

import static programm.Methods.getCounterShips;

public class ShipSmall extends Ship implements Comparable<ShipSmall>{

    ArrayList<Worker> crewSmall = new ArrayList<>();
    protected final int maxCapacity = 50;

    //@author Louis, Jonas
    // Constructor
    public ShipSmall() {
        this.id = getCounterShips();
    }

    // Die negative ID stellt klar, dass das nur ein theoretischer Platzhalter ist
    public ShipSmall(int id){
        this.id = -id;
    }

    //@author Matthias
    // fügt Arbeiter der Crew hinzu
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

    //@author Nino, Jonas
    // Sortierung für getInformationOilrig()
    @Override
    public int compareTo(ShipSmall o) {
        return Integer.compare(this.getId(), o.getId());
    }
}
