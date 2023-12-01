package Programm;

import java.lang.reflect.Array;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Scanner;

import assets.*;

public class InputOutput {

    //Initialise Oilrigsplattforms with Ships

    //Spezifikation der Exceptions wäre besser
    public static void InputHandler(ArrayList<Oilrig> oilrigs) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        Oilrig sender;
        Oilrig receiver;
        while (true) {
            try {
                input = scanner.nextLine();
                String[] arguments = input.split(" ");  //Louis: führt zu einem Fehler: nach korrektem Input kann beliebiger Text eingefügt werden
                switch (arguments[0]) {
                    case "help":
                        Methods.PrintHelp();
                        break;
                    case "moveemptybigship":
                        //moveemptybigship [sender Oilrig id] [reciver Oilrig id]
                        try {
                            for (Oilrig i : oilrigs) {
                                if (Integer.parseInt(arguments[1]) == i.getId()) {
                                    sender = i;
                                    for (Oilrig j : oilrigs) {
                                        if (Integer.parseInt(arguments[2]) == j.getId()) {
                                            receiver = j;
                                            Methods.moveEmptyBigShip(i, j);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("ERROR");//unzureichende Info
                        }
                        break;
                    case "moveemptysmallship":
                        //moveemptysmallship [sender Oilrig id] [reciver Oilrig id]
                        try {
                            for (Oilrig i : oilrigs) {
                                if (Integer.parseInt(arguments[1]) == i.getId()) {
                                    sender = i;
                                    for (Oilrig j : oilrigs) {
                                        if (Integer.parseInt(arguments[2]) == j.getId()) {
                                            receiver = j;
                                            Methods.moveEmptySmallShip(i, j);
                                        }
                                    }
                                } else {
                                    System.out.println("Oilrig not found!!");
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("ERROR");//unzureichende Info
                        }
                        break;
                    case "movecrew":
                        int amount = 0;
                        int amountbigships = 0;
                        int amountsmallships = 0;
                        // movecrew [amount] [sender Oilrig id] [reciver Oilrig id] [amount BigShips] [amount SmallShips]
                        try {
                            amount = Integer.parseInt(arguments[1]);
                            for (Oilrig i : oilrigs) {
                                if (Integer.parseInt(arguments[2]) == i.getId()) {
                                    sender = i;
                                    for (Oilrig j : oilrigs) {
                                        if (Integer.parseInt(arguments[3]) == j.getId()) {
                                            receiver = j;
                                            amountbigships = Integer.parseInt(arguments[4]);
                                            amountsmallships = Integer.parseInt(arguments[5]);
                                            if (amountbigships < sender.getBigShipAmount() && amountbigships < sender.getSmallShipAmount()) {
                                                Methods.moveCrew(amount, sender, receiver, amountbigships, amountsmallships);
                                            }
                                        }
                                    }
                                } else {
                                    System.out.println("Oilrig not found!!");
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("ERROR");//unzureichende Info
                        }
                        break;
                    case "evacuate":
                        //!! Wird die Schwerste Aufgabe werden !!
                        //evacuate [Oilrig id]
                        /*
                        int ip = Integer.parseInt(arguments[1]);
                        if (ip == 1 || ip == 2 || ip == 3 || ip == 4) { //Oilrig 1, 2, 3, oder 4 Infos werden ausgegeben
                            Oilrig or = getPlatByID(ip);
                            if (or == null) {                            //immer lieber überprüfen
                                System.out.println("Oilrig not found!");
                            }
                            //assets.Oilrig.Evacuate(or);
                        } else System.out.println("Wrong ID! Please use an ID between 1 and 4.");
                        */
                        break;
                    //@author Jonas
                    case "overview": // Louis: Input 'overview x' funktioniert, muss behoben werden!
                        try {
                            for (Oilrig i : oilrigs) {
                                System.out.println(i.GetInformationOverview());
                            }
                        } catch (Exception e) {
                            System.out.println("ERROR");//unzureichende Info
                        }
                        break;
                    //@autor Jonas
                    case "oilrig": // Louis: gibt falschen output, Methode für Details fehlt - Update: fixed
                        try {
                            //Louis: try catch für Integer.parseInt einfügen
                            if(Integer.parseInt(arguments[1]) >= 5 || Integer.parseInt(arguments[1]) <= 0){ //ID muss geprüft werden, wie ID einfügen?
                                System.out.println("oilrig with id " + Integer.parseInt(arguments[1]) + " not found");
                            }
                            else{
                                for (Oilrig i : oilrigs) { //Louis: Input: 'Oilrig e' führt zu Error, Input: 'Oilrig 5' zu nichts
                                    if (Integer.parseInt(arguments[1]) == i.getId()) {
                                        System.out.println(i.GetInformationOilrig()); // Ship-IDs nicht fortlaufend?
                                    }
                                }
                            }
                            // Louis: Nutzung des try-catch als if-else ist nicht gut. Es muss vorher geprüft werden, ob id korrekt
                        } catch (Exception e) {// was kann exception werfen? getID()? dann Nullexception?
                            System.out.println("ERROR");//unzureichende Info
                        }
                        break;
                    case "exit":
                        System.out.println("Close simulation");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("This Command does not exist ");
                        break;
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}