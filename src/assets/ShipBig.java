package assets;

import java.util.ArrayList;

public class ShipBig {

    ArrayList<Worker> crew = new ArrayList<Worker> ();
    private int id; // ID nicht fortlaufend?
    protected int maxCapacity = 100;

    public ShipBig(int id){
        this.id=id;
    }

    //Get Methods
    public int getId() {
        return id;
    }

    //Add Worker to Crew
    public void receiveWorker(Worker worker) {
        if (crewBig.size() < maxCapacity) {
            crewBig.add(worker);
        }else {
            System.out.println("Ship is full");
        }
    }

    public void receiveWorkerlist(ArrayList<Worker> worker) {
        if (crewBig.size() < maxCapacity) {
            for (int i = 0; i < worker.size(); i++) {
                crewBig.add(worker.get(i));
            }
        }else {
            System.out.println("Ship is full");
        }
    }

    //Departure Worker from ship and remove him from crew
    public Worker departureWorker(int id) {
        Worker temp = crewBig.get(id);
        crewBig.remove(id);
        return temp;
    }

    public ArrayList<Worker> departureAll() {
        ArrayList<Worker> temp = crew;
        crew.clear();
        return temp;
    }

    public boolean isEmpty(){
        if (crewBig.size() == 0) {
            return true;
        }
        return false;
    }

    public int compareToShipBig(ShipBig otherShipBig){
        if (this.id < otherShipBig.id){
            return -1;
        } else if (this.id > otherShipBig.id){
            return 1;
        } else{
            return 0;
        }
    }

    public String GetShipInformation() {
        int freeCapacity = maxCapacity - crewBig.size();
        String result = "";

        //Output
        result += "Ship ID: " + id + "\n";
        result += "------------------------------\n";
        result += "max.capacity    : " + maxCapacity + "\n";
        result += "used capacity   : " + crewBig.size() + "\n";
        result += "free capacity   : " + freeCapacity + "\n";

        return result;
    }
}
