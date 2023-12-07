package programm;

import assets.Oilrig;
import java.util.ArrayList;
import static programm.Methods.printStartupHeader;
import static programm.Methods.handleInput;

/**
 * Main Klasse ist die Anfangsklasse
 */
public class Main {

    /**
     * Main-Methode initialisiert die Ölplattformen nach vorgegebenen Parametern und ruft printStartupHeader() und handleInput() auf
     * @see Oilrig#Oilrig(Oilrig)
     * @see Methods#handleInput(ArrayList)
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

        printStartupHeader();
        handleInput(oilrigs);
    }
}

//TO DO:
// auskommentieren
// unused Stuff löschen!
// welche Nachricht wann prüfen!
// Testfälle
