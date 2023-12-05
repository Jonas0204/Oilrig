package assets;

import javax.swing.*;
import java.util.ArrayList;

import static programm.Methods.getCounterShips;

public class ShipBig extends Ship{

    ArrayList<Worker> crewBig = new ArrayList<Worker> ();
    protected final int maxCapacity = 100;

    //@author Jonas, Louis
    // Constructor
    public ShipBig(int id){
        this.id = getCounterShips();
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
        ArrayList<Worker> temp = new ArrayList<Worker>(crewBig);
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
}
