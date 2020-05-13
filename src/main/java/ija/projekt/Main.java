package ija.projekt;

import com.google.gson.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//TODO nacitanie zo subora

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI.fxml"));
        BorderPane root = loader.load();
        /*to co je v okne */
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        MainController controller = loader.getController();
        List<Drawable> elements = new ArrayList<>();

        MyLine testLine = new MyLine("Linkapepega");
        MyLine testLine2 = new MyLine("Cachticka strela");
        MyStreet sharedStreet = new MyStreet("Ulica8", new Coordinate(400, 600), new Coordinate(270, 420));
        MyStreet sharedStreet2 = new MyStreet("Ulica69", new Coordinate(270, 420), new Coordinate(330, 200));
        sharedStreet2.addStop(60, "Zdráhalova");
        testLine.addStreet(new MyStreet("Ulica1", new Coordinate(68, 50), new Coordinate(30, 240)));
        testLine.addStreet(new MyStreet("Ulica2", new Coordinate(30, 240), new Coordinate(200, 410)));
        testLine.addStreet(new MyStreet("Ulica13", new Coordinate(200, 410), new Coordinate(140, 570)));
        testLine.addStreet(new MyStreet("Ulica7", new Coordinate(140, 570), new Coordinate(400, 600)));
        testLine.addStreet(sharedStreet);
        testLine.addStreet(sharedStreet2);
        testLine.addStreet(new MyStreet("Ulica9", new Coordinate(330, 200), new Coordinate(68, 50)));

        //elements.add(new Bus( 10, testLine, 12));
        Bus bus2 = new Bus( 12, testLine, 12);
        elements.add(bus2);
        MyStreet pepegaStreet = new MyStreet("Ulica4", new Coordinate(400, 600), new Coordinate(600,  600));
        MyStreet pepegaStreet2 = new MyStreet("Ulica5",new Coordinate(600, 600), new Coordinate(700,  500));
        pepegaStreet.addStop(50, "Koniklecova");
        pepegaStreet2.addStop(90, "Chirana");
        testLine2.addStreet(pepegaStreet);
        testLine2.addStreet(pepegaStreet2);
        testLine2.addStreet(new MyStreet("Ulica10", new Coordinate(700,  500), new Coordinate(620,  287)));
        testLine2.addStreet(new MyStreet("Ulica11", new Coordinate(620,  287), new Coordinate(420,  200)));
        testLine2.addStreet(new MyStreet("Ulica6", new Coordinate(420, 200), new Coordinate(330,  200)));
        testLine2.addStreet(sharedStreet2);
        testLine2.addStreet(sharedStreet);
        Bus bus1 = new Bus( 25, testLine2, 12);
        elements.add(bus1);
        for(int k = 0; k < testLine.getStreets().size(); k++){
            elements.add((MyStreet) testLine.getStreets().get(k));
        }
        for(int k = 0; k < testLine2.getStreets().size(); k++){
            elements.add((MyStreet) testLine2.getStreets().get(k));
        }

        //try (Writer writer = new FileWriter("/Users/marekziska/Desktop/IJA/UKOLY/IJA-PROJEKT/src/main/resources/loadout.json")) {
        //    Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //    gson.toJson(map, writer);
        //}
        List<Bus> buslist = new ArrayList<>();
        buslist.add(bus1);
        buslist.add(bus2);
        //buslist.add(bus2);
        controller.setBuses(buslist);
        controller.setElements(elements);
        controller.startTime();
    }
}
