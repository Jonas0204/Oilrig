package assets;

import java.util.ArrayList;

import Programm.InputOutput;
import Programm.InputOutput.*;

public class Oilrig {

    private final int id;
    private ArrayList<Worker> workersOnPlatform = new ArrayList<Worker>();
    private ArrayList<ShipSmall> smallShipsOnPlatform = new ArrayList<ShipSmall>();
    private ArrayList<ShipBig> bigShipsOnPlatform = new ArrayList<ShipBig>();
    private final int initialCrew;
    private final int initialBigShips;
    private final int initialSmallShips;
    private boolean evacuate; //kann man eigentlich löschen

    // Get and Set methods
    public int getId() {
        return id;
    }

    public int getNumberofWorkers() {
        return workersOnPlatform.size();
    }

    public int getNumberofSmallShips() {
        return smallShipsOnPlatform.size();
    }

    public int getNumberofBigShips() {
        return bigShipsOnPlatform.size();
    }

    public boolean getOnFire() {
        return evacuate;
    }

    public void setOnFire() {
        this.evacuate = true;
    }

    //@autor Jonas
    public void addShip(ShipSmall ship) {
        smallShipsOnPlatform.add(ship);
    }
    public void addShip(ShipBig ship) {
        bigShipsOnPlatform.add(ship);
    }

    public ShipBig getEmptyBigShip() {
        for (ShipBig ship : bigShipsOnPlatform) {
            if (ship.isEmpty()) {
                return ship;
            }
        }
        return null;
    }

    private boolean TotalShipCountBiggerOne(){
        int i = bigShipsOnPlatform.size() + smallShipsOnPlatform.size();
        if (i < 1) {
            return true;
        }
        else return false;
    }

    private boolean BigShipCountBelowMax(){
        int maxAllowedShips = initialBigShips * 2;
        int dockedShips = bigShipsOnPlatform.size();
        if (dockedShips < maxAllowedShips) {
            return true;
        }
        else return false;
    }


    public void moveEmptyBigShip(int numberOfWorkers,  /*startPlatform,*/ Oilrig endPlatform, Ship movingShip) {
        ShipBig ship = getEmptyBigShip();
        if (ship == null) {
            System.out.println("Kein leeres Schiff vorhanden");
            return;
        }
        //Bedingung IV) Keine Plattform darf weniger als ein Versorgungschiff haben, außer im Falle einer Evakuierung.
        if (!TotalShipCountBiggerOne()) {
            System.out.println("Die Anzahl der Schiffe darf nicht unter 1 fallen"); //Möglicherweise nach Evakuierung unterscheiden
        }
        if (!BigShipCountBelowMax()) {
            System.out.println("Die Zielplattform kann keine großen Schiffe mehr entgegen nehmen!");
        }
        addShip(ship);
        //not done
    }

    // Print all ships on Oilplatform

    // Constructor
    // @autor Jonas & Matthias
    public Oilrig(int id, int workerToBegin, int initialBigShips, int initialSmallShips) {
        this.id = id;
        this.initialCrew = workerToBegin;
        this.initialBigShips = initialBigShips;
        this.initialSmallShips = initialSmallShips;

        int shipIdCounter = InputOutput.getShipCounter();

        for (int i = 1; i < workerToBegin; i++) {
            this.workersOnPlatform.add(new Worker(i, "helmut", 30));
        }


        for (int i = shipIdCounter; i <= initialSmallShips; i++) {
            this.smallShipsOnPlatform.add(new ShipSmall());
            shipIdCounter++;
            InputOutput.addShipCounter();
        }

        for (int i = shipIdCounter; i <= initialBigShips; i++) {
            this.bigShipsOnPlatform.add(new ShipBig());
            shipIdCounter++;
            InputOutput.addShipCounter();
        }
    }

    // Platform id 1-4,
    // bigship id 1-5,
    // 14


    //@author Louis
    public void Evacuate(Oilrig or) {
        int endangeredWorkers = or.workersOnPlatform.size();
        int shipsOnSite = or.smallShipsOnPlatform.size() + or.bigShipsOnPlatform.size();
        int availableCapacity = 0;
        int neededCapacityFromOutside;

        for(int i = 0; i < smallShipsOnPlatform.size(); i++){
            availableCapacity += or.smallShipsOnPlatform.get(i).maxCapacity - or.smallShipsOnPlatform.get(i).crew.size();
        }
        for(int i = 0; i < bigShipsOnPlatform.size(); i++){
            availableCapacity += or.bigShipsOnPlatform.get(i).maxCapacity - or.bigShipsOnPlatform.get(i).crew.size();
        }

        neededCapacityFromOutside = endangeredWorkers - availableCapacity;

        if(neededCapacityFromOutside <= 0){
            System.out.println("Schiffe von Platform genügen zum evakuieren");
        }
        else{
            System.out.println("Schiffe von anderen Plattformen werden benötigt");
        }



    }


    /*
     *
     * main (String[] args){
     * 		Oilplatform ONE = new Oilplattform(22, 100, 3, 5);
     * 		Oilplatform TWO = new Oilplattform(22, 100, 3, 5);
     * 		System.out.println(ONE.GetInformation());
     * }
     *
     */


    //@author Louis
    public String GetInformationOverview() {

        String result = "";

        result += "Oilrig ID: " + id + "\n";
        result += "------------------------------\n";
        result += "amount of ships docked: " + bigShipsOnPlatform.size() + smallShipsOnPlatform.size() + "\n";
        result += "          big ships   : " + bigShipsOnPlatform.size() + "\n";
        result += "          small ships : " + smallShipsOnPlatform.size() + "\n";
        result += "amount of workers     : " + workersOnPlatform.size() + "\n";

        return result;


    }
    public String GetInformationOilrig() {

        String result = "";
        String bigShipsOnPlatformString = "|";
        String smallShipsOnPlatformString = "|";
        String workersOnPlatformString = "|";

        for(int i = 0; i < bigShipsOnPlatform.size(); i++) {
            bigShipsOnPlatformString += Integer.toString(bigShipsOnPlatform.get(i).getId()) + "|"; //Holt ID des Objektes an der Stelle [i] aus Liste bigShipsOnPlatform und konvertiert zu string
        }

        for(int i = 0; i < smallShipsOnPlatform.size(); i++) {
            smallShipsOnPlatformString += Integer.toString(smallShipsOnPlatform.get(i).getId()) + "|";
        }

        for(int i = 0; i < workersOnPlatform.size(); i++) {
            workersOnPlatformString += Integer.toString(workersOnPlatform.get(i).getId()) + "|";
        }

        result += "Oilrig ID: " + id + "\n";
        result += "---------------------------------------------------------------------------------------------------------\n";
        result += "big ships   : " + bigShipsOnPlatformString + "\n";
        result += "small ships : " + smallShipsOnPlatformString + "\n";
        result += "workers     : " + workersOnPlatformString + "\n";

        return result;
    }


}
