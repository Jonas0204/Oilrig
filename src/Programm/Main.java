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

        Methods.startupHeader();
        //InputOutput.InputHandler(oilrigs);

        for (int i = 10; i < 400; i+=10) {
            int[] temp = Methods.getdistribution(i, 2, 1 );
            System.out.println("I: " + i + "   Big " + temp[0] + "  Small " + temp[1]);
        }



    }
}