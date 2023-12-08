package assets;

import programm.Methods;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

/**
 * Klasse Oilrig
 *
 * @autor Matthias Bergs, Jonas Hülse, Louis Schadewaldt
 */
public class Oilrig{

    private final int id;
    private ArrayList<Worker> workersOnOilrig = new ArrayList<>();
    private ArrayList<ShipSmall> smallShipsOnOilrig = new ArrayList<>();
    private ArrayList<ShipBig> bigShipsOnOilrig = new ArrayList<>();
    public final int initialCrewOilrig;
    public final int initialBigShips;
    public final int initialSmallShips;
    private boolean evacuated = false;

    /**
     * Kopierkonstruktor, der eine Kopie einer Ölplattform erstellt.
     *
     * @param or zu kopierendes Ölplattform-Objekt
     */
    public Oilrig(Oilrig or){
        this.id = or.id;
        this.initialCrewOilrig = or.initialCrewOilrig;
        this.initialBigShips =  or.initialBigShips;
        this.initialSmallShips = or.initialSmallShips;
        this.workersOnOilrig = new ArrayList<>(or.workersOnOilrig);
        this.smallShipsOnOilrig = new ArrayList<>(or.smallShipsOnOilrig);
        this.bigShipsOnOilrig = new ArrayList<>(or.bigShipsOnOilrig);
        this.evacuated = or.evacuated;
    }

    /**
     * Erstellt eine neue Ölplattform mit den angegebenen Parametern.
     *
     * @param id                Die ID der Ölplattform
     * @param initialCrew       Die Anfangsbesatzung der Ölplattform
     * @param initialBigShips   Die Anzahl der anfänglichen großen Schiffe auf der Ölplattform
     * @param initialSmallShips Die Anzahl der anfänglichen kleinen Schiffe auf der Ölplattform
     * @see Methods#addCounterShips()
     * @see Methods#getCounterShips()
     * @see Methods#addCounterWorker()
     * @see Methods#getCounterWorker()
     */
    public Oilrig(int id, int initialCrew, int initialBigShips, int initialSmallShips) {
        this.id = id;
        this.initialCrewOilrig = initialCrew;
        this.initialBigShips = initialBigShips;
        this.initialSmallShips = initialSmallShips;

        //Add Workers to Oilrig
        // @see Methods.getCounterShips @autor Jonas
        // Debug System.out.println(String.valueOf(Methods.getCounterWorker()) + " < " + String.valueOf(initialCrew + Methods.getCounterWorker()));

        int initMax = initialCrew + Methods.getCounterWorker();

        for (int i = Methods.getCounterWorker(); i < initMax; i++) {
            this.workersOnOilrig.add(new Worker(i));
            Methods.addCounterWorker();
        }

        initMax = initialBigShips + Methods.getCounterShips();
        //Add BigShips to Oilrig
        // @see Methods.getCounterShips
        for (int i = Methods.getCounterShips(); i < initMax; i++) {
            this.bigShipsOnOilrig.add(new ShipBig());
            Methods.addCounterShips();
        }

        initMax = initialSmallShips + Methods.getCounterShips();
        //Add SmallShips to Oilrig
        for (int i = Methods.getCounterShips(); i < initMax; i++) {
            this.smallShipsOnOilrig.add(new ShipSmall());
            Methods.addCounterShips();
        }
    }


    // -- Get-Methoden --

    /**
     * Getter-Methode für die ID der Ölplattform.
     *
     * @return Integer ID der Ölplattform
     */
    public int getId() {
        return id;
    }

    /**
     * Getter-Methode für die Anzahl der an der Ölplattform stationierten Arbeiter.
     *
     * @return Integer Anzahl der an der Ölplattform stationierten Arbeiter
     */
    public int getWorkerAmount(){
        return this.workersOnOilrig.size();
    }

    /**
     * Gibt ein kleines Schiff-Objekt anhand der ID zurück.
     *
     * @see  Oilrig#getShipById(int)
     * @see Oilrig#getBigShipById(int)
     * @param id Integer ID des Schiffes, dass zurückgegeben wird
     * @return kleines Schiff-Objekt der entsprechenden ID
     */
    public ShipSmall getSmallShipById(int id){
        for (ShipSmall ship : smallShipsOnOilrig){
            if (ship.getId() == id) return ship;
        }
        return null;
    }

    /**
     * Gibt ein großes Schiff-Objekt anhand der ID zurück.
     * @see  Oilrig#getShipById(int)
     * @see Oilrig#getSmallShipById(int)
     * @param id Integer ID des Schiffes, dass zurückgegeben wird
     * @return großes Schiff-Objekt der entsprechenden ID
     */
    public ShipBig getBigShipById(int id){
        for (ShipBig ship : bigShipsOnOilrig) {
            if (ship.getId() == id) return ship;
        }
        return null;
    }

    /**
     * Gibt ein Schiff-Objekt anhand der ID zurück. Vereint getSmallShipById(int id) und getBigShipById(int id).
     * @see  Oilrig#getBigShipById(int)
     * @see Oilrig#getSmallShipById(int)
     * @param id ID des Schiffes, dass zurückgegeben wird
     * @return Schiff-Objekt der entsprechenden ID
     */
    protected Ship getShipById(int id){
        for (Ship ship : bigShipsOnOilrig) {
            if (ship.getId() == id) return ship;
        }
        for (Ship ship : smallShipsOnOilrig) {
            if (ship.getId() == id) return ship;
        }
        return null;
    }


    // -- Check-Methoden Ölplattform und angedockte Schiffe --

    /**
     * Überprüft, ob mindestens ein Schiff an der Ölplattform angedockt ist und somit die Bedingung für
     * die Mindestanzahl an Schiffen an einer Plattform erfüllt.
     *
     * @see Methods#moveWorkers(String, String, String, String, boolean)
     * @return true, wenn mindestens ein Schiff an der Ölplattform angedockt ist
     */
    public boolean checkTotalShipCountBiggerOne(){
        int i = bigShipsOnOilrig.size() + smallShipsOnOilrig.size();
        return i >= 1;
    }

    /**
     * Überprüft, ob die Ölplattform noch ein kleines Schiff andocken lassen könnte, ohne dabei die maximale
     * Anzahl an angedockten kleinen Schiffen an der Ölplattform (Initialwert + 4) zu überschreiten.
     *
     * @see Oilrig#checkOilrigCanReceiveBigShip()
     * @return true, wenn die Anzahl der angedockten kleinen Schiffe plus eins kleiner oder gleich der initialen Anzahl
     * an angedockten kleinen Schiffen der Ölplattform plus vier ist
     */
    public boolean checkOilrigCanReceiveSmallShip(){
        return this.smallShipsOnOilrig.size() + 1 <= this.initialSmallShips + 4;
    }

    /**
     * Überprüft, ob die Ölplattform noch ein großes Schiff andocken lassen könnte und dabei nicht die maximale
     * Anzahl an angedockten großen Schiffen an der Ölplattform (Initialwert + 4) überschreitet.
     *
     * @see Oilrig#checkOilrigCanReceiveSmallShip()
     * @return true, wenn die Anzahl der angedockten großen Schiffe plus eins kleiner oder gleich der initialen Anzahl
     * an angedockten großen Schiffen der Ölplattform plus vier ist
     */
    public boolean checkOilrigCanReceiveBigShip(){
        return this.bigShipsOnOilrig.size() + 1 <= this.initialBigShips + 4;
    }


    // -- Hilfsmethoden zum Bewegen eines Schiffs --

    /**
     * Transferiert eine Menge an Arbeitern einer Ölplattform auf ein kleines, angedocktes Schiff.
     *
     * @param amount Anzahl der zu transferierenden Arbeiter
     * @param ship kleines Schiff, auf dass die Arbeiter transferiert werden
     * @see Oilrig#transferWorkerOilrigToShip(int, ShipBig)
     * @see ShipSmall#receiveWorker(Worker)
     */
    public void transferWorkerOilrigToShip(int amount, ShipSmall ship) {
        for (int i = 1; i <= amount; i++){
            Worker tempWorker = workersOnOilrig.get(0); // i oder 0, wenn das Objekt gelöscht wird ändert sich die Liste
            workersOnOilrig.remove(0);
            ship.receiveWorker(tempWorker);
        }
    }

    /**
     * Transferiert eine Menge an Arbeitern einer Ölplattform auf ein großes, angedocktes Schiff.
     *
     * @param amount Anzahl der zu transferierenden Arbeiter
     * @param ship großes Schiff, auf dass die Arbeiter transferiert werden
     * @see Oilrig#transferWorkerOilrigToShip(int, ShipSmall)
     * @see ShipBig#receiveWorker(Worker)
     */
    public void transferWorkerOilrigToShip(int amount, ShipBig ship) {
        for (int i = 1; i <= amount; i++){
            Worker tempWorker = workersOnOilrig.get(0); // i oder 0, wenn das Objekt gelöscht wird ändert sich die Liste
            workersOnOilrig.remove(0);
            ship.receiveWorker(tempWorker);
        }
    }

    /**
     * Transferiert alle Arbeiter auf einem kleinen Schiff auf eine Ölplattform.
     *
     * @param ship kleines Schiff, von dem alle Arbeiter auf eine Ölplattform transferiert werden
     * @see Oilrig#transferWorkerOilrigToShip(int, ShipBig)
     * @see ShipSmall#departureAll()
     */
    public void transferAllWorkerShipToOilrig(ShipSmall ship){
        ArrayList<Worker> wTransfer = ship.departureAll();
        workersOnOilrig.addAll(wTransfer);
    }

    /**
     * Transferiert alle Arbeiter auf einem großen Schiff auf eine Ölplattform.
     *
     * @param ship großes Schiff, von dem alle Arbeiter auf eine Ölplattform transferiert werden
     * @see Oilrig#transferWorkerOilrigToShip(int, ShipSmall)
     * @see ShipBig#departureAll()
     */
    public void transferAllWorkerShipToOilrig(ShipBig ship){
        ArrayList<Worker> wTransfer = ship.departureAll();
        workersOnOilrig.addAll(wTransfer);
    }

    /**
     * Dockt ein kleines Schiff von einer Ölplattform ab.
     *
     * @param ship kleines Schiff, dass von einer Ölplattform abgedockt wird
     * @see Oilrig#undockShip(ShipBig)
     */
    public void undockShip(ShipSmall ship){
        int id = ship.getId();
        for (int i = 0; i < smallShipsOnOilrig.size(); i++) {
            if (smallShipsOnOilrig.get(i).getId() == id) {
                try{
                    smallShipsOnOilrig.remove(i);
                }catch (IndexOutOfBoundsException oobe){
                    System.out.println("an error occurred: " + oobe.getMessage());
                }
            }
        }
    }

    /**
     * Dockt ein großes Schiff von einer Ölplattform ab.
     *
     * @param ship großes Schiff, dass von einer Ölplattform abgedockt wird
     * @see Oilrig#undockShip(ShipSmall)
     */
    public void undockShip(ShipBig ship){
        int id = ship.getId();
        for (int i = 0; i < bigShipsOnOilrig.size(); i++) {
            if (bigShipsOnOilrig.get(i).getId() == id) {
                try{
                    bigShipsOnOilrig.remove(i);
                }catch (IndexOutOfBoundsException oobe){
                    System.out.println("an error occurred: " + oobe.getMessage());
                }
            }
        }
    }

    /**
     * Dockt ein kleines Schiff an einer Ölplattform an.
     *
     * @param ship kleines Schiff, dass an einer Ölplattform angedockt wird
     * @see Oilrig#dockShip(ShipBig)
     */
    public void dockShip(ShipSmall ship){
        smallShipsOnOilrig.add(ship);
    }

    /**
     * Dockt ein großes Schiff an einer Ölplattform an.
     *
     * @param ship großes Schiff, dass an einer Ölplattform angedockt wird
     * @see Oilrig#dockShip(ShipSmall)
     */
    public void dockShip(ShipBig ship){
        bigShipsOnOilrig.add(ship);
    }

    /**
     * Schreibt die Überblick-Informationen einer Ölplattform in einen String und gibt diesen zurück. Über den Aufruf
     * in einer For-Each-Schleife in handleInput(ArrayList) wird der Überblick für alle Plattformen ausgegeben. Der
     * String ist für das Userinterface passend formatiert. Überblick-Informationen sind die Menge an angedockten
     * Schiffen an der Ölplattform (auch mit Unterscheidung der Schiffsgröße) und Anzahl der stationierten Arbeiter.
     *
     * @author Louis Schadewaldt
     * @see Methods#handleInput(ArrayList)
     * @return String mit allen Überblick-Informationen einer Plattform
     */
    public String getInformationOverview() {

        String result = "";

        // Output String - Formatierung
        result += "Oilrig ID: " + id + "\n";
        result += "------------------------------\n";
        result += "amount of ships docked: " + ( bigShipsOnOilrig.size() + smallShipsOnOilrig.size() ) + "\n";  // int sumOfShips = getBigShipAmount() + getSmallShipAmount();   // Fehlerhaft, 3+4 ist nicht 6
        result += "          big ships   : " + bigShipsOnOilrig.size() + "\n";                                  //int sumOfShips = bigShipsOnPlatform.size() + smallShipsOnPlatform.size();
        result += "          small ships : " + smallShipsOnOilrig.size() + "\n";
        result += "amount of workers     : " + workersOnOilrig.size() + "\n";
        return result;
    }

    /**
     * Schreibt die genauen Informationen einer spezifischen Ölplattform in einen String und gibt diesen zurück. Die
     * spezifische Ölplattform wird über deren ID in der Methode handleInput(ArrayList) bestimmt. Der String ist für
     * das Userinterface passend formatiert. Die zurückgegebenen Genaue Informationen sind die IDs der an der
     * Ölplattform angedockten Schiffe (nach Größe aufsteigend sortiert) mit Unterscheidung der Schiffsgröße und Anzahl
     * der stationierten Arbeiter.
     *
     * @see ShipBig#compareTo(ShipBig)
     * @see ShipSmall#compareTo(ShipSmall)
     * @author Louis Schadewaldt
     * @see Methods#handleInput(ArrayList)
     * @return String mit allen Überblick-Informationen einer Plattform
     */
    public String getInformationOilrig() {

        String result = "";
        StringBuilder bigShipsOnOilrigString = new StringBuilder("|");
        StringBuilder smallShipsOnOilrigString = new StringBuilder("|");

        // duplizierte Liste wird nach IDs sortiert für bigShip
        ArrayList<ShipBig> tempArray1 = new ArrayList<>(bigShipsOnOilrig);
        Collections.sort(tempArray1);

        for (ShipBig shipBig : tempArray1) {
            // Integer.toString kann NullPointerException werfen
            try {
                // Holt ID des Objektes an der Stelle [i] aus Liste bigShipsOnPlatform und konvertiert zu string
                bigShipsOnOilrigString.append(shipBig.getId()).append("|");
            } catch (NullPointerException npe) {
                System.out.println("an error occured: " + npe.getMessage());
            }
        }

        // duplizierte Liste wird nach IDs sortiert für smallShip
        ArrayList<ShipSmall> tempArray2 = new ArrayList<>(smallShipsOnOilrig);
        Collections.sort(tempArray2);

        for(int i = 0; i < smallShipsOnOilrig.size(); i++) {
            // siehe oben
            try{
                smallShipsOnOilrigString.append(tempArray2.get(i).getId()).append("|");
            }catch(NullPointerException npe){
                System.out.println("an error occured: " + npe.getMessage());
            }
        }

        // Output String - Formatierung
        result += "Oilrig ID: " + id + "\n";
        result += "------------------------------\n";
        result += "big ships             : " + bigShipsOnOilrigString + "\n";
        result += "small ships           : " + smallShipsOnOilrigString + "\n";
        result += "amount of workers     : " + workersOnOilrig.size() + "\n";

        return result;
    }


    // -- Evakuierung Methoden --

    /**
     * Bestimmt, ob eine Ölplattform für eine Evakuierung weitere Schiffe von anderen Ölplattformen benötigt. Die Größe
     * und Menge der benötigten Schiffe wird je nach benötigter Kapazität bestimmt. Die benötigten Schiffe für eine
     * mögliche Evakuierung werden als EvacuationPlanerItem in einer Array-Liste gespeichert.
     *
     * @see Oilrig#calculatePlan(int, ArrayList, ArrayList, ArrayList)
     * @see Oilrig#getEvacuationPlanerInfo(ArrayList)
     * @see Oilrig#executePlan(ArrayList)
     * @see EvacuationPlanerItem
     * @author Jonas Hüllse
     */
    public void checkEvacuationSpace(){
        ArrayList<EvacuationPlanerItem> ep = new ArrayList<>();

        int spaceAvailable = (this.smallShipsOnOilrig.size() * 50) + (this.bigShipsOnOilrig.size() * 100);
        int spaceNeeded = getWorkerAmount();
        int difference = spaceAvailable - spaceNeeded;
        if (difference >= 0){
            //Keine weiteren Schiffe benötigt
            ArrayList<EvacuationPlanerItem> epSmallShips = new ArrayList<>();
            ArrayList<EvacuationPlanerItem> epBigShips = new ArrayList<>();

            calculatePlan(spaceNeeded, epSmallShips, epBigShips, ep);
        }
        else {
            //Weiter Schiffe anfordern
            ArrayList<Oilrig> otherOrs = Methods.getOtherOilrigs(getId());
            ArrayList<EvacuationPlanerItem> epSmallShips = new ArrayList<>();
            ArrayList<EvacuationPlanerItem> epBigShips = new ArrayList<>();

            while (difference < 0){
                if (difference < -50){
                    EvacuationPlanerItem tempForNotNull = callForBigShipEP(otherOrs, "bigship");
                    if (tempForNotNull != null) {
                        tempForNotNull.toCallID_S[1] = this.id;
                        epBigShips.add(tempForNotNull);
                        System.out.println("Helping ship => ID " + tempForNotNull.shipId);
                        difference += 100;
                    }
                    else {
                        System.out.println("an error occurred: something went wrong while calling for help");
                        return;
                    }
                    // Abbruchbedingung für kein Schiff vorhanden
                } else {
                    EvacuationPlanerItem tempForNotNull = callForBigShipEP(otherOrs, "smallship");
                    if (tempForNotNull != null) {
                        tempForNotNull.toCallID_S[1] = this.id;
                        epSmallShips.add(tempForNotNull);
                        System.out.println("Helping ship => ID " + tempForNotNull.shipId);
                        difference += 50;
                    }
                    else {
                        System.out.println("an error occurred: something went wrong while calling for help");
                        return;
                    }
                    // @Jonas Maybe/Maybenot Abbruchbedingung für kein Schiff vorhanden
                }
            }
            calculatePlan(spaceNeeded, epSmallShips, epBigShips, ep);
        }
    }

    /**
     *
     * @param spaceNeeded
     * @param epSmallShips
     * @param epBigShips
     * @param ep
     * @author Jonas Hülse
     */
    private void calculatePlan(int spaceNeeded, ArrayList<EvacuationPlanerItem> epSmallShips, ArrayList<EvacuationPlanerItem> epBigShips, ArrayList<EvacuationPlanerItem> ep) {
        ArrayList<Oilrig> otherOrs = Methods.getOtherOilrigs(getId());

        for (ShipSmall ship : smallShipsOnOilrig) {
            epSmallShips.add(new EvacuationPlanerItem(ship.getId(), "smallship"));
        }
        for (ShipBig ship : bigShipsOnOilrig) {
            epBigShips.add(new EvacuationPlanerItem(ship.getId(),  "bigship"));
        }

        int avaSmallShips = epSmallShips.size();
        int avaBigShips = epBigShips.size();
        System.out.println("Bigships: " + avaBigShips + ", Smallships: " + avaSmallShips);

        // Rechnung zur gleichmäßigen Verteilung der Arbeiter auf die Schiffe
        int totalEqualShips = (2 * avaBigShips) + avaSmallShips;        // ShipBig entspricht nach Kapazität zwei kleinen Schiffen
        double workerPerShip = (double) spaceNeeded / totalEqualShips;  // runterbrechen: wie viele Arbeiter pro Schiff (Dezimalzahl)
        int evenWorkerPerShip = (int) Math.floor(workerPerShip);        // Dezimalzahl abrunden (Zahl wird ungenau)
        int temp = evenWorkerPerShip * totalEqualShips;                 // Hochrechnung der ungenauen Zahl auf den benötigten Gesamtwert für Arbeiter
        int diffFormDouble = spaceNeeded - temp;                        // Differenz zwischen eigentlich benötigtem Platz und der Hochrechnung der ungenauen Zahl (temp) --- überschüssige Arbeiter

        for (EvacuationPlanerItem epBigShip : epBigShips) {
            int localEvenWorkerPerShip;

            if (diffFormDouble > 0) {
                localEvenWorkerPerShip = (2 * evenWorkerPerShip) + 1;
                diffFormDouble--;
            } else localEvenWorkerPerShip = (2 * evenWorkerPerShip);

            for (Oilrig otherOr : otherOrs) {
                int freeSpace = otherOr.getFreeCapacity();
                // Wenn voll zum Nächsten
                if (freeSpace <= localEvenWorkerPerShip) {
                    continue;
                }
                if (!otherOr.checkOilrigCanReceiveBigShip()) {
                    continue;
                }

                if ((freeSpace - (2 * evenWorkerPerShip) >= 0 && epBigShip != null)) {
                    otherOr.workersOnOilrig.addAll(addEmptyWorkers(localEvenWorkerPerShip));
                    otherOr.bigShipsOnOilrig.add(new ShipBig(1));
                    epBigShip.destinationOr = otherOr.getId();
                    epBigShip.usedCrew = localEvenWorkerPerShip;
                    ep.add(epBigShip);
                    break;
                }
            }
        }
        for (EvacuationPlanerItem epSmallShip : epSmallShips) {
            int localEvenWorkerPerShip;

            if (diffFormDouble > 0) {
                localEvenWorkerPerShip = evenWorkerPerShip + 1;
                diffFormDouble--;
            } else localEvenWorkerPerShip = evenWorkerPerShip;

            for (Oilrig otherOr : otherOrs) {
                int freeSpace = otherOr.getFreeCapacity();
                // Wenn voll zum Nächsten
                if (freeSpace <= localEvenWorkerPerShip) {
                    continue;
                }
                if (!otherOr.checkOilrigCanReceiveSmallShip()) {
                    continue;
                }

                if ((freeSpace - evenWorkerPerShip) >= 0 && epSmallShip != null) {
                    otherOr.workersOnOilrig.addAll(addEmptyWorkers(localEvenWorkerPerShip));
                    otherOr.smallShipsOnOilrig.add(new ShipSmall(1));
                    epSmallShip.destinationOr = otherOr.getId();
                    epSmallShip.usedCrew = localEvenWorkerPerShip;
                    ep.add(epSmallShip);
                    break;
                }
            }
        }
        System.out.println(getEvacuationPlanerInfo(ep));

        Scanner yesOrNo = new Scanner(System.in);
        String input;
        boolean repeat = true;
        System.out.println("If you want to execute the suggested plan, please type 'y'. If you want to abort the plan, please type 'n'");
        while (repeat) {
            input = yesOrNo.nextLine();
            String[] arguments = input.split(" ");

            switch (arguments[0]) {
                case "y", "Yes", "Y", "yes":
                    executePlan(ep);
                    repeat = false;
                    break;
                case "n", "N", "no", "No":
                    repeat = false;
                    break;
                default:
                    System.out.println("Please type 'y' if you want to execute the suggested plan, or 'n' to abort it");
                    break;
            }
        }
    }

    /**
     *
     * @param ep
     * @author Jonas Hülse
     */
    private void executePlan(ArrayList<EvacuationPlanerItem> ep){
        for (EvacuationPlanerItem epItem : ep) {
            if (epItem.toCall){
                Methods.moveWorkers(String.valueOf(epItem.shipId), String.valueOf(0), String.valueOf(epItem.toCallID_S[0]),
                        String.valueOf(epItem.toCallID_S[1]),  true);
            }
            Methods.moveWorkers(String.valueOf(epItem.shipId), String.valueOf(epItem.usedCrew),
                    String.valueOf(Ship.getShipOriginID(epItem.shipId)), String.valueOf(epItem.destinationOr), true);
        }
        System.out.println("Evacuation successful...");
    }


    /**
     *
     *
     * @param ep
     * @return
     * @author Jonas Hülse, Louis Schadewaldt
     */
     private String getEvacuationPlanerInfo(ArrayList<EvacuationPlanerItem> ep){
        ArrayList<Oilrig> allOilrigs = Methods.getAllOilrigs();

        StringBuilder result = new StringBuilder("---------------------------------- Evacuation Plan ---------------------------------- \n");
        // unfertig
        result.append("evacuating " + this.workersOnOilrig.size() + " Workers from Oilrig [").append(this.getId()).append("]\n");
        for (EvacuationPlanerItem epItem : ep) {

            int maxCapacity = 0;

            // setzt maxCapacity in Abhängigkeit ob epItem.shipId ein großes oder kleines Schiff ist
            for (Oilrig temp : allOilrigs) {
                Ship ship = temp.getShipById(epItem.shipId);
                if (ship != null){
                    if(ship instanceof ShipBig){
                        maxCapacity = 100;
                    } else if (ship instanceof ShipSmall) {
                        maxCapacity = 50;
                    } else{
                        System.out.println("an error occurred: no ship found");
                    }
                }
            }

            // sucht, wo das Schiff ursprünglich angedockt ist
            String shipOriginID = String.valueOf(Ship.getShipOriginID(epItem.shipId));

            result.append("Ship: [").append(epItem.shipId).append("] from Oilrig [").append(shipOriginID).append("]").append("\tCrew: [").append(epItem.usedCrew).append("/").append(maxCapacity).append("]").append("\t\t→→→    \t\t").append("destinated Oilrig: [").append(epItem.destinationOr).append("]").append("\n");
        }
        return result.toString();
    }

    private static ArrayList<Worker> addEmptyWorkers(int i){
        ArrayList<Worker> temp = new ArrayList<>();
        for (int j = 1; j <= i; j++) {
            temp.add(new Worker(j * (-1)));
        }
        return temp;
    }

    private int getFreeCapacity(){
        int maxCapacity = 2 * initialCrewOilrig;
        int usedCapacity = workersOnOilrig.size();
        return maxCapacity - usedCapacity;
    }

    private static EvacuationPlanerItem callForBigShipEP(ArrayList<Oilrig> otherOr, String type){
        if (Objects.equals(type, "bigship")){
            for (Oilrig oilrig : otherOr) {
                if (!oilrig.bigShipsOnOilrig.isEmpty()) {
                    ShipBig ship = oilrig.bigShipsOnOilrig.get(0);
                    EvacuationPlanerItem temp = new EvacuationPlanerItem(ship.getId(), type);
                    temp.toCallID_S[0] = oilrig.getId();
                    temp.toCall = true;
                    return temp;
                }
            }
        } else {
            for (Oilrig oilrig : otherOr) {
                if (!oilrig.smallShipsOnOilrig.isEmpty()) {
                    ShipSmall ship = oilrig.smallShipsOnOilrig.get(0);
                    EvacuationPlanerItem temp = new EvacuationPlanerItem(ship.getId(), type);
                    temp.toCallID_S[0] = oilrig.getId();
                    temp.toCall = true;
                    return temp;
                }
            }
        }
        return null;
    }
}
