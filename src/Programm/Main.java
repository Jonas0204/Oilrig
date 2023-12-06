package Programm;


import assets.Oilrig;

import java.util.ArrayList;

public class Main {
    //@author Matthias
    public static void main(String[] args) {
        ArrayList<Oilrig> oilrigs = new ArrayList<>();
        Oilrig platform1 = new Oilrig(1, 760, 5, 4);
        Oilrig platform2 = new Oilrig(2, 520, 4, 4);
        Oilrig platform3 = new Oilrig(3, 360, 3, 4);
        Oilrig platform4 = new Oilrig(4, 120, 2, 2);
        oilrigs.add(platform1);
        oilrigs.add(platform2);
        oilrigs.add(platform3);
        oilrigs.add(platform4);

        //Methods.startupHeader();
        //InputOutput.InputHandler(oilrigs);


        for(int i = 140; i <= 350; i+=10){
            System.out.println("i is currently: " + i);
            int[] result = Methods.getdistribution(i, 1, 2);
            System.out.println("Big ships needed " + result[0] + "\n" + "Small ships needed " + result[1]);
        }


    }
}