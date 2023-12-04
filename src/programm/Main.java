package programm;

import assets.Oilrig;
import java.util.ArrayList;
import static programm.Methods.printStartupHeader;

public class Main {

    public static void main(String[] args) {
        ArrayList<Oilrig> oilrigs = new ArrayList<>();
        Oilrig platform1 = new Oilrig(1, 260, 5, 4);
        Oilrig platform2 = new Oilrig(2, 520, 4, 4);
        Oilrig platform3 = new Oilrig(3, 360, 3, 4);
        Oilrig platform4 = new Oilrig(4, 120, 2, 2);
        oilrigs.add(platform1);
        oilrigs.add(platform2);
        oilrigs.add(platform3);
        oilrigs.add(platform4);


        printStartupHeader();
        Methods.handleInput(oilrigs);


    }
}
//TO DO:
//Benennungsregeln und Sprache überprüfen!!!
//unused Stuff löschen!!!
//getShipById vereinfacht maybe manche anderen Prozesse mit getBigShipById und getSmallShipbyId --- Rekursion!!!
//welche nachricht wann prüfen!!!
//Listen sortieren bei Ausgabe!!!
//evakuierungsplan und methode schreiben!!!