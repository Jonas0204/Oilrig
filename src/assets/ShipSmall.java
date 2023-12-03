package assets;

import java.util.ArrayList;

public class ShipSmall {

    ArrayList<Worker> crewSmall = new ArrayList<Worker> ();
    private final int id;
    protected final int maxCapacity = 50;

    public ShipSmall(int id){
        this.id = id;
    }

    //Get Methods
    public int getId() {
        return id;
    }

    //Add Worker to Crew
    public void receiveWorker(Worker worker) {
        if (crewSmall.size() < maxCapacity) {
            crewSmall.add(worker);
        }else {
            System.out.println("Ship is full");
        }
    }

    public void receiveWorker(ArrayList<Worker> worker) {
        if (crewSmall.size() < maxCapacity) {
            for (int i = 0; i < worker.size(); i++) {
                crewSmall.add(worker.get(i));
            }
        }else {
            System.out.println("Ship is full");
        }
    }

    //Departure Worker from ship and remove him from crew
    public Worker departureWorker(int id) {
        Worker temp = crewSmall.get(id);
        crewSmall.remove(id);
        return temp;
    }
    public ArrayList<Worker> departureAll() {
        ArrayList<Worker> temp = new ArrayList<Worker>(crewSmall);
        crewSmall.clear();
        return temp;
    }

    public boolean isEmpty(){
        if (crewSmall.size() == 0) {
            return true;
        }
        return false;
    }

    public int compareToShipSmall(ShipSmall otherShipSmall){
        if (this.id < otherShipSmall.id){
            return -1;
        } else if (this.id > otherShipSmall.id){
            return 1;
        } else{
            return 0;
        }
    }

    //@author Louis
    public String GetShipInformation() {
        int freeCapacity = maxCapacity - crewSmall.size();
        String result = "";
        String workersOnBoard = "|";

        //Integer.toString kann NullPointerException werfen
        for(int i = 0; i < crewSmall.size(); i++) {
            try{
                workersOnBoard += Integer.toString(crewSmall.get(i).getId()) + "|";
            }catch(NullPointerException npe){
                System.out.println(npe.getMessage());
            }
        }
        //Output
        result += "Ship ID: " + id + "\n";
        result += "------------------------------\n";
        result += "max.capacity    : " + maxCapacity + "\n";
        result += "used capacity   : " + crewSmall.size() + "\n";
        result += "free capacity   : " + freeCapacity + "\n";

        return result;
    }
}
