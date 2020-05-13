package ija.projekt;

import com.google.gson.JsonIOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

import javax.xml.soap.Text;
import java.time.LocalTime;
import java.util.*;


public class MainController {
    @FXML
    private Pane content;
    @FXML
    private TextField fieldStopWatch = new TextField();
    private Timer timer = new Timer(false);;
    private LocalTime time = LocalTime.now();
    private List<TimeUpdate> updates = new ArrayList<>();
    private List<Drawable> elements = new ArrayList<>();
    private StopWatch stopWatch = new StopWatch();
    private List<Bus> buses;
    private double timeSpeedScale = 1;
    private boolean stoppedFlag = false;
    private boolean startedFlag = true;
    private boolean hoveredOverBus = false;
    private boolean clickedOverBus = false;
    private boolean selectingStreets = false;
    @FXML
    private Slider speedSlider = new Slider();
    @FXML
    private TextArea areaSchedule = new TextArea();

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

    @FXML
    private void restartSimulation(){
        stopWatch.restartStopwatch();
        for(Bus bus: buses){
            bus.restartPosition();
        }
        fieldStopWatch.setText(stopWatch.getTimeInString());
    }

    @FXML
    private void stopTimeSimulation() {
        timer.cancel();
        stoppedFlag = true;
        startedFlag = false;
    }

    @FXML
    private void startTimeSimulation() {
        if(!startedFlag){
            timer = new Timer(false);
            this.startTime();
            fieldStopWatch.setEditable(false);
            startedFlag = true;
        }
    }

    @FXML
    private void changeTimeSpeedOnDrag(){
        timeSpeedScale = 1 - speedSlider.getValue();
        System.out.println(timeSpeedScale);
        if(startedFlag){
            this.stopTimeSimulation();
            this.startTimeSimulation();
        }
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

    public void setBuses(List<Bus> buses){
        this.buses = buses;
    }

    @FXML
    public void onMouseClicked(MouseEvent event){
        if(hoveredOverBus){
            clickedOverBus = true;
            Bus targetBus = findBusAimedOn(event);
            if(targetBus == null){
                hoveredOverBus = false;
            }
        }
        else{
            clickedOverBus = false;
            areaSchedule.clear();
            redrawDefaultMap();
        }
    }

    private void highlightLine(Line line){
        for(Street street : line.getStreets()){
            content.getChildren().add(((MyStreet)street).getHighlightedGUI());
        }
    }

    private void redrawDefaultMap(){
        for(Bus bus : buses){
            for(Street street : bus.getLine().getStreets()){
                content.getChildren().addAll(((MyStreet)street).getGUI());
            }
        }
    }

    private Bus findBusAimedOn(MouseEvent event){
        for(Bus bus : buses){
            if(((event.getX()+12 > bus.getCoords().getX()) && (bus.getCoords().getX() > event.getX()-12)) &&
                    ((event.getY()+12 > bus.getCoords().getY()) && (bus.getCoords().getY() > event.getY()-12))){
                hoveredOverBus = true;
                return bus;
            }
        }
        return null;
    }

    @FXML
    public void onSelectStreet(){
        selectingStreets = true;
        hoveredOverBus = false;
    }

    @FXML
    public void onMouseMoved(MouseEvent event){
        Bus targetBus = findBusAimedOn(event);
        if(targetBus != null){
            if(!clickedOverBus){
                highlightLine(targetBus.getLine());
                areaSchedule.setText(targetBus.getFormattedBusSchedule(stopWatch));
            }
        }
        else{
            if(!clickedOverBus){
                redrawDefaultMap();
                areaSchedule.clear();
            }
            hoveredOverBus = false;
        }
    }

    public void startTime(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time = time.plusSeconds(1);
                Platform.runLater(() -> {
                    fieldStopWatch.setText(stopWatch.getTimeInString());
                    for(TimeUpdate update: updates){
                        update.update(time);
                    }
                });
                stopWatch.updateTime();
            }
        }, 0, (int)(1000*timeSpeedScale));
    }
}
