package ija.projekt;

import javafx.fxml.FXML;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Time;


public class MainController {
    @FXML
    private Pane content;
    private Timer timer;
    private LocalTime time = LocalTime.now();
    private List<TimeUpdate> updates = new ArrayList<>();
    private List<Drawable> elements = new ArrayList<>();

    @FXML
    private void onZoom(ScrollEvent event){
        /*aby sa neprogpagoval do vyssich panov*/
        event.consume();
        System.out.println(event.getDeltaY());
        double zoom = event.getDeltaY() >= 0 ? 1.1 : 0.9;
        content.setScaleX(zoom * content.getScaleX());
        content.setScaleY(zoom * content.getScaleY());
        content.layout();
    }

    public void setElements(List<Drawable> elements){
        this.elements = elements;
        for(Drawable drawable : elements){
            content.getChildren().addAll(drawable.getGUI());
            if(drawable instanceof TimeUpdate){
                updates.add((TimeUpdate) drawable);
            }
        }
    }

    public void startTime(){
        timer = new Timer(false);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time = time.plusSeconds(1);
                for(TimeUpdate update: updates){
                    update.update(time);
                }
            }
        }, 0, 1000);
    }
}
