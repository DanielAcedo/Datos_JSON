package com.example.daniel.datos_json.Ej4;

/**
 * Created by Daniel on 08/01/2017.
 */

public class BusLine {
    private String number;
    private String name;
    private String waitTime;

    public BusLine(){}

    public BusLine(String number, String name, String waitTime) {
        this.number = number;
        this.name = name;
        this.waitTime = waitTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime;
    }
}
