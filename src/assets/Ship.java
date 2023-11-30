package assets;

import java.util.ArrayList;

//@author Matthias
public abstract class Ship {
    ArrayList<Worker> crew = new ArrayList<Worker> ();
    private int id;
    protected int maxCapacity;

    //Get Methods
    public int getId() {
        return id;
    }

    //Add Worker to Crew
    public void receiveWorker(Worker worker) {
        if (checkCapacity(crew.size(), 1)) {
            crew.add(worker);
        }else {
            System.out.println("Ship is full");
        }
    }

    public void receiveWorker(ArrayList<Worker> worker) {
        if (checkCapacity(crew.size(), worker.size())) {
            for (int i = 0; i < worker.size(); i++) {
                crew.addAll(worker);
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

    public abstract boolean checkCapacity(int currently, int numberofworker); {

    }

    //@author Louis
    public String GetShipInformation() {
        int freeCapacity = maxCapacity - crew.size();
        String result = "";
        String workersOnBoard = "|";

        for(int i = 0; i < crew.size(); i++) {
            workersOnBoard += Integer.toString(crew.get(i).getId()) + "|";
        }

        result += "Ship ID: " + id + "\n";
        result += "------------------------------\n";
        result += "max.capacity    : " + maxCapacity + "\n";
        result += "used capacity   : " + crew.size() + "\n";
        result += "free capacity   : " + freeCapacity + "\n";
        result += "workers on board: " + workersOnBoard + "\n";

        return result;
    }
}
