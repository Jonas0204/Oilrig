package Programm;


import static Programm.InputOutput.startupHeader;

public class Main {

    // auf Englisch deutsch und grossklein achten

    //@author Matthias
    // Nichts hinzufügen alles Objektorientiert schreiben
    public static void main(String[] args) {
        startupHeader();
        InputOutput.initializePlatforms();
        InputOutput.InputHandler();
    }
}