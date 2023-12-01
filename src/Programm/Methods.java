package Programm;

import assets.Oilrig;

//Only Static Methods are implemented here
public class Methods {

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

    //@author Louis
    public static void PrintHelp() {
        System.out.println("--------------------------- HELP -----------------------------");
        System.out.println("help 				 = (This window)");
        System.out.println("overview  	 		 = open overview");
        System.out.println("oilrig [oilrig ID] 	 = open oilrig");
        System.out.println("moveemptybigship [sender Oilrig id] [reciver Oilrig id] = move an empty BigShip to another destination");
        System.out.println("moveemptysmallship [sender Oilrig id] [reciver Oilrig id] = move an empty SmallShip to another destination");
        System.out.println("movecrew [amount] [sender Oilrig id] [reciver Oilrig id] [amount BigShips] [amount SmallShips] = Sends Crew to Destination");
        System.out.println("evacuate [oilrig ID] = evacuate oilrig and safe crew to another oilrig");
        System.out.println("exit				 = exit programm");
        System.out.println("\n\n");
    }

    //Schauen das es nicht null ist -> Adde Try and Catch Später
    public static void moveEmptyBigShip(Oilrig sender, Oilrig receiver){
        //Schaue nach Emptyship auf Sender und sende an receiver
        receiver.addBigShip(sender.getEmptyBigShip());
    }

    public static void moveEmptySmallShip(Oilrig sender, Oilrig receiver){
        //Schaue nach Emptyship auf Sender und sende an receiver
        receiver.addSmallShip(sender.getEmptySmallShip());
    }

    public static void moveBigShip(Oilrig sender, Oilrig receiver){
        //Sende BigShip von sender an receiver
        receiver.addSmallShip(null);
    }

    public static void moveSmallShip(Oilrig sender, Oilrig receiver){
        //Sende SmallShip von sender an receiver
        receiver.addSmallShip(null);
    }

    public static void moveCrew(int amount, Oilrig sender, Oilrig receiver, int bigShip, int smallShip){

    }

}
