package Programm;

import assets.Oilrig;
import java.util.ArrayList;
import static Programm.Methods.startupHeader;

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

        startupHeader();
        Methods.InputHandler(oilrigs);
    }
}
//TO DO:
//receiveWorker() Methode doppelt aber anders und einmal ungenutzt!!!
//Benennungsregeln und Sprache überprüfen!!!
//Try-Catch einfügen (z.b.: input: 'move ruwiz wje')!!!
//Listen sortieren bei Ausgabe!!!
//alles unused löschen!!!
//evakuierungsplan und methode schreiben!!!
//167 und 147 in InputOutput assert für NullPointerException - keine Ahnung ob das richtig ist, hat nur IntelliJ vorgeschlagen!!!