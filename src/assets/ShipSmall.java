package assets;

import java.util.ArrayList;

public class ShipSmall {

    // Louis: Wo ist Ship Class? Brauche für Methode. GetInfo ist sonst doppelt z.B.

    ArrayList<Worker> crew = new ArrayList<Worker> ();
    private int id;// ID nicht fortlaufend?
    protected int maxCapacity = 50;

    public ShipSmall(int id){
        this.id=id;
    }

    //Get Methods
    public int getId() {
        return id;
    }

    //Add Worker to Crew
    public void receiveWorker(Worker worker) {
        if (crew.size() < maxCapacity) {
            crew.add(worker);
        }else {
            System.out.println("Ship is full");
        }
    }

    public void receiveWorker(ArrayList<Worker> worker) {
        if (crew.size() < maxCapacity) {
            for (int i = 0; i < worker.size(); i++) {
                crew.add(worker.get(i));
            }
        }else {
            System.out.println("Ship is full");
        }
    }

    //Departure Worker from ship and remove him from crew
    public Worker departureWorker(int id) {
        Worker temp = crew.get(id);
        crew.remove(id);
        return temp;
    }

    public ArrayList<Worker> departureAll() {
        ArrayList<Worker> temp = crew;
        crew.clear();
        return temp;
    }

    public boolean isEmpty(){
        if (crew.size() == 0) {
            return true;
        }
        return false;
    }

    //@author Louis
    public String GetShipInformation() {
        int freeCapacity = maxCapacity - crew.size();
        String result = "";
        String workersOnBoard = "|";

        //Integer.toString kann NullPointerException werfen
        for(int i = 0; i < crew.size(); i++) {
            try{
                workersOnBoard += Integer.toString(crew.get(i).getId()) + "|";
            }catch(NullPointerException npe){
                System.out.println(npe.getMessage());
            }
        }
        //Output
        result += "Ship ID: " + id + "\n";
        result += "------------------------------\n";
        result += "max.capacity    : " + maxCapacity + "\n";
        result += "used capacity   : " + crew.size() + "\n";
        result += "free capacity   : " + freeCapacity + "\n";
        result += "workers on board: " + workersOnBoard + "\n";

        return result;
    }

}
