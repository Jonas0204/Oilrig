package Programm;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Scanner;

import assets.*;

public class InputOutput {
    public static void InputHandler(ArrayList<Oilrig> oilrigs) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        Oilrig sender;
        Oilrig receiver;
        int amountofcrew = 0;
        int amountbigships = 0;
        int amountsmallships = 0;

        while (true) {
            try {
                input = scanner.nextLine();
                String[] arguments = input.split(" ");
                switch (arguments[0]) {
                    case "help":
                        Methods.PrintHelp();
                        break;
                    case "move":
                        try {
                            amountofcrew = Integer.parseInt(arguments[1]);
                            sender = Methods.getPlatformByID(Integer.parseInt(arguments[2]), oilrigs);
                            receiver = Methods.getPlatformByID(Integer.parseInt(arguments[3]), oilrigs);
                            amountbigships = Integer.parseInt(arguments[4]);
                            amountsmallships = Integer.parseInt(arguments[5]);
                            Methods.move(amountofcrew, sender, receiver, amountbigships, amountsmallships);
                        } catch (Exception e) {
                            System.out.println("ERROR");
                        }
                        break;
                    case "evacuate":
                        //EVACUATE
                        break;
                    case "overview":
                        try {
                            for (Oilrig i : oilrigs) {
                                System.out.println(i.GetInformationOverview());
                            }
                        } catch (Exception e) {
                            System.out.println("ERROR");
                        }
                        break;
                    case "oilriginfo":
                        try {
                            sender = Methods.getPlatformByID(Integer.parseInt(arguments[1]), oilrigs);
                            System.out.println(sender.GetInformationOilrig());
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