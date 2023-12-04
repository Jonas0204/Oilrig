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

    public Ship getShipById(int id){
        for (Ship ship : bigShipsOnOilrig) {
            if (ship.getId() == id) return ship;
        }
        for (Ship ship : smallShipsOnOilrig) {
            if (ship.getId() == id) return ship;
        }
        return null;
    }

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

    //UserInterface
    //@author Louis
    public String getInformationOverview() {

        String result = "";
        // int sumOfShips = getBigShipAmount() + getSmallShipAmount();   // Fehlerhaft, 3+4 ist nicht 6
        //int sumOfShips = bigShipsOnPlatform.size() + smallShipsOnPlatform.size();
        result += "Oilrig ID: " + id + "\n";
        result += "------------------------------\n";
        result += "amount of ships docked: " + ( bigShipsOnOilrig.size() + smallShipsOnOilrig.size() ) + "\n";
        result += "          big ships   : " + bigShipsOnOilrig.size() + "\n";
        result += "          small ships : " + smallShipsOnOilrig.size() + "\n";
        result += "amount of workers     : " + workersOnOilrig.size() + "\n";
        return result;
    }

    public String getInformationOilrig() {

        String result = "";
        String bigShipsOnOilrigString = "|";
        String smallShipsOnOilrigString = "|";

        //Integer.toString kann NullPointerException werfen
        for(int i = 0; i < bigShipsOnOilrig.size(); i++) {
            try{
                bigShipsOnOilrigString += Integer.toString(bigShipsOnOilrig.get(i).getId()) + "|"; //Holt ID des Objektes an der Stelle [i] aus Liste bigShipsOnPlatform und konvertiert zu string
            }catch (NullPointerException npe){
               System.out.println("an error occured: " + npe.getMessage());
            }
        }

        for(int i = 0; i < smallShipsOnOilrig.size(); i++) {
            try{
                smallShipsOnOilrigString += Integer.toString(smallShipsOnOilrig.get(i).getId()) + "|";
            }catch(NullPointerException npe){
                System.out.println("an error occured: " + npe.getMessage());
            }
        }

        //Output
        result += "Oilrig ID: " + id + "\n";
        result += "------------------------------\n";
        result += "big ships             : " + bigShipsOnOilrigString + "\n";
        result += "small ships           : " + smallShipsOnOilrigString + "\n";
        result += "amount of workers     : " + workersOnOilrig.size() + "\n";

        return result;
    }

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

            //ArrayList<Integer> idsSmallShip = new ArrayList<>();
            //ArrayList<Integer> idsBigShip = new ArrayList<>();

            for (ShipSmall ship : smallShipsOnOilrig) {
                ep.add(new EvacuationPlanerItem(ship.getId(), evenWorkerPerShip, "smallship"));
                //idsSmallShip.add(ship.getId());
            }
            for (ShipBig ship : bigShipsOnOilrig) {
                ep.add(new EvacuationPlanerItem(ship.getId(), 2 * evenWorkerPerShip, "bigship"));
                //idsBigShip.add(ship.getId());
            }

            ArrayList<Oilrig> otherOrs = Methods.getOtherOilrigs(id);

            int bigShipConter = 0;
            int smallShipCounter = 0;

            for (int iShip = 0; iShip < ep.size(); iShip++) {
                for (int iOr = 0; iOr < otherOrs.size(); iOr++) {
                    System.out.println("Schleife von Or:" + otherOrs.get(iOr).getId());
                    int freeSpace = otherOrs.get(iOr).getFreeCapacity();
                    System.out.println("Freespace: " + freeSpace + " - " + (2 * evenWorkerPerShip));

                    EvacuationPlanerItem itemBig = getEP_AtTypeIndex(bigShipConter, "bigship");
                    EvacuationPlanerItem itemSmall = getEP_AtTypeIndex(smallShipCounter, "smallship");
                    if ((freeSpace - (2 * evenWorkerPerShip) >= 0 && itemBig != null)){
                        bigShipConter++;
                        otherOrs.get(iOr).addEmptyWorkers(2 * evenWorkerPerShip);
                        itemBig.destinationOr = otherOrs.get(iOr).getId();
                        System.out.println("Dest: " + itemBig.destinationOr);
                    }
                    else if ((freeSpace - evenWorkerPerShip) >= 0 && itemSmall != null) {
                        smallShipCounter++;
                        otherOrs.get(iOr).addEmptyWorkers(evenWorkerPerShip);
                        itemSmall.destinationOr = otherOrs.get(iOr).getId();
                        System.out.println("Dest: " + itemSmall.destinationOr);
                    }
                    else break;
                }
            }
            System.out.println(getEvacuationPlanerInfo());
        }
        else {
            System.out.println("Debug: falscher weg");
            //Weiter Schiffe anfordern
        }

        return false;
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


    public String getEvacuationPlanerInfo(){

        String result = "------------------------ Evacuation Plan ------------------------ \n";
        for (EvacuationPlanerItem epItem : ep) {

            int maxCapacity = 0;

            Ship ship = getShipById(epItem.shipId);
            if(ship instanceof ShipBig){
                maxCapacity = 100;
            } else if (ship instanceof ShipSmall) {
                maxCapacity = 50;
            } else{
                System.out.println("an error occurred: no ship found");
            }

            result += "Ship ID " + epItem.shipId + "     Crew " + epItem.usedCrew + "/" + maxCapacity + "       →→→       " + "destinated Oilrig ID " + epItem.destinationOr + "\n"; //Warning: => StringBuilder benutzen?
        }
        return result;
    }

}
