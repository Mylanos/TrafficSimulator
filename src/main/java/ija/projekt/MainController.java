/*
 * Soubor: MainController.java
 * Ukol c. 2
 * Autor: Marek Ziska, xziska03@stud.fit.vutbr.cz
 * Skupina: 2BIB
 * Datum 17.05.2020
 */

package ija.projekt;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Je zodpovedný za spracovanie prichádzajúcich požiadaviek. Aktualizuje model a vracia pohľad, ktorý by sa mal vykresliť.
 */
public class MainController {
    @FXML
    private Pane content;
    @FXML
    private TextField fieldStopWatch = new TextField();
    private Timer timer = new Timer(false);;
    private LocalTime time = LocalTime.now();
    private List<Drawable> elements = new ArrayList<>();
    private StopWatch stopWatch = new StopWatch();
    private List<Bus> buses = new ArrayList<>();
    private List<MyStreet> streets = new ArrayList<>();
    private List<MyLine> lines = new ArrayList<>();
    private List<Bus> dispatchedBuses = new ArrayList<>();
    private double timeSpeedScale = 1;
    private boolean stoppedFlag = false;
    private boolean startedFlag = true;
    private MyStreet selectedStreet;
    @FXML
    private Slider speedSlider = new Slider();
    @FXML
    private Slider delaySlider = new Slider();
    @FXML
    private TextArea areaSchedule = new TextArea();
    @FXML
    private TextArea areaStreetSelection = new TextArea();

    /**
     * Pri scroll evente nad hlavným panelom, počíta veľkosť zväčšenia panelu podľa tohoto eventu
     * @param event scroll event
     */
    @FXML
    private void onZoom(ScrollEvent event){
        /*aby sa neprogpagoval do vyssich panov*/
        event.consume();
        System.out.println(event.getDeltaY());
        double zoom = event.getDeltaY() >= 0 ? 1.05 : 0.95;
        content.setScaleX(zoom * content.getScaleX());
        content.setScaleY(zoom * content.getScaleY());
        content.layout();
    }

    /**
     * Reštartuje simuláciu, vyčistí view od dynamicky sa meniacich elementov v GUI a na novo ich inicializuje
     */
    @FXML
    private void restartSimulation(){
        stopWatch.restartStopwatch();
        for(Bus bus: buses){
            bus.restartPosition();
        }
        dispatchedBuses.clear();
        content.getChildren().clear();
        setElements(elements);
        fieldStopWatch.setText(stopWatch.getTimeInString());
    }

    /**
     * Zastaví simuláciu
     */
    @FXML
    private void stopTimeSimulation() {
        timer.cancel();
        stoppedFlag = true;
        startedFlag = false;
    }

    /**
     * Naštartuje simuláciu, a to len ak bola pozastavená.
     */
    @FXML
    private void startTimeSimulation() {
        if(!startedFlag){
            timer = new Timer(false);
            this.startTime();
            fieldStopWatch.setEditable(false);
            startedFlag = true;
        }
    }

    /**
     * Nastavuje oneskorenie pre danú ulicu
     */
    @FXML
    private void setDelayOnDrag(){
        System.out.println("wwwow");
        if(selectedStreet != null){
            int delay = (int) delaySlider.getValue();
            System.out.println(delay);
            selectedStreet.setDelay(delay);
            for(Bus bus: buses){
                if(bus.getLine().containsStreet(selectedStreet)){
                    bus.getLine().replaceStreet(selectedStreet);
                }
            }
            for(Bus bus: dispatchedBuses){
                bus.clearSchedule();
                bus.calculateScheduleTimes();
            }

        }
    }

    /**
     * Nastavuje zrýchlenie času
     */
    @FXML
    private void changeTimeSpeedOnDrag(){
        timeSpeedScale = 1 - speedSlider.getValue();
        System.out.println(timeSpeedScale);
        if(startedFlag){
            this.stopTimeSimulation();
            this.startTimeSimulation();
        }
    }

    /**
     * Inicializuje zoznam všetkých liniek na mape.
     * @param lines zoznam liniek
     */
    public void setLines(List<MyLine> lines){
        this.lines = lines;
    }

    /**
     * Inicializuje zoznam všetkých ulíc na mape.
     * @param streets zoznam ulíc
     */
    public void setStreets(List<MyStreet> streets){
        this.streets = streets;
    }

    /**
     * Inicializuje zoznam všetkých autobusov na mape.
     * @param buses zoznam autobusov
     */
    public void setBuses(List<Bus> buses){
        this.buses = buses;
    }

    /**
     * Inicializuje zoznam všetkých dynamicky sa meniacich elementov na mape.
     * @param elements zoznam elementov
     */
    public void setElements(List<Drawable> elements){
        this.elements = elements;
        for(Drawable drawable : elements){
            if(!(drawable instanceof TimeUpdate)){
                content.getChildren().addAll(drawable.getGUI());
            }

        }
    }

    /**
     * Pri kliknutí myši určuje či kliknutie prebehlo nad ulicou, nad autobusom alebo mimo.
     * @param event Udalosť kliku myši
     */
    @FXML
    public void onMouseClicked(MouseEvent event){
        Bus targetBus = findBusAimedOn(event);
        MyStreet targetStreet = findStreetAimedOn(event);
        selectedStreet = null;
        areaStreetSelection.clear();
        areaSchedule.clear();
        if(targetBus != null){
            redrawDefaultMap();
            highlightLine(targetBus.getLine());
            areaSchedule.setText(targetBus.getFormattedBusSchedule(stopWatch));
        }
        else if(targetStreet != null){
            selectedStreet = targetStreet;
            delaySlider.setValue(selectedStreet.getDelay());
            redrawDefaultMap();
            highlighStreet(targetStreet);
            areaStreetSelection.setText("\t\t" + targetStreet.getId());
        }
        else{
            redrawDefaultMap();
        }
    }

    /**
     * Zvýrazní ulicu v GUI
     * @param street ulica ktorá bude zvýraznená
     */
    private void highlighStreet(MyStreet street){
        content.getChildren().add(((MyStreet)street).getHighlightedGUI());
    }

    /**
     * Zvýrazní linku v GUI
     * @param line linka ktorá bude zvýraznená
     */
    private void highlightLine(Line line){
        for(MyStreet street : line.getStreets()){
            highlighStreet(street);
        }
    }

    /**
     * Prekreslí mapu na počiatočný výzor
     */
    private void redrawDefaultMap(){
        for(Street street: streets) {
            content.getChildren().addAll(((MyStreet) street).getGUIstreet());
        }
    }

    /**
     * Hľadá ulicu nad ktorou bolo prejdené s myšou
     * @param event Udalosť posunu myši
     * @return Ulica nad ktorou bola myš, inak null
     */
    private MyStreet findStreetAimedOn(MouseEvent event){
        Coordinate eventCoords = new Coordinate(event.getX(), event.getY());
        System.out.print(event.getX());
        System.out.print(" faafa ");
        System.out.println(event.getY());
        for(MyStreet street : streets){
            Coordinate streetRoundedCoordsBegin = new Coordinate(Math.round(street.begin().getX()), Math.round(street.begin().getY()));
            Coordinate streetRoundedCoordsEnd = new Coordinate(Math.round(street.end().getX()), Math.round(street.end().getY()));
            if(eventCoords.isOnLineBetweenInt(streetRoundedCoordsBegin, streetRoundedCoordsEnd)){
                return street;
            }
        }
        return null;
    }

    /**
     * Hľadá autobus nad ktorou bolo prejdené s myšou
     * @param event Udalosť posunu myši
     * @return Autobus nad ktorou bola myš, inak null
     */
    private Bus findBusAimedOn(MouseEvent event){
        for(Bus bus : buses){
            if(((event.getX()+12 > bus.getCoords().getX()) && (bus.getCoords().getX() > event.getX()-12)) &&
                    ((event.getY()+12 > bus.getCoords().getY()) && (bus.getCoords().getY() > event.getY()-12))){
                return bus;
            }
        }
        return null;
    }

    /**
     * Autobus bol vydaný na cestu linky
     * @param bus autobus ktorý začal svoju cestu
     */
    public void dispatchNewBus(Bus bus){
        dispatchedBuses.add(bus);
    }

    /**
     * Odosiela nový autobus
     * @param lineID linka na ktorej bude odoslaný nový autobus
     * @param dispatchTime čas v ktorom autobus začína svoju cestu
     */
    public void dispatching(String lineID, int dispatchTime){
        for(Bus bus: buses){
            MyLine busLine = bus.getLine();
            if(busLine.getID().equals(lineID)){
                if(busLine.getBusCount() > 0){
                    if(!dispatchedBuses.contains(bus)){
                        bus.setDispatchTime(dispatchTime);
                        bus.calculateScheduleTimes();
                        content.getChildren().addAll(bus.getGUI());
                        dispatchNewBus(bus);
                        busLine.decreaseBusQueue();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Hlavná časť kontroléru. Simuluje čas, aktualizuje dynamické elementy v GUI.
     */
    public void startTime(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(stopWatch.getTime() % 10 == 0){
                        for(MyLine line: lines){
                            dispatching(line.getID(), stopWatch.getTime());
                        }
                    }
                    time = time.plusSeconds(1);
                    stopWatch.updateTime();
                    fieldStopWatch.setText(stopWatch.getTimeInString());
                    for(Bus bus: dispatchedBuses){
                        bus.update(time);
                    }
                });
            }
        }, 0, (int)(1000*timeSpeedScale));
    }
}
