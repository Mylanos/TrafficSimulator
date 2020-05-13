package ija.projekt;

import java.util.Timer;

public class StopWatch {
    private int hours;
    private int minutes;
    private int beginTime;

    public StopWatch() {
        this.hours = 7;
        this.minutes = 0;
        this.beginTime = 7;
    }

    public void restartStopwatch(){
        this.hours = beginTime;
        this.minutes = 0;
    }

    public void updateTime(){
        minutes += 1;
        if(minutes >= 60){
            hours+=1;
            minutes = 0;
        }
        if(hours>=60){
            hours+=1;
            hours = 0 ;
        }
    }

    public int convertFormattedTimeToMinutes(String time){
        String[] splitedTime = time.split(":", 2);
        int hours = Integer.parseInt(splitedTime[0]);
        int minutes = Integer.parseInt(splitedTime[1]);
        hours -= beginTime;
        return (hours*60) + minutes;
    }

    public String getSpecificTimeInString(int timePassed){
        int passedHours = beginTime;
        int passedMinutes = 0;
        if(timePassed != 0){
            passedHours = timePassed / 60;
            passedHours += beginTime;
            passedMinutes = timePassed % 60;
        }
        return String.format("%02d:%02d", passedHours, passedMinutes);
    }

    public String getTimeInString(){
        return String.format("%02d:%02d", hours, minutes);
    }
}
