/*
 * Soubor: Main.java
 * Ukol c. 2
 * Autor: Marek Ziska, xziska03@stud.fit.vutbr.cz
 * Skupina: 2BIB
 * Datum 17.05.2020
 */

package ija.projekt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Hlavná trieda aplikácie
 */
public class Main extends Application {

    /**
     * Ukladá mapu do json súbora
     * @param map mapa
     * @throws IOException
     */
    private void saveToJson(MyMap map) throws IOException {
        try (Writer writer = new FileWriter("/Users/marekziska/Desktop/IJA/UKOLY/IJA-PROJEKT/data/loadout.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(map, writer);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI.fxml"));
        BorderPane root = loader.load();
        /*to co je v okne */
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        MainController controller = loader.getController();

        //generateData();

        BufferedReader reader = new BufferedReader(new FileReader("/Users/marekziska/Desktop/IJA/UKOLY/IJA-PROJEKT/data/loadout.json"));
        Gson gson = new Gson();
        MyMap map = gson.fromJson(reader, MyMap.class);
        map.initTransientParts();

        List<Drawable> hehe = new ArrayList<>();
        hehe.addAll(map.getBuses());
        hehe.addAll(map.getStreets());

        controller.setBuses(map.getBuses());
        controller.setLines(map.getLines());
        controller.setElements(hehe);
        controller.setStreets(map.getStreets());
        controller.startTime();
    }

    /**
     * Generuje vytvorenú mapu, pomocou postupne incializovaných tried, posiela ju na uloženie do json súbora
     * @throws IOException
     */
    private void generateData() throws IOException {
        MyMap map = new MyMap();
        List<Drawable> elements = new ArrayList<>();

        MyStreet sharedStreet = new MyStreet("Trnavská", new Coordinate(400, 600), new Coordinate(270, 420));
        MyStreet sharedStreet2 = new MyStreet("Mestská", new Coordinate(270, 420), new Coordinate(330, 200));
        sharedStreet2.addStop(60, "Zdráhalova");
        MyStreet sharedStreet3 = new MyStreet("Vávrova", new Coordinate(68, 50), new Coordinate(30, 240));
        MyStreet sharedStreet4 = new MyStreet("Tahpea", new Coordinate(30, 240), new Coordinate(150, 360));

        MyStreet sharedStreet33 = new MyStreet("Tailorova", new Coordinate(150, 360), new Coordinate(80, 610));

        MyStreet sharedStreet5 = new MyStreet("Frizly", new Coordinate(80, 610), new Coordinate(220, 790));
        MyStreet sharedStreet6 = new MyStreet("Vivova", new Coordinate(220, 790), new Coordinate(400, 600));
        MyStreet sharedStreet7 = new MyStreet("Sergeiova", new Coordinate(330, 200), new Coordinate(68, 50));

        sharedStreet3.addStop(90, "Pepegova");
        sharedStreet33.addStop(100, "Žiškova");
        sharedStreet6.addStop(20, "Zochova");
        sharedStreet7.addStop(90, "Titania");

        //elements.add(new Bus( 10, testLine, 12));
        MyStreet pepegaStreet = new MyStreet("Ulica4", new Coordinate(400, 600), new Coordinate(600,  600));
        MyStreet pepegaStreet2 = new MyStreet("Ulica5",new Coordinate(600, 600), new Coordinate(700,  500));
        MyStreet pepegaStreet3 = new MyStreet("Ulica10", new Coordinate(700,  500), new Coordinate(640,  187));
        MyStreet pepegaStreet4 = new MyStreet("Ulica11", new Coordinate(640,  187), new Coordinate(490,  260));
        MyStreet pepegaStreet5 = new MyStreet("Ulica6", new Coordinate(490, 260), new Coordinate(330,  200));
        pepegaStreet.addStop(50, "Koniklecova");
        pepegaStreet2.addStop(90, "Chirana");
        pepegaStreet3.addStop(210, "Ferkova");

        MyStreet lulw1 = new MyStreet("Elgartova", new Coordinate(330,  200), new Coordinate(540,  70));
        MyStreet lulw2 = new MyStreet("Malinovského", new Coordinate(540,  70), new Coordinate(740,  110));
        MyStreet lulw3 = new MyStreet("Merhautova", new Coordinate(740,  110), new Coordinate(980,  280));
        MyStreet lulw4 = new MyStreet("Česká", new Coordinate(980,  280), new Coordinate(1000,  440));
        MyStreet lulw5 = new MyStreet("Konečného", new Coordinate(1000,  440), new Coordinate(880,  540));
        MyStreet lulw6 = new MyStreet("Hlavná", new Coordinate(880,  540), new Coordinate(780,  370));
        MyStreet lulw7 = new MyStreet("Stredná", new Coordinate(780,  370), new Coordinate(640,  187));

        lulw2.addStop(40, "Čachtická");
        lulw3.addStop(120, "Airova");
        lulw4.addStop(40, "Vedľajšia");
        lulw7.addStop(30, "Iota");


        map.addStreet(sharedStreet);
        map.addStreet(sharedStreet2);
        map.addStreet(sharedStreet3);
        map.addStreet(sharedStreet4);
        map.addStreet(sharedStreet33);
        map.addStreet(sharedStreet5);
        map.addStreet(sharedStreet6);
        map.addStreet(sharedStreet7);

        map.addStreet(lulw1);
        map.addStreet(lulw2);
        map.addStreet(lulw3);
        map.addStreet(lulw4);
        map.addStreet(lulw5);
        map.addStreet(lulw6);
        map.addStreet(lulw7);

        map.addStreet(pepegaStreet);
        map.addStreet(pepegaStreet2);
        map.addStreet(pepegaStreet3);
        map.addStreet(pepegaStreet4);
        map.addStreet(pepegaStreet5);

        map.addLine("53", 10,"Vávrova", "Tahpea", "Tailorova", "Frizly", "Vivova", "Trnavská", "Mestská", "Sergeiova");
        map.addLine("84",10,"Ulica4", "Ulica5", "Ulica10", "Ulica11", "Ulica6", "Mestská", "Trnavská");
        map.addLine("32",10,"Elgartova", "Malinovského", "Merhautova", "Česká", "Konečného", "Hlavná", "Stredná", "Ulica11", "Ulica6");

        this.saveToJson(map);
    }
}

