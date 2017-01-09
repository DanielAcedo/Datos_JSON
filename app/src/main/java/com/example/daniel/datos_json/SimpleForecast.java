package com.example.daniel.datos_json;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by usuario on 9/01/17.
 */

public class SimpleForecast {
    @SerializedName("dt")
    private long epochDate;
    @SerializedName("temp")
    private SimpleTemperature temp;
    @SerializedName("pressure")
    private float pressure;

    public SimpleForecast(long date, SimpleTemperature temp, float pressure) {
        this.epochDate = date;
        this.temp = temp;
        this.pressure = pressure;
    }

    public long getEpochDate() {
        return epochDate;
    }

    public void setEpochDate(long epochDate) {
        this.epochDate = epochDate;
    }

    public String getFormatedDate(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(epochDate * 1000L);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        return format.format(cal.getTime());
    }

    public SimpleTemperature getTemp() {
        return temp;
    }

    public void setTemp(SimpleTemperature temp) {
        this.temp = temp;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public String toString(){
        return "Date: "+getFormatedDate()+", MinTemp: "+temp.getMinTemp()+", MaxTemp: "+temp.getMaxTemp()+", Pressure: "+pressure;
    }
}
