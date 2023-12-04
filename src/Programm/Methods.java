package Programm;

import assets.Oilrig;
import java.util.ArrayList;

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
        System.out.println("------------------------------------------ HELP PAGE --------------------------------------------");
        System.out.println("help 				                                        = (This window)");
        System.out.println("overview  	 		                                        = open overview");
        System.out.println("oilriginfo [oilrig ID] 	                                    = open oilrig");
        System.out.println("movecrew [amount] [sender Oilrig id] [reciver Oilrig id]    = Sends Crew to Destination in Big and Small Ships");
        System.out.println("evacuate [oilrig ID]                                        = evacuate oilrig and safe crew to another oilrig");
        System.out.println("exit				                                        = exit programm");
        System.out.println("\n\n");
    }

    public static Oilrig getPlatformByID(int id, ArrayList<Oilrig> list){
        for (Oilrig i : list) {
            if(i.getId() == id){
                return i;
            }
        }
        System.out.println("Oilrig not found!!");
        return null;
    }

    /*
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
    */

    /*                           Big Small
                        move 0    21 22 1   0
                        move 0    21 22 0   1
                        move 100  21 22 1   0
                        move 100  21 22 1   1 Überflüssig
                        move 100  21 22 0   1 Nicht möglich
                        move 120  21 22 100 200 Nicht möglich
                        Min an Crew und Schiffen beachten wenn
    */

    public static void move(int amount, Oilrig sender, Oilrig receiver, int bigShip, int smallShip){
        if(amount != 0){

        }else{

        }
    }

    //   Crew   Ist Schiff  max Cap aS  Differenz   Lösung
    //1: 760 -> 5*100 + 4*50 = 700 -> posdiff > 50 = Großes Leeres Schiff
    //2: 520 -> 2*100 + 2*50 = 300 -> posdiff 220 > 50 & 100 = 2*100 + 1*50
    //3: 360 -> 5*100 + 3*50 = 650 -> posdiff 0 = 4*100
    //4: 420 -> 6*100 + 2*50 = 700 -> posdiff 0 = 4*100 + 1*50

    //Returnt Array dessen erster Index die Anzahl an Small und der zweite die Anzahl an BigShips ist
    /*
    public static getdistribution(int crew, int amountbigship, int amountsmallship){
        int[] ships = new int[2];
        int maxcrewcap = amountbigship*100 + amountsmallship*100;
        int bigshipneeded = 0;
        int smallshipneeded = 0;

        if(crew < maxcrewcap){
            if((crew % 100) == 0){
                bigshipneeded = crew / 100;
                System.out.println("Can be handled by Big ship only with " + bigshipneeded + " big ships");
            }else{
                bigshipneeded = crew / 100;
                smallshipneeded = (crew % 100) / 50;
                System.out.println("Cant be handled by Big ship has to use" + bigshipneeded + " as big and " + smallshipneeded + "as small");
            }
        }else{
            int difference = crew - maxcrewcap * -1;
            System.out.println("Max capship is greater than crew by " + difference);
        }
    }*/

}
