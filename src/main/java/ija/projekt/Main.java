package ija.projekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
        testLine.addStreet(new MyStreet("Pepega", new Coordinate(10, 10), new Coordinate(300, 300)));
        testLine.addStreet(new MyStreet("Omegalil", new Coordinate(300, 300), new Coordinate(200, 500), new Coordinate(400, 600)));
        testLine.addStreet(new MyStreet("Dayuum", new Coordinate(400, 600), new Coordinate(350, 200), new Coordinate(10, 10)));
        elements.add(new Bus( 15, testLine));
        for(int k = 0; k < testLine.getStreets().size(); k++){
            elements.add((Drawable) testLine.getStreets().get(k));
        }
        //elements.add();
        /*toto bude mozno nejako treba nacitavat zo subora*/
        controller.setElements(elements);
        controller.startTime();
    }
}
