package programm;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import assets.*;

import javax.print.attribute.standard.MediaSize;

public class Methods {

   private static ArrayList<Oilrig> oilrigs = new ArrayList<Oilrig>();



    // verarbeitet die Befehle (Konsoleninput) des Users und führt dementsprechende Methoden aus und sendet Output an User zurück
    public static void handleInput(ArrayList<Oilrig> oilrigsParam) {
        oilrigs = oilrigsParam;

        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (true) { // while-Schleife um ständig Befehle an das Programm geben zu können
            input = scanner.nextLine();
            String[] arguments = input.split(" ");
            switch (arguments[0]) {
                //@author Louis
                case "help": //help
                    if (arguments.length != 1) {
                        System.out.println("invalid amount of arguments provided");
                        break;
                    }
                    Methods.printHelp();
                    break;
                //@author Jonas
                case "move": //move [ship ID] [worker amount] [sending oilrig ID] [receiving oilrig ID]
                    if (arguments.length != 5) {
                        System.out.println("invalid amount of arguments provided");
                        break;
                    }
                    moveWorkers(arguments[1], arguments[2], arguments[3], arguments[4], false);
                    break;
                //@author Jonas
                case "evacuate": //evacuate [Oilrig id]
                    int id = 0;
                    try {
                        id = Integer.parseInt(arguments[1]);
                    }
                    catch (Exception ex){
                        System.out.println("ID argument is not a Number");
                    }

                    if (arguments.length != 2) {
                        System.out.println("invalid amount of arguments provided");
                        break;
                    }
                    if (id == 1 || id == 2 || id == 3 || id == 4) { //Oilrig 1, 2, 3, oder 4 Infos werden ausgegeben
                        Oilrig or = getPlatByID(id);
                        if (or == null) {                            //immer lieber überprüfen
                            System.out.println("an error occurred: Oilrig not found!");
                        }
                        boolean successful = evacuation(id);
                        if (successful) System.out.println("evacuation successful");
                        else System.out.println("evacuation failed");
                    } else System.out.println("an error occurred: Wrong ID! Please use an ID between 1 and 4.");
                    break;
                //@author Jonas, Louis
                case "overview": //overview
                    if (arguments.length != 1) {
                        System.out.println("invalid amount of arguments provided");
                        break;
                    }
                    try {
                        for (Oilrig i : oilrigs) {
                            System.out.println(i.getInformationOverview());
                        }
                    } catch (Exception e) {
                        System.out.println("an error occurred " + e.getMessage());
                    }
                    break;
                //@autor Jonas, Louis
                case "oilrig": //oilrig [oilrig ID]
                    if (arguments.length != 2) {
                        System.out.println("invalid amount of arguments provided");
                        break;
                    }
                    try {
                        if(  Integer.parseInt(arguments[1]) >= 5 ||   Integer.parseInt(arguments[1]) <= 0){
                            System.out.println("an error occurred: oilrig with ID " +   Integer.parseInt(arguments[1]) + " not found");
                        }
                        else {
                            for (Oilrig i : oilrigs) {
                                if (  Integer.parseInt(arguments[1]) == i.getId()) {
                                    System.out.println(i.getInformationOilrig());
                                }
                            }
                        }
                    } catch (NumberFormatException nfe) {
                        System.out.println("an error occurred: ID must be a number " + nfe.getMessage());
                    } catch (NullPointerException npe) {
                        System.out.println("an error occurred: Oilrig with that ID returns 'null' " + npe.getMessage());
                    }
                    break;
                //@author Louis
                case "exit": //exit
                    if (arguments.length != 1) {
                        System.out.println("invalid amount of arguments provided");
                        break;
                    }
                    System.exit(0);
                    break;
                default:
                    System.out.println("This Command does not exist. Try 'help' for information about commands");
                    break;
            }
            try {
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    /** @autor Jonas
     *
     * @return Gibt true zurück wenn beide ID's vorhanden sind, andernfalls wird false zurückgegeben
     */
    public static boolean existsID(int senderID, int receiverID){
        boolean senderIsTrue = false;
        boolean receiverIsTrue = false;

        try{
            for (Oilrig i: oilrigs){
                if (senderID == i.getId()) {
                    senderIsTrue = true;
                }
                else if (receiverID == i.getId()){
                    receiverIsTrue = true;
                }
            }
        }catch(NullPointerException npe){
            System.out.println("an error occurred: oilrig equals null " + npe.getMessage());
        }catch(ArrayIndexOutOfBoundsException oobe){
            System.out.println("an error occurred: oilrig not found " + oobe.getMessage());
        }
        return senderIsTrue && receiverIsTrue;
    }

    //@author Jonas
    //WICHTIG: Wenn diese Methode verwendet wird, muss der Rückgabewert auf NULL geprüft werden
    public static Oilrig getPlatByID(int ID) {
        try {
            for (Oilrig opf : oilrigs) {
                if (ID == opf.getId())
                    return opf;
            }
        } catch (NullPointerException npe) {
            System.out.println("an error occurred: " + npe);
        }
        return null;
    }

    //@author Jonas
    public static boolean evacuation(int evacuationId){
        Oilrig eOr = new Oilrig(getPlatByID(evacuationId));
        boolean successful = checkEvacuationSpace(eOr);
        return true;
    }

    //@author Jonas
    //Strings für Argumente in Input/Output
    public static void moveWorkers(String shipIdParam, String amountparam, String senderIdParam, String receiverIdParam, boolean mayday) {
        int senderID = 0;
        int receiverID = 0;
        int amount = 0;
        int shipID = 0;
        try {
            senderID =   Integer.parseInt(senderIdParam);
            receiverID =   Integer.parseInt(receiverIdParam);
            amount =   Integer.parseInt(amountparam);
            shipID =   Integer.parseInt(shipIdParam);
        }catch (Exception ex){
            System.out.println("an error occurred: ID's and amounts must be whole numbers");
            return; //geändert 04.12.2023 19.52
        }
        boolean idExists = Methods.existsID(senderID, receiverID);
        if (!idExists) {
            System.out.println("an error occurred: ID invalid for oilrig");
            return;
        }

        Oilrig senderOr = Methods.getPlatByID(senderID);
        Oilrig receiverOr = Methods.getPlatByID(receiverID);

        // Feststellen welche Art von Schiff wir verschieben
        // evtl. über getShipID() zusammenfassen
        assert senderOr != null;
        ShipSmall smallShip = senderOr.getSmallShipById(shipID);
        ShipBig bigShip = senderOr.getBigShipById(shipID);
        String ShipType = "";
        if (smallShip == null && bigShip == null) {
            System.out.println("an error occurred: could not find ship with id " + shipID);
            return;
        }
        else if (smallShip == null) {
            ShipType = "bigship";
        } else if (bigShip == null) {
            ShipType = "smallship";
        }

        if (ShipType.equals("smallship") && amount > 50) {
            System.out.println("an error occurred: Invalid amount of workers. Select a bigger ship or send less workers ");
            return;
        } else if (ShipType.equals("bigship")  && amount > 100) {
            System.out.println("an error occurred: Invalid amount of workers. Ship can't hold more than 100 workers ");
            return;
        }

        // Bedingungen prüfen
        // Bedingung I) Auf jeder Plattform müssen sich immer mindestens 10% initialen Besatzung an Mitarbeitenden befinden, außer die Plattform wurde evakuiert.
        int minWorkers = (int) Math.ceil(0.1 * senderOr.initialCrewOilrig);
        if (minWorkers >  senderOr.getWorkerAmount()){
            System.out.println("an error occurred: Invalid amount of workers. Oilrig needs at least " + Math.ceil(0.1 * senderOr.initialCrewOilrig) + "workers");
        }
        // Bedingung III) Jede Plattform kann in jeder Kategorie maximal vier Versorgungsschiffe mehr zugeordnet haben als in der initialen Konfiguration.
        if (ShipType.equals("smallship")) {
            if (!Objects.requireNonNull(receiverOr).checkOilrigCanReceiveSmallShip()) {
                System.out.println("an error occurred: receiving oilrig cannot hold that amount of small ships at a time");
            }
        }
        if (ShipType.equals("bigship")) {
            if (!Objects.requireNonNull(receiverOr).checkOilrigCanReceiveBigShip()) {
                System.out.println("an error occurred: receiving oilrig cannot hold that amount of small ships at a time");
            }
        }

        // Wenn eine Evakuierung vorliegt können die Bedingung II und IV außer Kraft gesetzt werden
        if (mayday) {
            // Bedingung II) Auf keiner Plattform dürfen sich mehr als doppelt so viele Mitarbeitende befinden wie initial vorhanden waren.
            assert receiverOr != null;
            int maxWorkers = receiverOr.initialCrewOilrig * 2;
            if (maxWorkers < (receiverOr.getWorkerAmount() + amount)) {
                System.out.println("an error occurred: receiving oilrig cannot hold that amount of workers at a time");
            }
            // Bedingung IV) Keine Plattform darf weniger als ein Versorgungschiff haben, außer im Falle einer Evakuierung.
            if (!senderOr.checkTotalShipCountBiggerOne()) {
                System.out.println("an error occurred: more ships required");
            }
        }


        switch(ShipType){
            case "smallship":
                senderOr.transferWorkerOilrigToShip(amount, smallShip);
                senderOr.undockShip(smallShip);
                System.out.println("moving " + smallShip.getShipInformation());
                // => Schiff beladen und abgedockt von Startplattform

                // andocken an Zielplattform
                receiverOr.dockShip(smallShip);
                receiverOr.transferAllWorkerShipToOilrig(smallShip);
                break;
            case "bigship":
                senderOr.transferWorkerOilrigToShip(amount, bigShip);
                senderOr.undockShip(bigShip);
                System.out.println("moving " + bigShip.getShipInformation());
                // => Schiff beladen und abgedockt von Startplattform

                // andocken an Zielplattform
                receiverOr.dockShip(bigShip);
                //System.out.println("test: " + bigShip.GetShipInformation());
                receiverOr.transferAllWorkerShipToOilrig(bigShip);
                break;
            default:
                System.out.println("an error occurred: ship does not exist");
        }
    }

    //@author Jonas
    public static ArrayList<Oilrig> getOtherOilrigs(int senderID){
        ArrayList<Oilrig> returnOrList = new ArrayList<>();
        for (int i = 0; i < oilrigs.size(); i++) {
            Oilrig temp = new Oilrig(oilrigs.get(i));
            returnOrList.add(temp);
        }
        //if (returnOrList == oilrigs) System.out.println("Selber Array");
        //if (returnOrList.get(0).getId() == oilrigs.get(0).getId()) System.out.println("wtf");
        for (int i = 0; i < oilrigs.size(); i++) {
            if (oilrigs.get(i).getId() == senderID) {
                returnOrList.remove(i);
            }
        }
        if (returnOrList.get(0).workersOnOilrig == getPlatByID(1).workersOnOilrig) System.out.println("Same");
        if (returnOrList.get(0).workersOnOilrig.equals(getPlatByID(1).workersOnOilrig)) System.out.println("Same2");
        returnOrList.get(0).workersOnOilrig.add(new Worker(-1000));
        if (returnOrList.get(0).workersOnOilrig == getPlatByID(1).workersOnOilrig) System.out.println("Same3");
        if (returnOrList.get(0).workersOnOilrig.equals(getPlatByID(1).workersOnOilrig)) System.out.println("Same4");

        return returnOrList;
    }

    //@author Louis, Ayman
    //Output Header und Ladebalken
    public static void printStartupHeader() {
        try {
            System.out.print("\n\n"); //Header
            System.out.println(" ____  ____  _      ____   ____   ____ ");
            System.out.println("/    \\l    j| T    |    \\ l    j /    T");
            System.out.println("|    | |  T | |    |  D  ) |  T Y   __j");
            System.out.println("|  O | |  | | |    |    |  |  | T  |  _");
            System.out.println("|    | |  | | l___ |    \\  |  | |  l_j|");
            System.out.println("|    |j    l|     T|  .  Y j  l |     |");
            System.out.println("\\____/|____jl_____jl__j\\_j|____jl___,_/");

            System.out.print("\n\nLoading   "); //Ladebalken
            Thread.sleep(800);
            System.out.print("..........................");
            Thread.sleep(500);
            System.out.print("..........");
            Thread.sleep(1000);
            System.out.print(".................");
            Thread.sleep(600);
            System.out.print("   finshed");
            Thread.sleep(800);
            System.out.print("\n\n");
            System.out.print("type in 'help' for information");
            System.out.print("\n\n");
        } catch (InterruptedException ie) {     //Thread.sleep kann InterruptedException werfen
            System.out.println("an error occured: " + ie.getMessage());
        }
    }

    //@author Louis
    //Output für Help Befehl
    public static void printHelp() {
        System.out.println("-------------------------------------------------------- HELP --------------------------------------------------------");
        System.out.println("help 				                                                        = (This window)");
        System.out.println("overview  	 		                                                        = open overview");
        System.out.println("oilrig [oilrig ID] 	                                                        = open oilrig");
        System.out.println("move [ship ID] [worker amount] [sending oilrig ID] [receiving oilrig ID]    = transfer Ship with amount of workers");
        System.out.println("evacuate [oilrig ID]                                                        = evacuate oilrig");
        System.out.println("exit				                                                        = exit programm");
        System.out.println("\n\n");
    }

    /**
     * @autor Jonas
     */
    private static int counterShips = 1;
    private static int counterWorker = 1;

    public static int getCounterShips() {
        return counterShips;
    }
    public static int getCounterWorker(){
        return counterWorker;
    }
    public static void addCounterShips(){
        counterShips++;
    }
    public static void addCounterWorker(){
        counterWorker++;
    }


    //@author Jonas
    // Evakuierungsmethoden
    public static boolean checkEvacuationSpace(Oilrig senderOr){
        ArrayList<EvacuationPlanerItem> ep = new ArrayList<>();

        int spaceAvailable = (senderOr.getSmallShipAmount() * 50) + (senderOr.getBigShipAmount() * 100);
        int spaceNeeded = senderOr.getWorkerAmount();
        int difference = spaceAvailable - spaceNeeded;
        if (difference >= 0){
            //Keine weiter Schiffe benötigt
            /** Mögliche visuelle Darstellung vom Evakuierungsplan
             *
             * Evakuierungsplan:
             * Ship 1:  crew 50/50, => Oilrig 2
             * Ship 7:  crew 88/100, => Oilrig 3
             */

            ArrayList<EvacuationPlanerItem> epSmallShips = new ArrayList<>();
            ArrayList<EvacuationPlanerItem> epBigShips = new ArrayList<>();

            calculatePlan(spaceNeeded, epSmallShips, epBigShips, senderOr, ep);

            /*
            ArrayList<Oilrig> otherOrs = Methods.getOtherOilrigs(id);

            ArrayList<EvacuationPlanerItem> epSmallShips = new ArrayList<>();
            ArrayList<EvacuationPlanerItem> epBigShips = new ArrayList<>();

            for (ShipSmall ship : smallShipsOnOilrig) {
                //System.out.println(ship.getId());
                epSmallShips.add(new EvacuationPlanerItem(ship.getId(), "smallship"));
                //idsSmallShip.add(ship.getId());
            }
            for (ShipBig ship : bigShipsOnOilrig) {
                //System.out.println(ship.getId());
                epBigShips.add(new EvacuationPlanerItem(ship.getId(),  "bigship"));
                //idsBigShip.add(ship.getId());
            }

            int avaSmallShips = getSmallShipAmount();
            int avaBigShips = getBigShipAmount();

            // Rechnung zur gleichmäßigen Verteilung der Arbeiter auf die Schiffe
            int totalEqualShips = (2 * avaBigShips) + avaSmallShips;        // ShipBig entspricht nach Kapazität zwei kleinen Schiffen
            double workerPerShip = (double)(spaceNeeded / totalEqualShips); // runterbrechen: wieviele Arbeiter pro Schiff (Dezimalzahl)
            int evenWorkerPerShip = (int) Math.floor(workerPerShip);        // Dezimalzahl abrunden (Zahl wird ungenau)
            int temp = evenWorkerPerShip * totalEqualShips;                 // Hochrechnung der ungenauen Zahl auf den benötigten Gesamtwert für Arbeiter
            int diffFormDouble = spaceNeeded - temp;                        // Differenz zwischen eigentlich benötigtem Platz und der Hochrechnung der ungenauen Zahl (temp) --- überschüssige Arbeiter


            // Planerstellung evtl. auslagern

            for (int i = 0; i < epBigShips.size(); i++){
                for (int iOr = 0; iOr < otherOrs.size(); iOr++) {
                    // Wenn voll zum Nächsten
                    if (otherOrs.get(iOr).workersOnOilrig.size() < evenWorkerPerShip) break;

                    int freeSpace = otherOrs.get(iOr).getFreeCapacity();
                    EvacuationPlanerItem tempItem = epBigShips.get(i);
                    //System.out.println(tempItem.shipId);

                    if ((freeSpace - (2 * evenWorkerPerShip) >= 0 && tempItem != null)){

                        otherOrs.get(iOr).addEmptyWorkers(2 * evenWorkerPerShip);
                        tempItem.destinationOr = otherOrs.get(iOr).getId();
                        tempItem.usedCrew = 2 * evenWorkerPerShip;
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
                        otherOrs.get(iOr).addEmptyWorkers(evenWorkerPerShip);
                        tempItem.destinationOr = otherOrs.get(iOr).getId();
                        tempItem.usedCrew = evenWorkerPerShip;
                        ep.add(tempItem);
                        System.out.println("ID: " + tempItem.shipId + ", type = " + tempItem.type + ", dest: " + tempItem.destinationOr);
                        break;
                    }
                }
            }
            System.out.println(getEvacuationPlanerInfo());

             */
        }
        else {
            System.out.println("Debug: Hilfe rufen! Alaaaaarm");
            //Weiter Schiffe anfordern
            ArrayList<Oilrig> otherOrs = Methods.getOtherOilrigs(senderOr.getId());
            ArrayList<EvacuationPlanerItem> epSmallShips = new ArrayList<>();
            ArrayList<EvacuationPlanerItem> epBigShips = new ArrayList<>();

            while (difference < 0){
                System.out.println("Diff: " + difference);
                if (difference <= -100){
                    EvacuationPlanerItem tempForNotNull = callForBigShipEP(otherOrs, "bigship");
                    if (tempForNotNull != null) {
                        epBigShips.add(tempForNotNull);
                        System.out.println("Helping ship => ID " + tempForNotNull.shipId);
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
                        System.out.println("Helping ship => ID " + tempForNotNull.shipId);
                        difference -= 100;
                    }
                    else {
                        System.out.println("an error occurred: something went wrong while calling for help");
                        return false;
                    }
                    // @Jonas Maybe/Maybenot Abbruchbedingung für kein Schiff vorhanden
                }
            }
            calculatePlan(spaceNeeded, epSmallShips, epBigShips, senderOr, ep);
        }
        return true;
    }

    private static EvacuationPlanerItem callForBigShipEP(ArrayList<Oilrig> otherOr, String type){
        if (type == "bigship"){
            for (Oilrig oilrig : otherOr) {
                if (!oilrig.bigShipsOnOilrig.isEmpty()) {
                    ShipBig ship = oilrig.bigShipsOnOilrig.get(0);
                    return new EvacuationPlanerItem(ship.getId(), type);
                }
            }
        } else {
            for (Oilrig oilrig : otherOr) {
                if (!oilrig.smallShipsOnOilrig.isEmpty()) {
                    ShipSmall ship = oilrig.smallShipsOnOilrig.get(0);
                    return new EvacuationPlanerItem(ship.getId(), type);
                }
            }
        }
        return null;
    }



    public static void calculatePlan(int spaceNeeded, ArrayList<EvacuationPlanerItem> epSmallShips, ArrayList<EvacuationPlanerItem> epBigShips, Oilrig senderOr, ArrayList<EvacuationPlanerItem> ep) {
        ArrayList<Oilrig> otherOrs = Methods.getOtherOilrigs(senderOr.getId());
        for (ShipSmall ship : senderOr.smallShipsOnOilrig) {
            epSmallShips.add(new EvacuationPlanerItem(ship.getId(), "smallship"));
        }
        for (ShipBig ship : senderOr.bigShipsOnOilrig) {
            epBigShips.add(new EvacuationPlanerItem(ship.getId(),  "bigship"));
        }

        int avaSmallShips = senderOr.getSmallShipAmount();
        int avaBigShips = senderOr.getBigShipAmount();

        // Rechnung zur gleichmäßigen Verteilung der Arbeiter auf die Schiffe
        int totalEqualShips = (2 * avaBigShips) + avaSmallShips;        // ShipBig entspricht nach Kapazität zwei kleinen Schiffen
        double workerPerShip = (double)(spaceNeeded / totalEqualShips); // runterbrechen: wieviele Arbeiter pro Schiff (Dezimalzahl)
        int evenWorkerPerShip = (int) Math.floor(workerPerShip);        // Dezimalzahl abrunden (Zahl wird ungenau)
        int temp = evenWorkerPerShip * totalEqualShips;                 // Hochrechnung der ungenauen Zahl auf den benötigten Gesamtwert für Arbeiter
        int diffFormDouble = spaceNeeded - temp;                        // Differenz zwischen eigentlich benötigtem Platz und der Hochrechnung der ungenauen Zahl (temp) --- überschüssige Arbeiter

        Oilrig platform4 = new Oilrig(4, 120, 2, 2);

        for (int i = 0; i < epBigShips.size(); i++){
            for (int iOr = 0; iOr < otherOrs.size(); iOr++) {
                // Wenn voll zum Nächsten
                if (otherOrs.get(iOr).getWorkersOnOilrig().size() < evenWorkerPerShip) break;

                //if (otherOrs.get(iOr) == getPlatByID(1)) System.out.println("Selbes Objekt");

                int freeSpace = otherOrs.get(iOr).getFreeCapacity();
                EvacuationPlanerItem tempItem = epBigShips.get(i);
                //System.out.println(tempItem.shipId);

                if ((freeSpace - (2 * evenWorkerPerShip) >= 0 && tempItem != null)){
                    ArrayList<Worker> tempxx = new ArrayList<>();
                    System.out.println(getPlatByID(1).getWorkerAmount());
                    tempxx.addAll(addEmptyWorkers(2 * evenWorkerPerShip));
                    System.out.println(getPlatByID(1).getWorkerAmount());
                    Oilrig tor = otherOrs.get(iOr);

                    System.out.println("x:" + getPlatByID(1).getWorkerAmount());
                    platform4.workersOnOilrig.addAll(tempxx);
                    //otherOrs.get(iOr).getWorkersOnOilrig().addAll(addEmptyWorkers(2 * evenWorkerPerShip));
                    System.out.println("x2:" + getPlatByID(1).getWorkerAmount());
                    System.out.println("o:" + otherOrs.get(iOr).workersOnOilrig.size());
                    if (otherOrs.get(0).workersOnOilrig == getPlatByID(1).workersOnOilrig) System.out.println("Same");
                    tempItem.destinationOr = otherOrs.get(iOr).getId();
                    tempItem.usedCrew = 2 * evenWorkerPerShip;
                    ep.add(tempItem);
                    //System.out.println("ID: " + tempItem.shipId + ", type = " + tempItem.type + ", dest: " + tempItem.destinationOr);
                    break;
                }
            }
        }
        for (int i = 0; i < epSmallShips.size(); i++){
            for (int iOr = 0; iOr < otherOrs.size(); iOr++) {
                // Wenn voll zum Nächsten
                if (otherOrs.get(iOr).getWorkerAmount() < evenWorkerPerShip) break;

                int freeSpace = otherOrs.get(iOr).getFreeCapacity();
                EvacuationPlanerItem tempItem = epSmallShips.get(i);

                if ((freeSpace - evenWorkerPerShip) >= 0 && tempItem != null) {
                    otherOrs.get(iOr).getWorkersOnOilrig().addAll(addEmptyWorkers(evenWorkerPerShip));

                    tempItem.destinationOr = otherOrs.get(iOr).getId();
                    tempItem.usedCrew = evenWorkerPerShip;
                    ep.add(tempItem);
                    //System.out.println("ID: " + tempItem.shipId + ", type = " + tempItem.type + ", dest: " + tempItem.destinationOr);
                    break;
                }
            }
        }
        System.out.println(getEvacuationPlanerInfo(senderOr, ep));
    }

    public static ArrayList<Worker> addEmptyWorkers(int i){
        ArrayList<Worker> temp = new ArrayList<>();
        for (int j = 1; j < i; j++) {
            temp.add(new Worker(j * (-1)));
        }
        System.out.println("2:" + Methods.getPlatByID(1).getWorkerAmount());
        return temp;
    }


    // Große Schiffe und kleine Schiffe werden hier theoretisch als Seperate Liste betrachtet
    // Beispiel: Die Evakuierungsplanliste hat 12 Elemente, 5 kleine Schiff, 7 große Schiffe
    // So gibt diese Methode ein kleines/großes Schiff zurück gemessen an seinen theoretischem Index [0-4] oder [0-6]
    public EvacuationPlanerItem getEP_AtTypeIndex(int i, String type, ArrayList<EvacuationPlanerItem> ep) {
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
    public static String getEvacuationPlanerInfo(Oilrig senderOr, ArrayList<EvacuationPlanerItem> ep){

        String result = "------------------------ Evacuation Plan ------------------------ \n";
        // unfertig
        result += "evacuating " + "Workers from Oilrig [" + "]\n";
        for (EvacuationPlanerItem epItem : ep) {

            int maxCapacity = 0;

            //setzt maxCapacity in Abhängigkeit ob epItem.shipId ein großes oder kleines Schiff ist
            Ship ship = senderOr.getShipById(epItem.shipId);
            if(ship instanceof ShipBig){
                maxCapacity = 100;
            } else if (ship instanceof ShipSmall) {
                maxCapacity = 50;
            } else{
                System.out.println("an error occurred: no ship found");
            }

            // Unfertig
            // Output
            result += "Ship: [" + epItem.shipId + "] from Oilrig [" + senderOr.getShipById(epItem.shipId) + "]" + "\tCrew: [" + epItem.usedCrew + "/" + maxCapacity + "]" + "\t\t→→→    \t\t" + "destinated Oilrig: [" + epItem.destinationOr + "]" + "\n"; //Warning: => StringBuilder benutzen?
        }
        return result;
    }

}