package assets;

import programm.Methods;
import java.util.ArrayList;
import java.util.Collections;

public class Oilrig{

    private final int id;
    public ArrayList<Worker> workersOnOilrig = new ArrayList<Worker>();
    public ArrayList<ShipSmall> smallShipsOnOilrig = new ArrayList<ShipSmall>();
    public ArrayList<ShipBig> bigShipsOnOilrig = new ArrayList<ShipBig>();
    public final int initialCrewOilrig;
    private final int initialBigShips;
    private final int initialSmallShips;
    public boolean evacuate = false;

    // Constructor
    // @autor Jonas & Matthias
    public Oilrig(Oilrig or){
        this.id = or.id;
        this.initialCrewOilrig = or.initialCrewOilrig;
        this.initialBigShips =  or.initialBigShips;
        this.initialSmallShips = or.initialSmallShips;
        this.workersOnOilrig = new ArrayList<>(or.workersOnOilrig);
        this.smallShipsOnOilrig = new ArrayList<>(or.smallShipsOnOilrig);
        this.bigShipsOnOilrig = new ArrayList<>(or.bigShipsOnOilrig);
    }
    public Oilrig(int id, int initialCrew, int initialBigShips, int initialSmallShips) {
        this.id = id;
        this.initialCrewOilrig = initialCrew;
        this.initialBigShips = initialBigShips;
        this.initialSmallShips = initialSmallShips;

        //Add Workers to Oilrig
        // @see Methods.getCounterShips @autor Jonas
        // Debug System.out.println(String.valueOf(Methods.getCounterWorker()) + " < " + String.valueOf(initialCrew + Methods.getCounterWorker()));

        int initMax = initialCrew + Methods.getCounterWorker();

        for (int i = Methods.getCounterWorker(); i < initMax; i++) {
            this.workersOnOilrig.add(new Worker(i));
            Methods.addCounterWorker();
        }

        initMax = initialBigShips + Methods.getCounterShips();
        //Add BigShips to Oilrig
        // @see Methods.getCounterShips
        for (int i = Methods.getCounterShips(); i < initMax; i++) {
            this.bigShipsOnOilrig.add(new ShipBig(i));
            Methods.addCounterShips();
        }

        initMax = initialSmallShips + Methods.getCounterShips();
        //Add SmallShips to Oilrig
        for (int i = Methods.getCounterShips(); i < initMax; i++) {
            this.smallShipsOnOilrig.add(new ShipSmall(i));
            Methods.addCounterShips();
        }
    }

    //@author Matthias
    // Get and Set methods
    public int getId() {
        return id;
    }
    public int getBigShipAmount(){
        return this.bigShipsOnOilrig.size();
    }
    public int getSmallShipAmount(){
        return this.bigShipsOnOilrig.size();
    }
    public int getWorkerAmount(){
        return this.workersOnOilrig.size();
    }
    public ArrayList<Worker> getWorkersOnOilrig(){
        System.out.println("3:" + Methods.getPlatByID(1).getWorkerAmount());
        return this.workersOnOilrig;
    }

    public Oilrig getCopy(Oilrig senderOr){
        Oilrig temp = new Oilrig(senderOr);
        return temp;
    }

    //@autor Jonas
    public void addBigShip(ShipBig ship) {
        bigShipsOnOilrig.add(ship);
    }
    public void addSmallShip(ShipSmall ship){
        smallShipsOnOilrig.add(ship);
    }

    //@author Jonas
    public ShipSmall getSmallShipById(int id){
        for (ShipSmall ship : smallShipsOnOilrig){
            if (ship.getId() == id) return ship;
        }
        return null;
    }
    public ShipBig getBigShipById(int id){
        for (ShipBig ship : bigShipsOnOilrig) {
            if (ship.getId() == id) return ship;
        }
        return null;
    }
    //vereint getSmallShipById(int id) und getBigShipById(int id)
    //@author Louis
    public Ship getShipById(int id){
        for (Ship ship : bigShipsOnOilrig) {
            if (ship.getId() == id) return ship;
        }
        for (Ship ship : smallShipsOnOilrig) {
            if (ship.getId() == id) return ship;
        }
        return null;
    }

    //@author Jonas
    //check-Methoden
    public boolean checkTotalShipCountBiggerOne(){
        int i = bigShipsOnOilrig.size() + smallShipsOnOilrig.size();
        return i >= 1;
    }
    private boolean checkBigShipCountBelowMax(){
        int maxAllowedShips = initialBigShips * 2;
        int dockedShips = bigShipsOnOilrig.size();
        return dockedShips < maxAllowedShips;
    }
    private boolean checkSmallShipCountBelowMax(){
        int maxAllowedShips = initialSmallShips * 2;
        int dockedShips = smallShipsOnOilrig.size();
        return dockedShips < maxAllowedShips;
    }
    //return: kann Platform receiver ein kleines Schiff aufnehmen
    public boolean checkOilrigCanReceiveSmallShip(){
        return this.getSmallShipAmount() + 1 <= this.initialSmallShips + 4;
    }
    //return: kann Platform receiver ein grosses Schiff aufnehmen
    public boolean checkOilrigCanReceiveBigShip(){
        return this.getBigShipAmount() + 1 <= this.initialBigShips + 4;
    }

    //@author Jonas
    //Hilfsmethoden zum bewegen eines Schiffs
    public void transferWorkerOilrigToShip(int amount, ShipSmall ship) {
        for (int i = 1; i <= amount; i++){
            Worker tempWorker = workersOnOilrig.get(0); // i oder 0, wenn das Objekt gelöscht wird ändert sich die Liste
            workersOnOilrig.remove(0);
            ship.receiveWorker(tempWorker);
        }
    }
    public void transferWorkerOilrigToShip(int amount, ShipBig ship) {
        for (int i = 1; i <= amount; i++){
            Worker tempWorker = workersOnOilrig.get(0); // i oder 0, wenn das Objekt gelöscht wird ändert sich die Liste
            workersOnOilrig.remove(0);
            ship.receiveWorker(tempWorker);
        }
    }
    public void transferAllWorkerShipToOilrig(ShipSmall ship){
        ArrayList<Worker> wTransfer = ship.departureAll();
        workersOnOilrig.addAll(wTransfer);
    }
    public void transferAllWorkerShipToOilrig(ShipBig ship){
        ArrayList<Worker> wTransfer = ship.departureAll();
        workersOnOilrig.addAll(wTransfer);
    }
    public void undockShip(ShipSmall ship){
        int id = ship.getId();
        for (int i = 0; i < smallShipsOnOilrig.size(); i++) {
            if (smallShipsOnOilrig.get(i).getId() == id) {
                try{
                    smallShipsOnOilrig.remove(i);
                }catch (IndexOutOfBoundsException oobe){
                    System.out.println("an error occurred: " + oobe.getMessage());
                }
            }
        }
    }
    public void undockShip(ShipBig ship){
        int id = ship.getId();
        for (int i = 0; i < bigShipsOnOilrig.size(); i++) {
            if (bigShipsOnOilrig.get(i).getId() == id) {
                try{
                    bigShipsOnOilrig.remove(i);
                }catch (IndexOutOfBoundsException oobe){
                    System.out.println("an error occurred: " + oobe.getMessage());
                }
            }
        }
    }
    public void dockShip(ShipSmall ship){
        smallShipsOnOilrig.add(ship);
    }
    public void dockShip(ShipBig ship){
        bigShipsOnOilrig.add(ship);
    }

    // UserInterface
    //@author Louis
    // Output Information aller Ölplattformen (hier nur eine, alle über Schleife gelöst siehe handleInput()
    public String getInformationOverview() {

        String result = "";

        // Output
        result += "Oilrig ID: " + id + "\n";
        result += "------------------------------\n";
        result += "amount of ships docked: " + ( bigShipsOnOilrig.size() + smallShipsOnOilrig.size() ) + "\n";  // int sumOfShips = getBigShipAmount() + getSmallShipAmount();   // Fehlerhaft, 3+4 ist nicht 6
        result += "          big ships   : " + bigShipsOnOilrig.size() + "\n";                                  //int sumOfShips = bigShipsOnPlatform.size() + smallShipsOnPlatform.size();
        result += "          small ships : " + smallShipsOnOilrig.size() + "\n";
        result += "amount of workers     : " + workersOnOilrig.size() + "\n";
        return result;
    }

    //@author Louis
    // Output Information einer bestimmten Ölplattfor
    public String getInformationOilrig() {

        String result = "";
        String bigShipsOnOilrigString = "|";
        String smallShipsOnOilrigString = "|";

        // duplizierte Liste wird nach IDs sortiert bigShip
        ArrayList<ShipBig> tempArray1 = new ArrayList<>();
        tempArray1.addAll(bigShipsOnOilrig);
        Collections.sort(tempArray1);

        for(int i = 0; i < tempArray1.size(); i++) {
            try{    //Integer.toString kann NullPointerException werfen
                bigShipsOnOilrigString += Integer.toString(tempArray1.get(i).getId()) + "|"; //Holt ID des Objektes an der Stelle [i] aus Liste bigShipsOnPlatform und konvertiert zu string
            }catch (NullPointerException npe){
               System.out.println("an error occured: " + npe.getMessage());
            }
        }

        // duplizierte Liste wird nach IDs sortiert smallShip
        ArrayList<ShipSmall> tempArray2 = new ArrayList<>();
        tempArray2.addAll(smallShipsOnOilrig);
        Collections.sort(tempArray2);

        for(int i = 0; i < smallShipsOnOilrig.size(); i++) {
            try{
                smallShipsOnOilrigString += Integer.toString(tempArray2.get(i).getId()) + "|";  //siehe oben
            }catch(NullPointerException npe){
                System.out.println("an error occured: " + npe.getMessage());
            }
        }

        // Output
        result += "Oilrig ID: " + id + "\n";
        result += "------------------------------\n";
        result += "big ships             : " + bigShipsOnOilrigString + "\n";
        result += "small ships           : " + smallShipsOnOilrigString + "\n";
        result += "amount of workers     : " + workersOnOilrig.size() + "\n";

        return result;
    }

    public void addAllWorkersOnOilrig(ArrayList<Worker> list){
        this.workersOnOilrig.addAll(list);
    }

    public int getFreeCapacity(){
        int maxCapacity = 2 * initialCrewOilrig;
        int usedCapacity = workersOnOilrig.size();
        return maxCapacity - usedCapacity;
    }



}
