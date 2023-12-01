package Programm;

import java.lang.reflect.Array;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Scanner;

import assets.*;

public class InputOutput {

    //Initialise Oilrigsplattforms with Ships

    public static void InputHandler(ArrayList<Oilrig> oilrigs) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        Oilrig sender;
        Oilrig receiver;
        while (true) {
            try {
                input = scanner.nextLine();
                String[] arguments = input.split(" ");
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
                            System.out.println("ERROR");
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
                            System.out.println("ERROR");
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
                            System.out.println("ERROR");
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
                    case "overview":
                        try {
                            for (Oilrig i : oilrigs) {
                                System.out.println(i.GetInformationOverview());
                            }
                        } catch (Exception e) {
                            System.out.println("ERROR");
                        }
                        break;
                    //@autor Jonas
                    case "oilrig":
                        try {
                            for (Oilrig i : oilrigs) {
                                if (Integer.parseInt(arguments[1]) == i.getId()) {
                                    System.out.println(i.GetInformationOverview());
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("ERROR");
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