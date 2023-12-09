package programm;

import assets.Oilrig;
import java.util.ArrayList;
import static programm.Manager.printStartupHeader;
import static programm.Manager.handleInput;

/**
 * Die Main-Klasse ist der Einstiegspunkt des Programms und initialisiert die Ölplattformen gemäß den vorgegebenen Parametern.
 * Sie ruft die Methode printStartupHeader() auf, um den Start des Programms anzuzeigen, und die Methode handleInput(), um mit dem Benutzer zu interagieren.
 *
 * @see Oilrig
 * @see Manager
 */
public class Main {

    /**
     * Main-Methode initialisiert die Ölplattformen nach vorgegebenen Parametern und ruft printStartupHeader() und handleInput() auf.
     *
     * @see Oilrig#Oilrig(Oilrig)
     * @see Manager#handleInput(ArrayList)
     * @param args Argumente
     */
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

        handleInput(oilrigs);
    }

    static {
        printStartupHeader();
    }
}

