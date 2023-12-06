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
            Thread.sleep(200);
            System.out.print("..........................");
            Thread.sleep(200);
            System.out.print("..........");
            Thread.sleep(300);
            System.out.print(".................");
            Thread.sleep(200);
            System.out.print("finshed");
            Thread.sleep(100);
            System.out.print("\n\n");
            System.out.print("type in 'help' for information");
            System.out.print("\n\n");
        } catch (Exception e) {

        }

    }

    //@author Louis
    public static void PrintHelp() {
        System.out.println("------------------------------------------ HELP PAGE --------------------------------------------");
        System.out.println("help 				                                                                                        = (This window)");
        System.out.println("overview  	 		                                                                                        = get complete overview");
        System.out.println("oilriginfo [oilrig ID] 	                                                                                    = get oilrig info");
        System.out.println("move [amount] [sender Oilrig id] [reciver Oilrig id] [amount of big ships] [amount of small ships]      = Sends Crew to Destination in Big and Small Ships");
        System.out.println("evacuate [oilrig ID]                                                                                        = evacuate oilrig and safe crew to another oilrig");
        System.out.println("exit				                                                                                        = exit programm");
        System.out.println("\n\n");
    }

    public static Oilrig getPlatformByID(int id, ArrayList<Oilrig> list) {
        for (Oilrig i : list) {
            if (i.getId() == id) {
                return i;
            }
        }
        System.out.println("Oilrig not found!!");
        return null;
    }

    public static void move(int amount, Oilrig sender, Oilrig receiver, int bigShip, int smallShip) {
        int sum = amount;
        if (sum != 0) {
            int[] shipsneeded = getdistribution(sum, bigShip, smallShip);
            for (int i = 0; i < shipsneeded[0]; i++) {
                if(sum >=100){
                    sender.sendBigShip(receiver, 100, false);
                    sum -= 100;
                }else{
                    sender.sendBigShip(receiver, sum, false);
                }
            }
            for (int j = 0; j < shipsneeded[1]; j++) {
                if(sum >=50){
                    sender.sendSmallShip(receiver, 50, false);
                    sum -= 50;
                }else{
                    sender.sendSmallShip(receiver, sum, false);
                }
            }
            System.out.println("Sending Successful");
            System.out.println(sender.GetInformationOverview() + "\n" + receiver.GetInformationOverview());

        } else {
            for (int i = 0; i < bigShip; i++) {
                sender.sendBigShip(receiver, 0, false);
            }
            for (int j = 0; j < smallShip; j++) {
                sender.sendSmallShip(receiver, 0, false);
            }
            System.out.println("Sending Successful");
            System.out.println(sender.GetInformationOverview() + "\n" + receiver.GetInformationOverview());
        }
    }

    //Returnt Array dessen erster Index die Anzahl an Small und der zweite die Anzahl an BigShips ist
    public static int[] getdistribution(int crew, int amountbigship, int amountsmallship) {
        int[] shipsneeded = {0, 0};
        int maxcrewcap = amountbigship * 100 + amountsmallship * 50;
        int bigshipneeded = 0;
        int smallshipneeded = 0;
        if (crew > 0) {
            while(crew >= 1){
                if (maxcrewcap >= crew){
                    if(bigshipneeded < amountbigship) {
                        bigshipneeded++;
                        crew = crew - 100;
                    }else if(smallshipneeded < amountsmallship) {
                        smallshipneeded++;
                        crew = crew - 50;
                    }
                }else{
                   int difference = (maxcrewcap - crew) * -1; // 1000 - 1200 = - 200
                   int[] extraordinarily = getdistribution(difference , 1000, 1000);
                   System.out.println("Max capacity of ships on board is reached!!");
                   System.out.println("Need to add " + extraordinarily[0] + " Big ships and " + extraordinarily[1] + " Small ships to compensate difference");
                   return new int[]{amountbigship + extraordinarily[0], amountsmallship + extraordinarily[1]};
                }
            }
            shipsneeded[0] = bigshipneeded;
            shipsneeded[1] = smallshipneeded;
            return shipsneeded;
        } else {
            return shipsneeded;
        }

    }

}