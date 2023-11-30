package Programm;

import java.util.ArrayList;
import java.util.Scanner;
import assets.*;

public class InputOutput {

    public static void startSimulation() {
        //System.out.println("Hallo liebes Programm!");
    }

    //@author Louis
    public static void startupHeader() {
        try {
            System.out.print("\n\n");
            System.out.println(" ____  ____  _      ____   ____   ____ ");
            System.out.println("/    \\l    j| T    |    \\ l    j /    T");
            System.out.println("|    | |  T | |    |  D  ) |  T Y   __j");
            System.out.println("|  O | |  | | |    |    |  |  | T  |  _");
            System.out.println("|    | |  | | l___ |    \\  |  | |  l_j|");
            System.out.println("|    |j    l|     T|  .  Y j  l |     |");
            System.out.println("\\____/|____jl_____jl__j\\_j|____jl___,_/");

            System.out.print("\n\n--Loading Simulation--");
            Thread.sleep(800);
            System.out.print("..........................");
            Thread.sleep(500);
            System.out.print("..........");
            Thread.sleep(1000);
            System.out.print(".................");
            Thread.sleep(600);
            System.out.print("finshed");
            Thread.sleep(800);
            System.out.print("\n\n");
            System.out.print("type in 'help' for information");
            System.out.print("\n\n");
        } catch (Exception e) {

        }

    }

    public static void PrintHelp() {
        System.out.println("--------------- HELP -----------------");
        System.out.println("help 				 = (This window)");
        System.out.println("overview  	 		 = open overview");
        System.out.println("oilrig [oilrig ID] 	 = open oilrig");
        System.out.println("ship [ship ID] 	 	 = open ship");
        System.out.println("move [ship ID]     	 = move ship");
        System.out.println("evacuate [oilrig ID] = evacuate oilrig");
        System.out.println("exit				 = exit programm");
        System.out.println("\n\n");
    }

	/*public enum possibleInputs{
		help, overview, oilrig, ship, move, evacuate, exit;
	}*/



    public static void InputHandler() {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while(true) {
            try {
                input = scanner.nextLine();
                String[] arguments = input.split(" ");
                switch (arguments[0]) {
                    case "help":
                        PrintHelp();
                        break;
                    case "overview":
                        for (int i = 1; i < 5; i++) {
                            Oilrig or = getPlatByID(i);
                            if (or == null) {                            //immer lieber überprüfen
                                System.out.println("Oilrig not found!");
                            }
                            System.out.println(or.GetInformationOverview());
                        }
                        break;
                    case "oilrig": //@autor Jonas
                        int id = Integer.parseInt(arguments[1]);
                        if (id == 1 || id == 2 || id == 3 || id == 4) { //Oilrig 1, 2, 3, oder 4 Infos werden ausgegeben
                            Oilrig or = getPlatByID(id);
                            if (or == null) {                            //immer lieber überprüfen
                                System.out.println("Oilrig not found!");
                            }
                            System.out.println(or.GetInformationOilrig());
                        } else System.out.println("Wrong ID! Please use an ID between 1 and 4.");
                        break;
                    case "ship":// + id:

                        break;
                    case "move":// + id:
                        break;
                    case "evacuate":// + id
                        int ip = Integer.parseInt(arguments[1]);
                        if (ip == 1 || ip == 2 || ip == 3 || ip == 4) { //Oilrig 1, 2, 3, oder 4 Infos werden ausgegeben
                            Oilrig or = getPlatByID(ip);
                            if (or == null) {                            //immer lieber überprüfen
                                System.out.println("Oilrig not found!");
                            }
                            //assets.Oilrig.Evacuate(or);
                        } else System.out.println("Wrong ID! Please use an ID between 1 and 4.");
                        break;
                    case "exit":
                        System.exit(0);
                        System.out.println("System32 wurde geschlossen");
                        break;
                    default:
                        System.out.println("This Command does not exist ");
                        break;
                }

            } catch (Exception e) {

            }
        }
    }

    public void ExitProgramm() {

        Scanner scannerExit = new Scanner(System.in);
        System.out.println("are you sure you want to exit this application? confirm with 'y'");

        try {
            String input = scannerExit.nextLine();
            if (input == "y") {
                System.exit(0);
            }
        } catch (Exception e) {
        }
        scannerExit.close();

    }

    // @author Jonas
    public static ArrayList<Oilrig> a_opf = new ArrayList<Oilrig>();
    private static int shipCounter = 1;
    private static int workerCounter = 1;
    public static int getWorkerCounter() {
        return workerCounter;
    }
    public static void addWorkerCounter(){
        workerCounter++;
    }
    public static int getShipCounter() {
        return shipCounter;
    }
    public static void addShipCounter(){
        shipCounter++;
    }

    public static void initializePlatforms() {

        // Erstellen der Ölplattformen um sie mit ihrer vorgegebenen Konfiguration zu
        // initialisieren
        Oilrig platform1 = new Oilrig(1, 760, 5, 4);
        Oilrig platform2 = new Oilrig(2, 520, 4, 4);
        Oilrig platform3 = new Oilrig(3, 360, 3, 4);
        Oilrig platform4 = new Oilrig(4, 120, 2, 2);
        // ölplattformen in Liste packen
        a_opf.add(platform1);
        a_opf.add(platform2);
        a_opf.add(platform3);
        a_opf.add(platform4);
    }
    //WICHTIG: Wenn diese Methode verwendet wird, muss der Rückgabewert auf NULL geprüft werden
    public static Oilrig getPlatByID(int ID) {
        try {
            for (Oilrig opf : a_opf) {
                if (ID == opf.getId())
                    return opf;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

   /* public static Ship getShipByID(int ID) {
        try {
            for (Oilrig opf : a_opf) {
                if (ID == opf.getId())
                    return opf;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }*/

	/*
	public void OilrigMenuID(int ID) {
		Oilplatform current_opf = getPlatByID(ID);
		System.out.println("Oilrig ID   : " + current_opf.getId());
		System.out.println("docked ships: " + current_opf.getNumberofBigShips());
		System.out.println("small ships : " + current_opf.getNumberofSmallShips());
		System.out.println("big ships   : " + current_opf.getNumberofBigShips());
		System.out.println("workers     : " + current_opf.getNumberofWorkers());
		System.out.println("condition");
	}
	*/

    // -----------------------------------------------------Aufgeräumt--------------------------------------------------------------

    /*
     * public void MoveShipMenu() {
     *
     * }
     *
     * public void EvacuateMenu() {
     *
     * }
     */

}