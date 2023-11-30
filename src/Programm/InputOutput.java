package Programm;

import java.util.ArrayList;
import java.util.Scanner;
import assets.*;

public class InputOutput {

    public static void startSimulation() {
        System.out.println("Hallo liebes Programm!");
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
            System.out.print(".");
            Thread.sleep(500);
            System.out.print("..");
            Thread.sleep(1000);
            System.out.print("...");
            Thread.sleep(600);
            System.out.print("finshed");
            Thread.sleep(800);
            System.out.print("\n\n");
            System.out.print("type in 'help' for information");
            System.out.print("\n\n");
        } catch (Exception e) {
        }

    }

    public void PrintHelp() {
        System.out.println("----------------- HELP -----------------");
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

    public void InputHandler() {
        Scanner scannerInputHandler = new Scanner(System.in);
        try {
            String input = scannerInputHandler.nextLine();
            String[] arguments = input.split(" ");

            switch(arguments[0]) {
                case "help":
                    PrintHelp();
                    break;
                case "overview":
                    break;
                //assets.Oilrig.GetInformationOverview();
                case "oilrig": //@autor Jonas
                    int id = Integer.parseInt(arguments[1]);
                    if (id == 1 || id == 2 || id == 3 || id == 4) { //Oilrig 1-4 Infos werden ausgegeben
                        Oilrig or = getPlatByID(id);
                        if (or == null) {							//immer lieber überprüfen
                            System.out.println("Oilrig not found!");
                        }
                        String result = or.GetInformationOilrig();
                    }
                    else System.out.println("Wrong ID! Please use an ID between 1 and 4.");

                    break;
                //assets.Oilrig.GetInformationOilrig(id);
                case "ship":// + id:
                    break;
                //assets.Ship.GetInformationShip(id);
                case "move":// + id:
                    break;
                //assets.Ship.move(id);
                case "evacuate":// + id:
                    break;
                //assets.Oilrig.evacuate(id)
                case "exit":
                    ExitProgramm();
                    break;
            }
        } catch (Exception e) {

        }
        scannerInputHandler.close();
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

    public void initializePlatforms() {

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
    //WICHTG: Wenn diese Methode verwendet wird, muss der Rückgabewert auf NULL geprüft werden
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