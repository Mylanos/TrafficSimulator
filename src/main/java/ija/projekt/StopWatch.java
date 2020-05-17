/*
 * Soubor: StopWatch.java
 * Ukol c. 2
 * Autor: Marek Ziska, xziska03@stud.fit.vutbr.cz
 * Skupina: 2BIB
 * Datum 17.05.2020
 */

package ija.projekt;

/**
 * Objekt reprezentuje stopky, ktoré udržiavaju čas simulácie. Čas sa aktualizuje každú sekundu(pre simulátor má význam
 * minúty).
 */
public class StopWatch {
    private int hours;
    private int minutes;
    private int beginTime;

    public StopWatch() {
        this.hours = 7;
        this.minutes = 0;
        this.beginTime = 7;
    }

    /**
     * Reštartuje čas, inicializuje ho na počiatočnú hodnotu
     */
    public void restartStopwatch(){
        this.hours = beginTime;
        this.minutes = 0;
    }

    /**
     * Aktualizuje čas, zvýši hodnotu o jednu sekundu.
     */
    public void updateTime(){
        minutes += 1;
        if(minutes >= 60){
            hours+=1;
            minutes = 0;
        }
        if(hours>=24){
            hours = 0 ;
        }
    }

    /**
     * Vráti aktuálny čas v sekundách
     * @return čas v sekundách
     */
    public int getTime(){
        int time = minutes;
        time += (60*hours);
        return time;
    }

    /**
     * Konvertuje reťazec obsahujúci čas, do celočíselnej hodnoty v sekundách
     * @param time čas vo formáte (HH:MM)
     * @return celočíselna hodnota v sekundách
     */
    public int convertFormattedTimeToMinutes(String time){
        String[] splitedTime = time.split(":", 2);
        int hours = Integer.parseInt(splitedTime[0]);
        int minutes = Integer.parseInt(splitedTime[1]);
        hours -= beginTime;
        return (hours*60) + minutes;
    }

    /**
     * Konvertuje čas v sekundách na reťazec vo formáte (HH:MM)
     * @param timePassed počet sekúnd
     * @return reťazec vo formáte (HH:MM)
     */
    public String getSpecificTimeInString(int timePassed){
        int passedHours = 0;
        int passedMinutes = 0;
        if(timePassed != 0){
            passedHours = (timePassed / 60) % 24;
            passedMinutes = timePassed % 60;
        }
        return String.format("%02d:%02d", passedHours, passedMinutes);
    }

    /**
     * Vráti aktuálny čas v reťazci
     * @return aktuálny čas v reťazci
     */
    public String getTimeInString(){
        return String.format("%02d:%02d", hours, minutes);
    }
}
