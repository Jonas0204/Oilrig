package Programm;

import java.io.BufferedReader;
import java.io.FileReader;

public class Jsonparser {



    public String readJSONFile() {
        String output = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("json/vornamen_w.json"));
        } catch (Exception e) {
            System.err.println(e);
        }
        return output;
    }
}