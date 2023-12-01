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
    private boolean evacuate = false;

    // Constructor
    // @autor Jonas & Matthias
    public Oilrig(int id, int initialCrew, int initialBigShips, int initialSmallShips) {
        this.id = id;
        this.initialCrew = initialCrew;
        this.initialBigShips = initialBigShips;
        this.initialSmallShips = initialSmallShips;

        //Add Workers to Oilrig
        for (int i = 0; i < initialCrew; i++) {
            this.workersOnPlatform.add(new Worker(i, "Helmut", 30));
        }

        //Add BigShips to Oilrig
        for (int i = 0; i < initialBigShips; i++) {
            this.bigShipsOnPlatform.add(new ShipBig(i*10));
        }

        //Add SmallShips to Oilrig
        for (int i = 0; i < initialSmallShips; i++) {
            this.smallShipsOnPlatform.add(new ShipSmall(i*1000));
        }
    }

    // Get and Set methods
    public int getId() {
        return id;
    }

    public int getBigShipAmount(){
        return bigShipsOnPlatform.size();
    }

    public int getSmallShipAmount(){
        return bigShipsOnPlatform.size();
    }

    //@autor Jonas
    public void addBigShip(ShipBig ship) {
        bigShipsOnPlatform.add(ship);
    }

    public void addSmallShip(ShipSmall ship){
        smallShipsOnPlatform.add(ship);
    }

    public ShipBig getEmptyBigShip() {
        ShipBig temp;
        for (ShipBig ship : bigShipsOnPlatform) {
            if (ship.isEmpty()) {
                temp = ship;
                bigShipsOnPlatform.remove(ship);
                return temp;
            }
        }
        System.out.println("Oilrig id: " + id + " cant get empty ship!");
        return null;
    }

    public ShipSmall getEmptySmallShip() {
        for (ShipSmall ship : smallShipsOnPlatform) {
            if (ship.isEmpty()) {
                return ship;
            }
        }
        System.out.println("Oilrig id: " + id + " cant get empty ship!");
        return null;
    }

    //@author Louis
    public String GetInformationOverview() {

        String result = "";
        int sumOfShips = getBigShipAmount() + getSmallShipAmount();

        result += "Oilrig ID: " + id + "\n";
        result += "------------------------------\n";
        result += "amount of ships docked: " + sumOfShips + "\n";
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
