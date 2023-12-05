package assets;

import programm.Methods;
import java.util.ArrayList;


public class Oilrig{

    private final int id;
    public final ArrayList<Worker> workersOnOilrig = new ArrayList<Worker>();
    private final ArrayList<ShipSmall> smallShipsOnOilrig = new ArrayList<ShipSmall>();
    private final ArrayList<ShipBig> bigShipsOnOilrig = new ArrayList<ShipBig>();
    ArrayList<EvacuationPlanerItem> ep = new ArrayList<>();
    public final int initialCrewOilrig;
    private final int initialBigShips;
    private final int initialSmallShips;
    public boolean evacuate = false;

    // Constructor
    // @autor Jonas & Matthias
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
        return bigShipsOnOilrig.size();
    }
    public int getSmallShipAmount(){
        return bigShipsOnOilrig.size();
    }
    public int getWorkerAmount(){
        return workersOnOilrig.size();
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

        for(int i = 0; i < bigShipsOnOilrig.size(); i++) {
            try{    //Integer.toString kann NullPointerException werfen
                bigShipsOnOilrigString += Integer.toString(bigShipsOnOilrig.get(i).getId()) + "|"; //Holt ID des Objektes an der Stelle [i] aus Liste bigShipsOnPlatform und konvertiert zu string
            }catch (NullPointerException npe){
               System.out.println("an error occured: " + npe.getMessage());
            }
        }

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

    //@author Jonas
    // Evakuierungsmethoden
    public boolean checkEvacuationSpace(){
        int spaceAvailable = (smallShipsOnOilrig.size() * 50) + (bigShipsOnOilrig.size() * 100);
        int spaceNeeded = getWorkerAmount();
        int difference = spaceAvailable - spaceNeeded;
        if (difference >= 0){
            //Keine weiter Schiffe benötigt
            /** Mögliche visuelle Darstellung vom Evakuierungsplan
             *
             * Evakuierungsplan:
             * Ship 1:  crew 50/50, => Oilrig 2
             * Ship 7:  crew 88/100, => Oilrig 3
             */
            int avaSmallShips = getSmallShipAmount();
            int avaBigShips = getBigShipAmount();

            // Rechnung zur gleichmäßigen Verteilung der Arbeiter auf die Schiffe
            int totalEqualShips = (2 * avaBigShips) + avaSmallShips;        // ShipBig entspricht nach Kapazität zwei kleinen Schiffen
            double workerPerShip = (double)(spaceNeeded / totalEqualShips); // runterbrechen: wieviele Arbeiter pro Schiff (Dezimalzahl)
            int evenWorkerPerShip = (int) Math.floor(workerPerShip);        // Dezimalzahl abrunden (Zahl wird ungenau)
            int temp = evenWorkerPerShip * totalEqualShips;                 // Hochrechnung der ungenauen Zahl auf den benötigten Gesamtwert für Arbeiter
            int diffFormDouble = spaceNeeded - temp;                        // Differenz zwischen eigentlich benötigtem Platz und der Hochrechnung der ungenauen Zahl (temp) --- überschüssige Arbeiter

            ArrayList<EvacuationPlanerItem> epSmallShips = new ArrayList<>();
            ArrayList<EvacuationPlanerItem> epBigShips = new ArrayList<>();

            for (ShipSmall ship : smallShipsOnOilrig) {
                //System.out.println(ship.getId());
                epSmallShips.add(new EvacuationPlanerItem(ship.getId(), evenWorkerPerShip, "smallship"));
                //idsSmallShip.add(ship.getId());
            }
            for (ShipBig ship : bigShipsOnOilrig) {
                //System.out.println(ship.getId());
                epBigShips.add(new EvacuationPlanerItem(ship.getId(), 2 * evenWorkerPerShip, "bigship"));
                //idsBigShip.add(ship.getId());
            }

            ArrayList<Oilrig> otherOrs = Methods.getOtherOilrigs(id);

            int bigShipConter = 0;
            int smallShipCounter = 0;

            //ArrayList<EvacuationPlanerItem> tempEP = new ArrayList<>();

            for (int i = 0; i < epBigShips.size(); i++){
                for (int iOr = 0; iOr < otherOrs.size(); iOr++) {
                    // Wenn voll zum Nächsten
                    if (otherOrs.get(iOr).workersOnOilrig.size() < evenWorkerPerShip) break;

                    int freeSpace = otherOrs.get(iOr).getFreeCapacity();
                    EvacuationPlanerItem tempItem = epBigShips.get(i);
                    //System.out.println(tempItem.shipId);

                    if ((freeSpace - (2 * evenWorkerPerShip) >= 0 && tempItem != null)){
                        bigShipConter++;
                        otherOrs.get(iOr).addEmptyWorkers(2 * evenWorkerPerShip);
                        tempItem.destinationOr = otherOrs.get(iOr).getId();
                        ep.add(tempItem);
                        System.out.println("ID: " + tempItem.shipId + ", type = " + tempItem.type + ", dest: " + tempItem.destinationOr);
                        break;
                    }
                }
            }
            for (int i = 0; i < epSmallShips.size(); i++){
                for (int iOr = 0; iOr < otherOrs.size(); iOr++) {
                    // Wenn voll zum Nächsten
                    if (otherOrs.get(iOr).workersOnOilrig.size() < evenWorkerPerShip) break;

                    int freeSpace = otherOrs.get(iOr).getFreeCapacity();
                    EvacuationPlanerItem tempItem = epSmallShips.get(i);

                    if ((freeSpace - evenWorkerPerShip) >= 0 && tempItem != null) {
                        System.out.println(tempItem.shipId);
                        smallShipCounter++;
                        otherOrs.get(iOr).addEmptyWorkers(evenWorkerPerShip);
                        tempItem.destinationOr = otherOrs.get(iOr).getId();
                        ep.add(tempItem);
                        System.out.println("ID: " + tempItem.shipId + ", type = " + tempItem.type + ", dest: " + tempItem.destinationOr);
                        break;
                    }
                }
            }
            System.out.println(getEvacuationPlanerInfo());
        }
        else {
            System.out.println("Debug: Hilfe rufen! Alaaaaarm");
            //Weiter Schiffe anfordern
            ArrayList<Oilrig> otherOrs = Methods.getOtherOilrigs(id);
            ArrayList<EvacuationPlanerItem> epSmallShips = new ArrayList<>();
            ArrayList<EvacuationPlanerItem> epBigShips = new ArrayList<>();

            while (difference < 0){
                if (difference <= -100){
                    EvacuationPlanerItem tempForNotNull = callForBigShipEP(otherOrs, "bigship");
                    if (tempForNotNull != null) {
                        epBigShips.add(tempForNotNull);
                        difference -= 100;
                    }
                    else {
                        System.out.println("an error occurred: something went wrong while calling for help");
                        return false;
                    }
                    // Abbruchbedingung für kein Schiff vorhanden
                } else if (difference <= -50) {
                    EvacuationPlanerItem tempForNotNull = callForBigShipEP(otherOrs, "smallship");
                    if (tempForNotNull != null) {
                        epSmallShips.add(tempForNotNull);
                        difference -= 100;
                    }
                    else {
                        System.out.println("an error occurred: something went wrong while calling for help");
                        return false;
                    }
                    // @Jonas Maybe/Maybenot Abbruchbedingung für kein Schiff vorhanden
                }
            }

            for (ShipSmall ship : smallShipsOnOilrig) {
                epSmallShips.add(new EvacuationPlanerItem(ship.getId(), 0, "smallship"));
            }
            for (ShipBig ship : bigShipsOnOilrig) {
                epBigShips.add(new EvacuationPlanerItem(ship.getId(), 0, "bigship"));
            }
            int avaSmallShips = epSmallShips.size();
            int avaBigShips = epBigShips.size();

            // Planerstellung auslagern

        }

        return false;
    }

    private EvacuationPlanerItem callForBigShipEP(ArrayList<Oilrig> otherOr, String type){
        if (type == "bigship"){
            for (Oilrig oilrig : otherOr) {
                if (!oilrig.bigShipsOnOilrig.isEmpty()) {
                    ShipBig ship = oilrig.bigShipsOnOilrig.get(0);
                    return new EvacuationPlanerItem(ship.getId(), 0, type);
                }
            }
        } else {
            for (Oilrig oilrig : otherOr) {
                if (!oilrig.smallShipsOnOilrig.isEmpty()) {
                    ShipSmall ship = oilrig.smallShipsOnOilrig.get(0);
                    return new EvacuationPlanerItem(ship.getId(), 0, type);
                }
            }
        }
        return null;
    }

    private int getFreeCapacity(){
        int maxCapacity = 2 * initialCrewOilrig;
        int usedCapacity = workersOnOilrig.size();
        return maxCapacity - usedCapacity;
    }

    private void addEmptyWorkers(int i){
        for (int j = 1; j < i; j++) {
            workersOnOilrig.add(new Worker(j * (-1)));
        }
    }

    // Große Schiffe und kleine Schiffe werden hier theoretisch als Seperate Liste betrachtet
    // Beispiel: Die Evakuierungsplanliste hat 12 Elemente, 5 kleine Schiff, 7 große Schiffe
    // So gibt diese Methode ein kleines/großes Schiff zurück gemessen an seinen theoretischem Index [0-4] oder [0-6]
    public EvacuationPlanerItem getEP_AtTypeIndex(int i, String type) {
        int counter = 0;
        for (int j = 0; j < ep.size(); j++) {
            if (ep.get(j).type.equals(type) && counter == i){
                EvacuationPlanerItem temp = ep.get(j).clone();
                ep.remove(j);
                return temp;
            }
            else if (ep.get(j).type.equals(type)) counter++;
        }
        return null;
    }

    //@author Louis
    // Output des Evakuierungsplans
    public String getEvacuationPlanerInfo(){

        String result = "------------------------ Evacuation Plan ------------------------ \n";
        for (EvacuationPlanerItem epItem : ep) {

            int maxCapacity = 0;

            Ship ship = getShipById(epItem.shipId);     //setzt maxCapacity in Abhängigkeit ob epItem.shipId ein großes oder kleines Schiff ist
            if(ship instanceof ShipBig){
                maxCapacity = 100;
            } else if (ship instanceof ShipSmall) {
                maxCapacity = 50;
            } else{
                System.out.println("an error occurred: no ship found");
            }
            // Output
            result += "Ship ID " + epItem.shipId + "     Crew " + epItem.usedCrew + "/" + maxCapacity + "       →→→       " + "destinated Oilrig ID " + epItem.destinationOr + "\n"; //Warning: => StringBuilder benutzen?
        }
        return result;
    }
}
