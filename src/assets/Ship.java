package assets;

import java.util.ArrayList;

//@author Matthias
public abstract class Ship {
    ArrayList<Worker> crew = new ArrayList<Worker> ();
    private int capacity = crew.size();

    //für die Ausgabe
    private int id;
    protected int maxCapacity;
    private int freeCapacity = maxCapacity - capacity;

    public void receiveWorker(Worker worker) {
        if (checkCapacity(crew.size(), 1)) {
            crew.add(worker);
        }else {
            System.out.println("SHIP is full");
        }
    }

    public int getId() {
        return id;
    }

    public void receiveWorker(ArrayList<Worker> worker) {
        if (checkCapacity(crew.size(), worker.size())) {
            for (int i = 0; i < worker.size(); i++) {
                crew.addAll(worker);
            }
        }else {
            System.out.println("SHIP is full");
        }
    }

    public Worker departureWorker(int id) {
        return crew.get(id);
    }

    public ArrayList<Worker> departureAll() {
        return crew;
    }

    public boolean isEmpty(){
        if (capacity == 0 ) return true;
        else return false;
    }

    public abstract boolean checkCapacity(int currently, int numberofworker); {

    }

    //@author Louis
    public String GetShipInformation() {
        String result = "";
        String workersOnBoard = "|";

        for(int i = 0; i < capacity; i++) {
            workersOnBoard += crew.get(i).getId();
        }

        //Integer.toString(workersOnPlatform.get(i).getId()) + "|";

        result += "Ship ID: " + id + "\n";
        result += "------------------------------\n";
        result += "max.capacity    : " + maxCapacity + "\n";
        result += "used capacity   : " + capacity + "\n";
        result += "free capacity   : " + freeCapacity + "\n";
        result += "workers on board: " + workersOnBoard + "\n";

        return result;
    }


}
