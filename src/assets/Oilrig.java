package assets;

import programm.Manager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

/**
 * Die Klasse Oilrig definiert ein Ölplattform-Objekt. Außerdem umfasst die Klasse alle grundlegfenden Methoden einer 
 * Ölplattform, die für die Verwaltung dieser notwendig sind und die Basis für das Evakuieren und Transportieren von 
 * Schiffen und Arbeitern bilden.
 *
 * @see Manager#moveWorkers(String, String, String, String, boolean)
 * @see Manager#evacuation(int) 
 * @author Matthias Bergs, Jonas Hülse, Louis Schadewaldt
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
     * @see Manager#addCounterShips()
     * @see Manager#getCounterShips()
     * @see Manager#addCounterWorker()
     * @see Manager#getCounterWorker()
     */
    public Oilrig(int id, int initialCrew, int initialBigShips, int initialSmallShips) {
        this.id = id;
        this.initialCrewOilrig = initialCrew;
        this.initialBigShips = initialBigShips;
        this.initialSmallShips = initialSmallShips;

        //Add Workers to Oilrig
        // @see Methods.getCounterShips @autor Jonas
        // Debug System.out.println(String.valueOf(Methods.getCounterWorker()) + " < " + String.valueOf(initialCrew + Methods.getCounterWorker()));

        int initMax = initialCrew + Manager.getCounterWorker();

        for (int i = Manager.getCounterWorker(); i < initMax; i++) {
            this.workersOnOilrig.add(new Worker(i));
            Manager.addCounterWorker();
        }

        initMax = initialBigShips + Manager.getCounterShips();
        //Add BigShips to Oilrig
        // @see Methods.getCounterShips
        for (int i = Manager.getCounterShips(); i < initMax; i++) {
            this.bigShipsOnOilrig.add(new ShipBig());
            Manager.addCounterShips();
        }

        initMax = initialSmallShips + Manager.getCounterShips();
        //Add SmallShips to Oilrig
        for (int i = Manager.getCounterShips(); i < initMax; i++) {
            this.smallShipsOnOilrig.add(new ShipSmall());
            Manager.addCounterShips();
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
     * @return Integer, Anzahl der an der Ölplattform stationierten Arbeiter
     */
    public int getWorkerAmount(){
        return this.workersOnOilrig.size();
    }

    /**
     * Gibt ein Schiff-Objekt anhand der ID zurück.

     * @param id ID des Schiffes, dass zurückgegeben wird
     * @return Schiff, der entsprechenden ID
     */
    public Ship getShipById(int id){
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
     * @see Manager#moveWorkers(String, String, String, String, boolean)
     * @return true, wenn mindestens ein Schiff an der Ölplattform angedockt ist
     */
    public boolean checkTotalShipCountBiggerOne(){
        int i = bigShipsOnOilrig.size() + smallShipsOnOilrig.size();
        return i - 1 >= 1;
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


    // -- Hilfsmethoden zum Bewegen eines Schiffes --

    /**
     * Transferiert eine angegebene Anzahl von Arbeitern von der Ölplattform zu einem Schiff.
     *
     * @param amount Anzahl der zu transferierenden Arbeiter
     * @param ship Schiff, zu dem die Arbeiter transferiert werden sollen
     */
    public void transferWorkerOilrigToShip(int amount, Ship ship) {
        for (int i = 1; i <= amount; i++){
            Worker tempWorker = workersOnOilrig.get(0); // i oder 0, wenn das Objekt gelöscht wird ändert sich die Liste
            workersOnOilrig.remove(0);
            ship.receiveWorker(tempWorker);
        }
    }

    /**
     * Verschiebt alle Arbeiter von einem Schiff zur Ölplattform.
     *
     * @param ship Schiff, von dem die Arbeiter zur Ölplattform transferiert werden sollen
     */
    public void transferAllWorkerShipToOilrig(Ship ship){
        ArrayList<Worker> wTransfer = ship.departureAll();
        workersOnOilrig.addAll(wTransfer);
    }

    /**
     * Entfernt das angegebene Schiff aus der Liste der angedockten Schiffe der Ölplattform.
     *
     * @see Manager#moveWorkers(String, String, String, String, boolean) 
     * @param ship Schiff, das von der Ölplattform abgedockt werden soll
     */
    public void undockShip(Ship ship){
        int id = ship.getId();

        // Je nach Schiffstyp wir die entsprechende Liste verwendet
        if(ship instanceof ShipBig){
            for (int i = 0; i < bigShipsOnOilrig.size(); i++) {
                if (bigShipsOnOilrig.get(i).getId() == id) {
                    try{
                        bigShipsOnOilrig.remove(i);
                    }catch (IndexOutOfBoundsException oobe){
                        System.out.println("an error occurred: " + oobe.getMessage());
                    }
                }
            }
        }else if(ship instanceof ShipSmall){
            for (int i = 0; i < smallShipsOnOilrig.size(); i++) {
                if (smallShipsOnOilrig.get(i).getId() == id) {
                    try{
                        smallShipsOnOilrig.remove(i);
                    }catch (IndexOutOfBoundsException oobe){
                        System.out.println("an error occurred: " + oobe.getMessage());
                    }
                }
            }
        }else{
            System.out.println("an error occurred: ship not found");
        }
    }

    /**
     * Dockt ein Schiff an diese Ölplattform an, indem es entsprechend seinem Typ der Plattform hinzugefügt wird.
     *
     * @param ship Schiff, das an eine Ölplattform angedockt werden soll
     */
    public void dockShip(Ship ship){
        if(ship instanceof ShipBig){
            // Falls es sich um ein großes Schiff handelt, wird es der Liste der großen Schiffe auf dieser Ölplattform hinzugefügt
            bigShipsOnOilrig.add((ShipBig)ship);
        }else if (ship instanceof ShipSmall){
            // Falls es sich um ein kleines Schiff handelt, wird es der Liste der kleinen Schiffe auf dieser Ölplattform hinzugefügt
            smallShipsOnOilrig.add((ShipSmall) ship);
        }else{
            // Wenn der Schiffstyp nicht erkannt wird, wird eine Fehlermeldung ausgegeben
            System.out.println("an error occurred: ship not found");
        }
    }


    /**
     * Schreibt die Überblick-Informationen einer Ölplattform in einen String und gibt diesen zurück. Über den Aufruf
     * in einer For-Each-Schleife in handleInput(ArrayList) wird der Überblick für alle Plattformen ausgegeben. Der
     * String ist für das Userinterface passend formatiert. Überblick-Informationen sind die Menge an angedockten
     * Schiffen an der Ölplattform (auch mit Unterscheidung der Schiffsgröße) und Anzahl der stationierten Arbeiter.
     *
     * @author Louis Schadewaldt
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
     * das Userinterface passend formatiert. Die zurückgegebenen genauen Informationen sind die IDs der an der
     * Ölplattform angedockten Schiffe (nach Größe aufsteigend sortiert) mit Unterscheidung der Schiffsgröße und Anzahl
     * der stationierten Arbeiter.
     *
     * @see Ship#compareTo(Ship)
     * @author Louis Schadewaldt
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
     * @see Oilrig#callForShipEP(ArrayList, String)
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
            ArrayList<Oilrig> otherOrs = Manager.getOtherOilrigs(getId());
            ArrayList<EvacuationPlanerItem> epSmallShips = new ArrayList<>();
            ArrayList<EvacuationPlanerItem> epBigShips = new ArrayList<>();

            while (difference < 0){
                if (difference < -50){
                    EvacuationPlanerItem tempForNotNull = callForShipEP(otherOrs, "bigship");
                    if (tempForNotNull != null) {
                        tempForNotNull.toCallID_S[1] = this.id;
                        epBigShips.add(tempForNotNull);
                        //System.out.println("Helping ship => ID " + tempForNotNull.shipId);
                        difference += 100;
                    }
                    else {
                        System.out.println("an error occurred: something went wrong while calling for help");
                        return;
                    }
                    // Abbruchbedingung für kein Schiff vorhanden
                } else {
                    EvacuationPlanerItem tempForNotNull = callForShipEP(otherOrs, "smallship");
                    if (tempForNotNull != null) {
                        tempForNotNull.toCallID_S[1] = this.id;
                        epSmallShips.add(tempForNotNull);
                        //System.out.println("Helping ship => ID " + tempForNotNull.shipId);
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
     * Berechnet einen Evakuierungsplan für Schiffe und Arbeiter auf dieser Ölplattform basierend auf dem benötigten Platz.
     * Der Plan wird unter Berücksichtigung der verfügbaren Schiffe und der Arbeiter der Zielplattformen erstellt.
     *
     * Die Methode berücksichtigt die vorhandenen Schiffe und die benötigte Kapazität für die Evakuierung. Sie erstellt einen Plan,
     * der die Arbeiter gleichmäßig auf die Schiffe verteilt und sicherstellt, dass die Arbeiterkapazität jeder Zielplattform
     * nicht überschritten wird. Der Plan wird in Form von Evakuierungsplan-Elementen dargestellt, die die benötigten Informationen für
     * den Transfer von Arbeitern und Schiffen enthalten.
     *
     * @param spaceNeeded für die Evakuierung benötigte Kapazität
     * @param epSmallShips Liste von Evakuierungsplan-Elementen für kleine Schiffe, die eventuell vorher angefordert wurden. Wenn keine kleinen Schiffe angefordert wurden, ist diese Liste leer.
     * @param epBigShips Liste von Evakuierungsplan-Elementen für große Schiffe, die eventuell vorher angefordert wurden. Wenn keine großen Schiffe angefordert wurden, ist diese Liste leer.
     * @param ep Liste von Evakuierungsplan-Elementen, also der letztendliche Evakuierungsplan
     * @see Manager#getOtherOilrigs(int)
     * @see EvacuationPlanerItem
     * @see Oilrig#getFreeCapacity()
     * @see Oilrig#addEmptyWorkers(int)
     * @see Oilrig#getEvacuationPlanerInfo(ArrayList)
     * @see Oilrig#executePlan(ArrayList)
     * @author Jonas Hülse
     */
    private void calculatePlan(int spaceNeeded, ArrayList<EvacuationPlanerItem> epSmallShips, ArrayList<EvacuationPlanerItem> epBigShips, ArrayList<EvacuationPlanerItem> ep) {
        // Erhalten der anderen Ölplattformen außer dieser
        ArrayList<Oilrig> otherOrs = Manager.getOtherOilrigs(getId());

        // Fügt Evakuierungsplan-Elemente für die zu evakuierende Plattform hinzu, also die eigenen Schiffe
        for (ShipSmall ship : smallShipsOnOilrig) {
            epSmallShips.add(new EvacuationPlanerItem(ship.getId(), "smallship"));
        }
        for (ShipBig ship : bigShipsOnOilrig) {
            epBigShips.add(new EvacuationPlanerItem(ship.getId(),  "bigship"));
        }

        int avaSmallShips = epSmallShips.size();
        int avaBigShips = epBigShips.size();
        //System.out.println("Bigships: " + avaBigShips + ", Smallships: " + avaSmallShips);

        // Logik zur gleichmäßigen Verteilung der Arbeiter auf die Schiffe und Zielplattformen...
        int totalEqualShips = (2 * avaBigShips) + avaSmallShips;        // ShipBig entspricht nach der Kapazität zwei kleinen Schiffen
        double workerPerShip = (double) spaceNeeded / totalEqualShips;  // runterbrechen: wie viele Arbeiter pro Schiff (Dezimalzahl)
        int evenWorkerPerShip = (int) Math.floor(workerPerShip);        // Dezimalzahl abrunden (Zahl wird ungenau)
        int temp = evenWorkerPerShip * totalEqualShips;                 // Hochrechnung der ungenauen Zahl auf den benötigten Gesamtwert für Arbeiter
        int diffFormDouble = spaceNeeded - temp;                        // Differenz zwischen eigentlich benötigtem Platz und der Hochrechnung der ungenauen Zahl (temp) --- überschüssige Arbeiter

        for (EvacuationPlanerItem epBigShip : epBigShips) {
            int localEvenWorkerPerShip;

            // Zu jedem Schiff wird, falls noch vorhanden, noch ein Überschüssiger Arbeiter zugewiesen
            if (diffFormDouble > 0) {
                localEvenWorkerPerShip = (2 * evenWorkerPerShip) + 1;
                diffFormDouble--;
            } else localEvenWorkerPerShip = (2 * evenWorkerPerShip);

            for (Oilrig otherOr : otherOrs) {
                int freeSpace = otherOr.getFreeCapacity();
                // Wenn die Plattform voll ist, zum Nächsten
                if (freeSpace <= localEvenWorkerPerShip) {
                    continue;
                }
                if (!otherOr.checkOilrigCanReceiveBigShip()) {
                    continue;
                }

                // Es werden der Ölplattformkopien leere Objekte hinzugefügt um eine Befüllung zu simulieren
                if ((freeSpace - (2 * evenWorkerPerShip) >= 0 && epBigShip != null)) {
                    // Hinzufügen von leeren Arbeitern zur Zielplattform
                    otherOr.workersOnOilrig.addAll(addEmptyWorkers(localEvenWorkerPerShip));
                    // Hinzufügen eines leeren großen Schiffes zur Zielplattform
                    otherOr.bigShipsOnOilrig.add(new ShipBig(1));
                    // Aktualisierung der Zielplattform im Evakuierungsplan
                    epBigShip.destinationOr = otherOr.getId();
                    // Aktualisierung der verwendeten Arbeitskräfte im Evakuierungsplan
                    epBigShip.usedCrew = localEvenWorkerPerShip;
                    ep.add(epBigShip);
                    break;
                }
            }
        }
        for (EvacuationPlanerItem epSmallShip : epSmallShips) {
            int localEvenWorkerPerShip;

            // Zu jedem Schiff wird, falls noch vorhanden, noch ein Überschüssiger Arbeiter zugewiesen
            if (diffFormDouble > 0) {
                localEvenWorkerPerShip = evenWorkerPerShip + 1;
                diffFormDouble--;
            } else localEvenWorkerPerShip = evenWorkerPerShip;

            for (Oilrig otherOr : otherOrs) {
                int freeSpace = otherOr.getFreeCapacity();
                // Wenn die Plattform voll ist, zum Nächsten
                if (freeSpace <= localEvenWorkerPerShip) {
                    continue;
                }
                if (!otherOr.checkOilrigCanReceiveSmallShip()) {
                    continue;
                }

                // Es werden der Ölplattformkopien leere Objekte hinzugefügt um eine Befüllung zu simulieren
                if ((freeSpace - evenWorkerPerShip) >= 0 && epSmallShip != null) {
                    // Hinzufügen von leeren Arbeitern zur Zielplattform
                    otherOr.workersOnOilrig.addAll(addEmptyWorkers(localEvenWorkerPerShip));
                    // Hinzufügen eines leeren kleinen Schiffes zur Zielplattform
                    otherOr.smallShipsOnOilrig.add(new ShipSmall(1));
                    // Aktualisierung der Zielplattform im Evakuierungsplan
                    epSmallShip.destinationOr = otherOr.getId();
                    // Aktualisierung der verwendeten Arbeitskräfte im Evakuierungsplan
                    epSmallShip.usedCrew = localEvenWorkerPerShip;
                    ep.add(epSmallShip);
                    break;
                }
            }
        }
        System.out.println(getEvacuationPlanerInfo(ep));

        // Abfrage, ob der vorgeschlagene Evakuierungsplan ausgeführt werden soll
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
     * Führt den Evakuierungsplan aus, der auf der berechneten Evakuierungsplanliste basiert.
     * Geht jeden Eintrag in der Liste durch und bewegt Arbeiter und Schiffe entsprechend dem Evakuierungsplan.
     *
     * @param ep Liste von Evakuierungsplan-Elementen, die die Aktionen für die Evakuierung darstellen
     * @see Oilrig#calculatePlan(int, ArrayList, ArrayList, ArrayList)
     * @see Manager#moveWorkers(String, String, String, String, boolean)
     * @autor Jonas Hülse
     */
    private void executePlan(ArrayList<EvacuationPlanerItem> ep){
        for (EvacuationPlanerItem epItem : ep) {
            if (epItem.toCall){
                Manager.moveWorkers(String.valueOf(epItem.shipId), String.valueOf(0), String.valueOf(epItem.toCallID_S[0]),
                        String.valueOf(epItem.toCallID_S[1]),  true);
            }
            Manager.moveWorkers(String.valueOf(epItem.shipId), String.valueOf(epItem.usedCrew),
                    String.valueOf(Ship.getShipOriginID(epItem.shipId)), String.valueOf(epItem.destinationOr), true);
        }
        System.out.println("Evacuation successful...");
    }

    /**
     * Gibt eine formatierte Übersicht des Evakuierungsplans, anhand des Parameters [ep], aus.
     * Geht die Liste von Evakuierungsplan-Elementen durch und generiert Informationen zu Schiffen, ihrer aktuellen Crew und ihren Ziel- und Ursprungsplattformen.
     *
     * @param ep Liste von Evakuierungsplan-Elementen, die die geplanten Evakuierungsschritte darstellen
     * @return String, der eine Übersicht des Evakuierungsplans enthält
     * @author Jonas Hülse, Louis Schadewaldt
     */
     private String getEvacuationPlanerInfo(ArrayList<EvacuationPlanerItem> ep){
        ArrayList<Oilrig> allOilrigs = Manager.getAllOilrigs();

        StringBuilder result = new StringBuilder("---------------------------------- Evacuation Plan ---------------------------------- \n");
        // unfertig
        result.append("evacuating " + this.workersOnOilrig.size() + " Workers from Oilrig [").append(this.getId()).append("]\n");
        for (EvacuationPlanerItem epItem : ep) {

            int maxCapacity = 0;

            // Ermitteln der maximalen Kapazität des Schiffes (abhängig von großem oder kleinem Schiff)
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

            result.append("Ship: [").append(epItem.shipId).append("] from Oilrig [").append(shipOriginID).append("]").append("\tCrew: [").append(epItem.usedCrew).append("/").append(maxCapacity).append("]").append("\t\t>>>    \t\t").append("destinated Oilrig: [").append(epItem.destinationOr).append("]").append("\n");
        }
        return result.toString();
    }

    /**
     * Erstellt und liefert eine Liste von leeren Arbeitern basierend auf der angegebenen Anzahl.
     * Jeder leere Arbeiter erhält eine eindeutige negative ID.
     *
     * @param i Anzahl der leeren Arbeiter, die erstellt werden sollen
     * @return Liste von leeren Arbeitern
     */
    private static ArrayList<Worker> addEmptyWorkers(int i){
        ArrayList<Worker> temp = new ArrayList<>();
        for (int j = 1; j <= i; j++) {
            temp.add(new Worker(j * (-1)));
        }
        return temp;
    }

    /**
     * Berechnet und liefert die verfügbare Kapazität von Arbeitern auf der Ölplattform.
     * Die verfügbare Kapazität ist die Differenz zwischen der maximalen Kapazität (das doppelte der anfänglichen Besatzung) der Plattform und der aktuellen Anzahl von Arbeitern.
     *
     * @return Integer verfügbarer Kapazität auf der Ölplattform
     */
    private int getFreeCapacity(){
        int maxCapacity = 2 * initialCrewOilrig;
        int usedCapacity = workersOnOilrig.size();
        return maxCapacity - usedCapacity;
    }

    /**
     * Sucht nach einem verfügbaren Schiff eines bestimmten Typs auf anderen Ölplattformen und erstellt
     * ein EvacuationPlanerItem für den Ruf nach einem solchen Schiff, um eine Evakuierung vorzubereiten.
     *
     * @param otherOr Liste der anderen Ölplattformen
     * @param type Typ des gesuchten Schiffes ("bigship" oder "smallship")
     * @return EvacuationPlanerItem, das den Ruf nach einem passenden Schiff repräsentiert;
     *         null, wenn kein passendes Schiff gefunden wurde
     */
    private static EvacuationPlanerItem callForShipEP(ArrayList<Oilrig> otherOr, String type){
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
