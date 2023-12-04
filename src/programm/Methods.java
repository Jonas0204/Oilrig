package programm;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import assets.*;

public class Methods {

    private static ArrayList<Oilrig> oilrigs = new ArrayList<Oilrig>();

    public static void handleInput(ArrayList<Oilrig> oilrigsParam) {
        oilrigs = oilrigsParam;

        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (true) {

            input = scanner.nextLine();
            String[] arguments = input.split(" ");  //Louis: führt zu einem Fehler: nach korrektem Input kann beliebiger Text eingefügt werden
            switch (arguments[0]) {
                case "help":
                    if (arguments.length != 1) {
                        System.out.println("invalid amount of arguments provided");
                        break;
                    }
                    Methods.printHelp();
                    break;
                case "move": //move [ship ID] [worker amount] [sending oilrig ID] [receiving oilrig ID]
                    if (arguments.length != 5) {
                        System.out.println("invalid amount of arguments provided");
                        break;
                    }
                    moveWorkers(arguments[1], arguments[2], arguments[3], arguments[4], false);
                    break;
                case "evacuate"://evacuate [Oilrig id]
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
                case "overview":
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
                case "oilrig": // generiert zu viele Worker
                    if (arguments.length != 2) {
                        System.out.println("invalid amount of arguments provided");
                        break;
                    }

                    try {
                        //Louis: try catch für Integer.parseInt einfügen
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
                case "exit":
                    if (arguments.length != 1) {
                        System.out.println("invalid amount of arguments provided");
                        break;
                    }
                    System.exit(0);
                    break;
                default:
                    System.out.println("This Command does not exist. Try 'help' for information");
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

    public static boolean evacuation(int evacuationId){
        Oilrig eOr = getPlatByID(evacuationId);
        boolean successful = eOr.checkEvacuationSpace();




        return true;
    }

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
            System.out.println("an error occurred: ID's or amounts invalid");
        }
        boolean idExists = Methods.existsID(senderID, receiverID);
        if (!idExists) {
            System.out.println("an error occurred: invalid ID");
            return;
        }

        Oilrig senderOr = Methods.getPlatByID(senderID);
        Oilrig receiverOr = Methods.getPlatByID(receiverID);

        // Feststellen welche Art von Schiff wir verschieben
        assert senderOr != null;
        ShipSmall smallShip = senderOr.getSmallShipById(shipID);
        ShipBig bigShip = senderOr.getBigShipById(shipID);
        String ShipType = "";
        if (smallShip == null && bigShip == null) {
            System.out.println("an error occurred: Ship returns 'null'");
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
                // => Schiff beladen und abgedocked

                // docking
                receiverOr.dockShip(smallShip);
                receiverOr.transferAllWorkerShipToOilrig(smallShip);
                break;
            case "bigship":
                senderOr.transferWorkerOilrigToShip(amount, bigShip);
                senderOr.undockShip(bigShip);
                System.out.println("moving " + bigShip.getShipInformation());
                // => Schiff beladen und abgedocked

                // docking
                receiverOr.dockShip(bigShip);
                //System.out.println("test: " + bigShip.GetShipInformation());
                receiverOr.transferAllWorkerShipToOilrig(bigShip);
                break;
            default:
                System.out.println("an error occurred: ship does not exist");
        }
    }

    public static ArrayList<Oilrig> getOtherOilrigs(int senderID){
        ArrayList<Oilrig> returnOrList = new ArrayList<>();
        returnOrList.addAll(oilrigs);
        for (int i = 0; i < oilrigs.size(); i++) {
            if (oilrigs.get(i).getId() == senderID) {
                returnOrList.remove(i);
            }
        }
        return returnOrList;
    }

    private static int counterShips = 1;
    private static int counterWorker = 1;

    //@author Louis, Jonas
    public static void printStartupHeader() {
        try {
            System.out.print("\n\n");
            System.out.println(" ____  ____  _      ____   ____   ____ ");
            System.out.println("/    \\l    j| T    |    \\ l    j /    T");
            System.out.println("|    | |  T | |    |  D  ) |  T Y   __j");
            System.out.println("|  O | |  | | |    |    |  |  | T  |  _");
            System.out.println("|    | |  | | l___ |    \\  |  | |  l_j|");
            System.out.println("|    |j    l|     T|  .  Y j  l |     |");
            System.out.println("\\____/|____jl_____jl__j\\_j|____jl___,_/");

            System.out.print("\n\nLoading   ");
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
        } catch (InterruptedException ie) {             //Thread.sleep kann InterruptedException werfen
            System.out.println("an error occured: " + ie.getMessage());
        }
    }

    //@author Louis
    public static void printHelp() {
        System.out.println("--------------------------------------------------------- HELP ---------------------------------------------------------");
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

    public static void evacuate(Oilrig or){
        or.evacuate = true;

        or.checkEvacuationSpace();

        or.evacuate = false;
    }

}