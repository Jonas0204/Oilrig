package assets;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;

import Programm.InputOutput;
import Programm.InputOutput.*;
import Programm.Methods;

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

    public int getInitialBigShips() {
        return initialBigShips;
    }

    public int getInitialSmallShips() {
        return initialSmallShips;
    }

    public ShipBig getShipBigByID(int id){
        ShipBig temp;
        for (ShipBig i : bigShipsOnPlatform) {
            if(i.getId() == id){
                temp = i;
                bigShipsOnPlatform.remove(temp);
                return temp;
            }
        }
        System.out.println("ShipBig not found!!");
        return null;
    }

    public ShipSmall getShipSmallByID(int id){
        ShipSmall temp;
        for (ShipSmall i : smallShipsOnPlatform) {
            if(i.getId() == id){
                temp = i;
                smallShipsOnPlatform.remove(temp);
                return temp;
            }
        }
        System.out.println("ShipSmall not found!!");
        return null;
    }

    public void addBigShip(ShipBig ship) {
        for(Worker i : ship.crew){
            workersOnPlatform.add(i);
        }
        ship.crew.clear();
        bigShipsOnPlatform.add(ship);
    }

    public void addSmallShip(ShipSmall ship){
        for(Worker i : ship.crew){
            workersOnPlatform.add(i);
        }
        ship.crew.clear();
        smallShipsOnPlatform.add(ship);
    }

    public void sendBigShip(Oilrig destination, int crew, boolean evacuate){
        if(crew <= ShipBig.maxCapacity){
            if(!evacuate){
                if((workersOnPlatform.size() - crew) > initialCrew*0.1 && bigShipsOnPlatform.size() > 1){
                    if((destination.workersOnPlatform.size() + crew) < destination.workersOnPlatform.size() * 2){
                        if((destination.initialBigShips + 4) >= (destination.bigShipsOnPlatform.size() + 1))   {
                            System.out.println(GetSendingInformation(100, crew, id, destination.id, smallShipsOnPlatform.get(1).getId()));
                            for (int i = 0; i < crew; i++) {
                                bigShipsOnPlatform.get(1).receiveWorker(workersOnPlatform.get(i));
                                workersOnPlatform.remove(i);
                            }
                            destination.addBigShip(bigShipsOnPlatform.get(1));
                            bigShipsOnPlatform.remove(1);
                        }else{
                            System.out.println("Cant send, sending will exceed the max capacity of Bigships");
                        }
                    }else{
                        System.out.println("Cant send, sending will exceed capacity of destination");
                    }
                }else{
                    System.out.println("Cant send, sending will fall below initial capacity of workers or the number of big ships on this plattform will fall below 1");
                }
            }else{
                //EVACUATE
            }
        }else{
            System.out.println("The crew to be sent exceeds the capacity of the ship");
        }
    }

    public void sendSmallShip(Oilrig destination, int crew, boolean evacuate){

        if(crew <= ShipSmall.maxCapacity){
            if(!evacuate){
                if((workersOnPlatform.size() - crew) > initialCrew*0.1 && smallShipsOnPlatform.size() > 1){
                    if((destination.workersOnPlatform.size() + crew) < destination.workersOnPlatform.size() * 2){
                        if((destination.initialSmallShips + 4) >= (destination.smallShipsOnPlatform.size() + 1))   {
                            System.out.println(GetSendingInformation(50, crew, id, destination.id, smallShipsOnPlatform.get(1).getId()));
                            for (int i = 0; i < crew; i++) {
                                smallShipsOnPlatform.get(1).receiveWorker(workersOnPlatform.get(i));
                                workersOnPlatform.remove(i);
                            }
                            destination.addSmallShip(smallShipsOnPlatform.get(1));
                            smallShipsOnPlatform.remove(1);
                        }else{
                            System.out.println("Cant send, sending will exceed the max capacity of Smallships");
                        }
                    }else{
                        System.out.println("Cant send, sending will exceed capacity of destination");
                    }
                }else{
                    System.out.println("Cant send, sending will fall below initial capacity of workers or the number of small ships on this plattform will fall below 1");
                }
            }else{
                //EVACUATE
            }
        }else{
            System.out.println("The crew to be sent exceeds the capacity of the ship");
        }
    }

    public String GetInformationOverview() {

        String result = "";
        int sumOfShips = bigShipsOnPlatform.size() + smallShipsOnPlatform.size();

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

        for(int i = 0; i < bigShipsOnPlatform.size(); i++) {
            bigShipsOnPlatformString += Integer.toString(bigShipsOnPlatform.get(i).getId()) + "|"; //Holt ID des Objektes an der Stelle [i] aus Liste bigShipsOnPlatform und konvertiert zu string
        }

        for(int i = 0; i < smallShipsOnPlatform.size(); i++) {
            smallShipsOnPlatformString += Integer.toString(smallShipsOnPlatform.get(i).getId()) + "|";
        }

        result += "Oilrig ID: " + id + "\n";
        result += "---------------------------------------------------------------------------------------------------------\n";
        result += "big ships   : " + bigShipsOnPlatformString + "\n";
        result += "small ships : " + smallShipsOnPlatformString + "\n";
        result += "workers     : " + "|" + workersOnPlatform.size() + "|" + "\n";

        return result;
    }

    public String GetSendingInformation(int max, int send, int sender, int receiver, int shipid){
        String result = "";

        result += "--------------Sending Ship----------------\n";
        result += "Oilrig: " + sender + " ➡➡ " + "Ship ID: " + shipid + " ➡➡ " + "Destination Oilrig: " + receiver + "\n";
        result += "------------------------------------------\n";
        if(max == 100){
            result += "Type of Ship          : " + "Big Ship" + "\n";
        }else{
            result += "Type of Ship          : " + "Small Ship" + "\n";
        }
        result += "      Maximum Capacity: " + max + "\n";
        result += "         Used Capacity: " + send + "\n";
        return result;
    }
}
