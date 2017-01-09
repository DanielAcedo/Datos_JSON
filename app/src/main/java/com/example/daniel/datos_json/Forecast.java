package com.example.daniel.datos_json;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Daniel on 15/12/2016.
 */

public class Forecast {
    private SimpleDateFormat sdf;


    private String cityName;
    private String countryCode;
    private String iconCode;
    private String skyMain;
    private String skyDescription;
    private Calendar sunrise;
    private Calendar sunset;
    private Calendar date;
    private float pressure;
    private float temperature;
    private float maxTemp;
    private float minTemp;
    private float windSpeed;
    private byte humidity;
    private byte cloudiness;

    public Forecast(){
        sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    public Forecast(String cityName, String countryCode, String iconCode, String skyMain,
                    String skyDescription, Calendar sunrise, Calendar sunset, Calendar date,
                    float pressureGround, float temperature, float maxTemp,
                    float minTemp, float windSpeed, byte humidity, byte cloudiness) {

        super();
        this.cityName = cityName;
        this.countryCode = countryCode;
        this.iconCode = iconCode;
        this.skyMain = skyMain;
        this.skyDescription = skyDescription;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.date = date;
        this.pressure = pressureGround;
        this.temperature = temperature;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.cloudiness = cloudiness;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getIconCode() {
        return iconCode;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }

    public String getSkyMain() {
        return skyMain;
    }

    public void setSkyMain(String skyMain) {
        this.skyMain = skyMain;
    }

    public String getSkyDescription() {
        return skyDescription;
    }

    public void setSkyDescription(String skyDescription) {
        this.skyDescription = skyDescription;
    }

    public Calendar getSunrise() {
        return sunrise;
    }

    public void setSunrise(Calendar sunrise) {
        this.sunrise = sunrise;
    }

    public Calendar getSunset() {
        return sunset;
    }

    public void setSunset(Calendar sunset) {
        this.sunset = sunset;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        this.maxTemp = maxTemp;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(float minTemp) {
        this.minTemp = minTemp;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public byte getHumidity() {
        return humidity;
    }

    public void setHumidity(byte humidity) {
        this.humidity = humidity;
    }

    public byte getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(byte cloudiness) {
        this.cloudiness = cloudiness;
    }

    @Override
    public String toString() {
        return  String.format(
                    "Name: %1$s\nCountry: %2$s\nTemp: %3$s Cº \nSky: %4$s\nDescription: %5$s\n" +
                            "Humidity: %6$s%%\nGround pressure: %7$s hpa\n"
                ,   cityName, countryCode, temperature, skyMain, skyDescription, humidity, pressure)+
                "Sunset: "+sdf.format(sunset.getTime())+"\n"+
                "Sunrise: "+sdf.format(sunrise.getTime())+"\n"+
                "Date: "+sdf.format(date.getTime());
    }
}
