package assets;

import Programm.Methods;
import java.util.ArrayList;


public class Oilrig{

    private final int id;
    private final ArrayList<Worker> workersOnPlatform = new ArrayList<Worker>();
    private final ArrayList<ShipSmall> smallShipsOnPlatform = new ArrayList<ShipSmall>();
    private final ArrayList<ShipBig> bigShipsOnPlatform = new ArrayList<ShipBig>();
    public final int initialCrew;
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
        // @see Methods.getCounterShips @autor Jonas
        // Debug System.out.println(String.valueOf(Methods.getCounterWorker()) + " < " + String.valueOf(initialCrew + Methods.getCounterWorker()));

        int initMax = initialCrew + Methods.getCounterWorker();

        for (int i = Methods.getCounterWorker(); i < initMax; i++) {
            this.workersOnPlatform.add(new Worker(i));
            Methods.addCounterWorker();
        }

        initMax = initialBigShips + Methods.getCounterShips();
        //Add BigShips to Oilrig
        // @see Methods.getCounterShips
        for (int i = Methods.getCounterShips(); i < initMax; i++) {
            this.bigShipsOnPlatform.add(new ShipBig(i));
            Methods.addCounterShips();
        }

        initMax = initialSmallShips + Methods.getCounterShips();
        //Add SmallShips to Oilrig
        for (int i = Methods.getCounterShips(); i < initMax; i++) {
            this.smallShipsOnPlatform.add(new ShipSmall(i));
            Methods.addCounterShips();
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
    public int getWorkerAmount(){
        return workersOnPlatform.size();
    }

    //@autor Jonas
    public void addBigShip(ShipBig ship) {
        bigShipsOnPlatform.add(ship);
    }
    public void addSmallShip(ShipSmall ship){
        smallShipsOnPlatform.add(ship);
    }
    public ShipSmall getSmallShipById(int id){
        for (ShipSmall ship : smallShipsOnPlatform){
            if (ship.getId() == id) return ship;
        }
        return null;
    }
    public ShipBig getBigShipById(int id){
        for (ShipBig ship : bigShipsOnPlatform) {
            if (ship.getId() == id) return ship;
        }
        return null;
    }

    //check-Methoden
    public boolean checkTotalShipCountBiggerOne(){
        int i = bigShipsOnPlatform.size() + smallShipsOnPlatform.size();
        return i >= 1;
    }

    private boolean checkBigShipCountBelowMax(){
        int maxAllowedShips = initialBigShips * 2;
        int dockedShips = bigShipsOnPlatform.size();
        return dockedShips < maxAllowedShips;
    }

    private boolean checkSmallShipCountBelowMax(){
        int maxAllowedShips = initialSmallShips * 2;
        int dockedShips = smallShipsOnPlatform.size();
        return dockedShips < maxAllowedShips;
    }

    //@author Louis
    //return: kann Platform receiver ein kleines Schiff aufnehmen
    public boolean checkOilrigCanReceiveSmallShip(){
        return this.getSmallShipAmount() + 1 <= this.initialSmallShips + 4;
    }

    //@author Louis
    //return: kann Platform receiver ein grosses Schiff aufnehmen
    public boolean checkOilrigCanReceiveBigShip(){
        return this.getBigShipAmount() + 1 <= this.initialBigShips + 4;
    }

    public void transferWorkerOilrigToShip(int amount, ShipSmall ship) {
        for (int i = 1; i <= amount; i++){
            Worker tempWorker = workersOnPlatform.get(0); // i oder 0, wenn das Objekt gelöscht wird ändert sich die Liste
            workersOnPlatform.remove(0);
            ship.receiveWorker(tempWorker);
        }
    }
    public void transferWorkerOilrigToShip(int amount, ShipBig ship) {
        for (int i = 1; i <= amount; i++){
            Worker tempWorker = workersOnPlatform.get(0); // i oder 0, wenn das Objekt gelöscht wird ändert sich die Liste
            workersOnPlatform.remove(0);
            ship.receiveWorker(tempWorker);
        }
    }
    public void transferAllWorkerShipToOilrig(ShipSmall ship){
        ArrayList<Worker> wTransfer = ship.departureAll();
        workersOnPlatform.addAll(wTransfer);
    }
    public void transferAllWorkerShipToOilrig(ShipBig ship){
        ArrayList<Worker> wTransfer = ship.departureAll();
        workersOnPlatform.addAll(wTransfer);
    }

    public void undockShip(ShipSmall ship){
        int id = ship.getId();
        for (int i = 0; i < smallShipsOnPlatform.size(); i++) {
            if (smallShipsOnPlatform.get(i).getId() == id) {
                try{
                    smallShipsOnPlatform.remove(i);
                }catch (IndexOutOfBoundsException oobe){
                    System.out.println("an error occurred: " + oobe.getMessage());
                }
            }
        }
    }
    public void undockShip(ShipBig ship){
        int id = ship.getId();
        for (int i = 0; i < bigShipsOnPlatform.size(); i++) {
            if (bigShipsOnPlatform.get(i).getId() == id) {
                try{
                    bigShipsOnPlatform.remove(i);
                }catch (IndexOutOfBoundsException oobe){
                    System.out.println("an error occurred: " + oobe.getMessage());
                }
            }
        }
    }
    public void dockShip(ShipSmall ship){
        smallShipsOnPlatform.add(ship);
    }
    public void dockShip(ShipBig ship){
        bigShipsOnPlatform.add(ship);
    }

    //UserInterface
    //@author Louis
    public String GetInformationOverview() {

        String result = "";
        // int sumOfShips = getBigShipAmount() + getSmallShipAmount();   // Fehlerhaft, 3+4 ist nicht 6
        //int sumOfShips = bigShipsOnPlatform.size() + smallShipsOnPlatform.size();
        result += "Oilrig ID: " + id + "\n";
        result += "------------------------------\n";
        result += "amount of ships docked: " + ( bigShipsOnPlatform.size() + smallShipsOnPlatform.size() ) + "\n";
        result += "          big ships   : " + bigShipsOnPlatform.size() + "\n";
        result += "          small ships : " + smallShipsOnPlatform.size() + "\n";
        result += "amount of workers     : " + workersOnPlatform.size() + "\n";
        return result;
    }

    public String GetInformationOilrig() {

        String result = "";
        String bigShipsOnPlatformString = "|";
        String smallShipsOnPlatformString = "|";
        
        //Integer.toString kann NullPointerException werfen
        for(int i = 0; i < bigShipsOnPlatform.size(); i++) {
            try{
                bigShipsOnPlatformString += Integer.toString(bigShipsOnPlatform.get(i).getId()) + "|"; //Holt ID des Objektes an der Stelle [i] aus Liste bigShipsOnPlatform und konvertiert zu string
            }catch (NullPointerException npe){
               System.out.println("an error occured: " + npe.getMessage());
            }
        }

        for(int i = 0; i < smallShipsOnPlatform.size(); i++) {
            try{
                smallShipsOnPlatformString += Integer.toString(smallShipsOnPlatform.get(i).getId()) + "|";
            }catch(NullPointerException npe){
                System.out.println("an error occured: " + npe.getMessage());
            }
        }

        //Output
        result += "Oilrig ID: " + id + "\n";
        result += "------------------------------\n";
        result += "big ships             : " + bigShipsOnPlatformString + "\n";
        result += "small ships           : " + smallShipsOnPlatformString + "\n";
        result += "amount of workers     : " + workersOnPlatform.size() + "\n";

        return result;
    }

}
