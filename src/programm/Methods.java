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

        // Bedingung I) Auf jeder Plattform müssen sich immer mindestens 10% initialen Besatzung an Mitarbeitenden befinden, außer die Plattform wurde evakuiert.
        int minWorkers = (int) Math.ceil(0.1 * senderOr.initialCrewOilrig);
        if (minWorkers >  senderOr.getWorkerAmount()){
            System.out.println("an error occurred: Invalid amount of workers. Oilrig needs at least " + Math.ceil(0.1 * senderOr.initialCrewOilrig) + "workers");
        }
        // Bedingung II) Auf keiner Plattform dürfen sich mehr als doppelt so viele Mitarbeitende befinden wie initial vorhanden waren.
        assert receiverOr != null;
        int maxWorkers = receiverOr.initialCrew * 2;
        if (maxWorkers < (receiverOr.getWorkerAmount() + amount)) {
            System.out.println("an error occurred: receiving oilrig cannot hold that amount of workers at a time");
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
        // Bedingung IV) Keine Plattform darf weniger als ein Versorgungschiff haben, außer im Falle einer Evakuierung.
        if (!senderOr.checkTotalShipCountBiggerOne()) {
            System.out.println("an error occurred: more ships required");
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

