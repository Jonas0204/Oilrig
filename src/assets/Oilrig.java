package assets;

import programm.Methods;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

public class Oilrig{

    private final int id;
    public ArrayList<Worker> workersOnOilrig = new ArrayList<>();
    public ArrayList<ShipSmall> smallShipsOnOilrig = new ArrayList<>();
    public ArrayList<ShipBig> bigShipsOnOilrig = new ArrayList<>();
    public final int initialCrewOilrig;
    private final int initialBigShips;
    private final int initialSmallShips;
    public boolean evacuated = false;

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
        this.evacuated = or.evacuated;
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
            this.bigShipsOnOilrig.add(new ShipBig());
            Methods.addCounterShips();
        }

        initMax = initialSmallShips + Methods.getCounterShips();
        //Add SmallShips to Oilrig
        for (int i = Methods.getCounterShips(); i < initMax; i++) {
            this.smallShipsOnOilrig.add(new ShipSmall());
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
        return this.smallShipsOnOilrig.size();
    }
    public int getWorkerAmount(){
        return this.workersOnOilrig.size();
    }
    public ArrayList<Worker> getWorkersOnOilrig(){
        return this.workersOnOilrig;
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
    //@author Louis
    //vereint getSmallShipById(int id) und getBigShipById(int id)
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

    //return: kann Platform receiver ein kleines Schiff aufnehmen
    public boolean checkOilrigCanReceiveSmallShip(){
        return this.getSmallShipAmount() + 1 <= this.initialSmallShips + 4;
    }
    //return: kann Platform receiver ein grosses Schiff aufnehmen
    public boolean checkOilrigCanReceiveBigShip(){
        return this.getBigShipAmount() + 1 <= this.initialBigShips + 4;
    }

    //@author Jonas
    //Hilfsmethoden zum Bewegen eines Schiffs
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
        StringBuilder bigShipsOnOilrigString = new StringBuilder("|");
        StringBuilder smallShipsOnOilrigString = new StringBuilder("|");

        // duplizierte Liste wird nach IDs sortiert bigShip
        ArrayList<ShipBig> tempArray1 = new ArrayList<>(bigShipsOnOilrig);
        Collections.sort(tempArray1);

        for (ShipBig shipBig : tempArray1) {
            try {    //Integer.toString kann NullPointerException werfen
                bigShipsOnOilrigString.append(shipBig.getId()).append("|"); //Holt ID des Objektes an der Stelle [i] aus Liste bigShipsOnPlatform und konvertiert zu string
            } catch (NullPointerException npe) {
                System.out.println("an error occured: " + npe.getMessage());
            }
        }

        // duplizierte Liste wird nach IDs sortiert smallShip
        ArrayList<ShipSmall> tempArray2 = new ArrayList<>(smallShipsOnOilrig);
        Collections.sort(tempArray2);

        for(int i = 0; i < smallShipsOnOilrig.size(); i++) {
            try{
                smallShipsOnOilrigString.append(tempArray2.get(i).getId()).append("|");  //siehe oben
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

    public boolean checkEvacuationSpace(){
        ArrayList<EvacuationPlanerItem> ep = new ArrayList<>();

        int spaceAvailable = (getSmallShipAmount() * 50) + (getBigShipAmount() * 100);
        int spaceNeeded = getWorkerAmount();
        int difference = spaceAvailable - spaceNeeded;
        if (difference >= 0){
            //Keine weiteren Schiffe benötigt
            ArrayList<EvacuationPlanerItem> epSmallShips = new ArrayList<>();
            ArrayList<EvacuationPlanerItem> epBigShips = new ArrayList<>();

            calculatePlan(spaceNeeded, epSmallShips, epBigShips, ep);
        }
        else {
            //Weiter Schiffe anfordern
            ArrayList<Oilrig> otherOrs = Methods.getOtherOilrigs(getId());
            ArrayList<EvacuationPlanerItem> epSmallShips = new ArrayList<>();
            ArrayList<EvacuationPlanerItem> epBigShips = new ArrayList<>();

            while (difference < 0){
                if (difference < -50){
                    EvacuationPlanerItem tempForNotNull = callForBigShipEP(otherOrs, "bigship");
                    if (tempForNotNull != null) {
                        tempForNotNull.toCallID_S[1] = this.id;
                        epBigShips.add(tempForNotNull);
                        System.out.println("Helping ship => ID " + tempForNotNull.shipId);
                        difference += 100;
                    }
                    else {
                        System.out.println("an error occurred: something went wrong while calling for help");
                        return false;
                    }
                    // Abbruchbedingung für kein Schiff vorhanden
                } else {
                    EvacuationPlanerItem tempForNotNull = callForBigShipEP(otherOrs, "smallship");
                    if (tempForNotNull != null) {
                        tempForNotNull.toCallID_S[1] = this.id;
                        epSmallShips.add(tempForNotNull);
                        System.out.println("Helping ship => ID " + tempForNotNull.shipId);
                        difference += 50;
                    }
                    else {
                        System.out.println("an error occurred: something went wrong while calling for help");
                        return false;
                    }
                    // @Jonas Maybe/Maybenot Abbruchbedingung für kein Schiff vorhanden
                }
            }
            calculatePlan(spaceNeeded, epSmallShips, epBigShips, ep);
        }
        return true;
    }

    public void calculatePlan(int spaceNeeded, ArrayList<EvacuationPlanerItem> epSmallShips, ArrayList<EvacuationPlanerItem> epBigShips, ArrayList<EvacuationPlanerItem> ep) {
        ArrayList<Oilrig> otherOrs = Methods.getOtherOilrigs(getId());

        for (ShipSmall ship : smallShipsOnOilrig) {
            epSmallShips.add(new EvacuationPlanerItem(ship.getId(), "smallship"));
        }
        for (ShipBig ship : bigShipsOnOilrig) {
            epBigShips.add(new EvacuationPlanerItem(ship.getId(),  "bigship"));
        }

        int avaSmallShips = epSmallShips.size();
        int avaBigShips = epBigShips.size();
        System.out.println("Bigships: " + avaBigShips + ", Smallships: " + avaSmallShips);

        // Rechnung zur gleichmäßigen Verteilung der Arbeiter auf die Schiffe
        int totalEqualShips = (2 * avaBigShips) + avaSmallShips;        // ShipBig entspricht nach Kapazität zwei kleinen Schiffen
        double workerPerShip = (double) spaceNeeded / totalEqualShips;  // runterbrechen: wie viele Arbeiter pro Schiff (Dezimalzahl)
        int evenWorkerPerShip = (int) Math.floor(workerPerShip);        // Dezimalzahl abrunden (Zahl wird ungenau)
        int temp = evenWorkerPerShip * totalEqualShips;                 // Hochrechnung der ungenauen Zahl auf den benötigten Gesamtwert für Arbeiter
        int diffFormDouble = spaceNeeded - temp;                        // Differenz zwischen eigentlich benötigtem Platz und der Hochrechnung der ungenauen Zahl (temp) --- überschüssige Arbeiter

        for (EvacuationPlanerItem epBigShip : epBigShips) {
            int localEvenWorkerPerShip;

            if (diffFormDouble > 0) {
                localEvenWorkerPerShip = (2 * evenWorkerPerShip) + 1;
                diffFormDouble--;
            } else localEvenWorkerPerShip = (2 * evenWorkerPerShip);

            for (Oilrig otherOr : otherOrs) {
                int freeSpace = otherOr.getFreeCapacity();
                // Wenn voll zum Nächsten
                if (freeSpace <= localEvenWorkerPerShip) {
                    continue;
                }
                if (!otherOr.checkOilrigCanReceiveBigShip()) {
                    continue;
                }

                if ((freeSpace - (2 * evenWorkerPerShip) >= 0 && epBigShip != null)) {
                    otherOr.getWorkersOnOilrig().addAll(addEmptyWorkers(localEvenWorkerPerShip));
                    otherOr.bigShipsOnOilrig.add(new ShipBig(1));
                    epBigShip.destinationOr = otherOr.getId();
                    epBigShip.usedCrew = localEvenWorkerPerShip;
                    ep.add(epBigShip);
                    //System.out.println("ID: " + tempItem.shipId + ", type = " + tempItem.type + ", dest: " + tempItem.destinationOr);
                    break;
                }
            }
        }
        for (EvacuationPlanerItem epSmallShip : epSmallShips) {
            //System.out.println("ID: " + epSmallShip.shipId + ", type = " + epSmallShip.type + ", dest: " + epSmallShip.destinationOr);
            int localEvenWorkerPerShip;
            if (diffFormDouble > 0) {
                localEvenWorkerPerShip = evenWorkerPerShip + 1;
                diffFormDouble--;
            } else localEvenWorkerPerShip = evenWorkerPerShip;

            for (Oilrig otherOr : otherOrs) {
                // Wenn voll zum Nächsten
                int freeSpace = otherOr.getFreeCapacity();
                if (freeSpace <= localEvenWorkerPerShip) {
                    //System.out.println("The most unusual error.1");
                    continue;
                }
                if (!otherOr.checkOilrigCanReceiveSmallShip()) {
                    continue;
                }


                if ((freeSpace - evenWorkerPerShip) >= 0 && epSmallShip != null) {
                    otherOr.getWorkersOnOilrig().addAll(addEmptyWorkers(localEvenWorkerPerShip));
                    otherOr.smallShipsOnOilrig.add(new ShipSmall(1));
                    epSmallShip.destinationOr = otherOr.getId();
                    epSmallShip.usedCrew = localEvenWorkerPerShip;
                    ep.add(epSmallShip);
                    //System.out.println("ID: " + epSmallShip.shipId + ", type = " + epSmallShip.type + ", dest: " + epSmallShip.destinationOr);
                    break;
                }
            }
        }
        System.out.println(getEvacuationPlanerInfo(ep));

        Scanner yesOrNo = new Scanner(System.in);
        String input;
        boolean repeat = true;
        System.out.println("If you want to execute the suggested plan, please type 'y'. If you want to abort the plan, please type 'n'");
        while (repeat) {
            input = yesOrNo.nextLine();
            String[] arguments = input.split(" ");

            switch (arguments[0]) {
                case "y", "Yes", "Y", "yes":
                    executePlan(ep);
                    repeat = false;
                    break;
                case "n", "N", "no", "No":
                    repeat = false;
                    break;
                default:
                    System.out.println("Please type 'y' if you want to execute the suggested plan, or 'n' to abort it");
                    break;
            }
        }
    }

    private void executePlan(ArrayList<EvacuationPlanerItem> ep){
        for (EvacuationPlanerItem epItem : ep) {
            if (epItem.toCall){
                Methods.moveWorkers(String.valueOf(epItem.shipId), String.valueOf(0), String.valueOf(epItem.toCallID_S[0]),
                        String.valueOf(epItem.toCallID_S[1]),  true);
            }
            Methods.moveWorkers(String.valueOf(epItem.shipId), String.valueOf(epItem.usedCrew),
                    String.valueOf(Ship.getShipOriginID(epItem.shipId)), String.valueOf(epItem.destinationOr), true);
        }
    }

    public String getEvacuationPlanerInfo(ArrayList<EvacuationPlanerItem> ep){
        ArrayList<Oilrig> allOilrigs = Methods.getAllOilrigs();

        StringBuilder result = new StringBuilder("---------------------------------- Evacuation Plan ---------------------------------- \n");
        // unfertig
        result.append("evacuating " + this.workersOnOilrig.size() + " Workers from Oilrig [").append(this.getId()).append("]\n");
        for (EvacuationPlanerItem epItem : ep) {

            int maxCapacity = 0;

            //setzt maxCapacity in Abhängigkeit ob epItem.shipId ein großes oder kleines Schiff ist
            for (Oilrig temp : allOilrigs) {
                Ship ship = temp.getShipById(epItem.shipId);
                if (ship != null){
                    if(ship instanceof ShipBig){
                        maxCapacity = 100;
                    } else if (ship instanceof ShipSmall) {
                        maxCapacity = 50;
                    } else{
                        System.out.println("an error occurred: no ship found");
                    }
                }
            }

            // Output
            String shipOriginID = String.valueOf(Ship.getShipOriginID(epItem.shipId));

            result.append("Ship: [").append(epItem.shipId).append("] from Oilrig [").append(shipOriginID).append("]").append("\tCrew: [").append(epItem.usedCrew).append("/").append(maxCapacity).append("]").append("\t\t→→→    \t\t").append("destinated Oilrig: [").append(epItem.destinationOr).append("]").append("\n"); //Warning: => StringBuilder benutzen?
        }
        return result.toString();
    }

    public static ArrayList<Worker> addEmptyWorkers(int i){
        ArrayList<Worker> temp = new ArrayList<>();
        for (int j = 1; j < i; j++) {
            temp.add(new Worker(j * (-1)));
        }
        return temp;
    }

    public int getFreeCapacity(){
        int maxCapacity = 2 * initialCrewOilrig;
        int usedCapacity = workersOnOilrig.size();
        return maxCapacity - usedCapacity;
    }

    private static EvacuationPlanerItem callForBigShipEP(ArrayList<Oilrig> otherOr, String type){
        if (Objects.equals(type, "bigship")){
            for (Oilrig oilrig : otherOr) {
                if (!oilrig.bigShipsOnOilrig.isEmpty()) {
                    ShipBig ship = oilrig.bigShipsOnOilrig.get(0);
                    EvacuationPlanerItem temp = new EvacuationPlanerItem(ship.getId(), type);
                    temp.toCallID_S[0] = oilrig.getId();
                    temp.toCall = true;
                    return temp;
                }
            }
        } else {
            for (Oilrig oilrig : otherOr) {
                if (!oilrig.smallShipsOnOilrig.isEmpty()) {
                    ShipSmall ship = oilrig.smallShipsOnOilrig.get(0);
                    EvacuationPlanerItem temp = new EvacuationPlanerItem(ship.getId(), type);
                    temp.toCallID_S[0] = oilrig.getId();
                    temp.toCall = true;
                    return temp;
                }
            }
        }
        return null;
    }
}
