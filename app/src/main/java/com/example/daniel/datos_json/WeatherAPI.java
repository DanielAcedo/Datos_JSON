package com.example.daniel.datos_json;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;

/**
 * Created by Daniel on 15/12/2016.
 */

public class WeatherAPI {
    public static final String APIKEY = "9a3532d2ea0c378075f8f0057c72cff3";
    public static final String CURRENT_WEATHER_PATH = "http://api.openweathermap.org/data/2.5/weather?";
    public static final String LAST_7_DAYS_PATH = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    public static final String ICON_PREFIX_PATH = "http://openweathermap.org/img/w/";

    //Cities ID
    public static final String ID_MALAGA = "2514256";

    public static final String SEPARATOR_PRMT = "&";
    public static final String ID_PREFIX = "id=";
    public static final String APIKEY_PREFIX = "APPID=";
    public static final String UNIT_PREFIX = "units=";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef()
    public @interface Metric {
        String UNIT_METRIC = "metric";
        String UNIT_IMPERIAL = "imperial";
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef()
    public @interface RequestType{
        int CURRENT_WEATHER = 0;
        int LAST_7_DAYS = 1;
    }

    /**
     * Create a complete url path for calling Weather API
     * @param api_key Application Key
     * @param city_id City id within the API
     * @param units Metric system used
     * @return
     */
    public static String createCompleteApiCall(@RequestType int type, String api_key, String city_id, @Metric String units){
        String out = "";

        switch (type){
            case RequestType.CURRENT_WEATHER:
                out = CURRENT_WEATHER_PATH+
                        ID_PREFIX+
                        city_id+
                        SEPARATOR_PRMT+
                        APIKEY_PREFIX+
                        api_key+
                        SEPARATOR_PRMT+
                        UNIT_PREFIX+
                        units;
                break;
            case RequestType.LAST_7_DAYS:
                out = LAST_7_DAYS_PATH+
                        ID_PREFIX+
                        city_id+
                        SEPARATOR_PRMT+
                        APIKEY_PREFIX+
                        api_key+
                        SEPARATOR_PRMT+
                        UNIT_PREFIX+
                        units;
        }

        return out;
    }

    public static Forecast parseJSONForecast(JSONObject json) throws JSONException {
        Forecast forecast = new Forecast();
        Calendar calTmp = Calendar.getInstance();

        //Name
        forecast.setCityName(json.getString("name"));
        //CountryCode
        forecast.setCountryCode(json.getJSONObject("sys").getString("country"));
        //Cloudiness
        forecast.setCloudiness((byte)json.getJSONObject("clouds").getInt("all"));
        //Date
        calTmp.setTimeInMillis(json.getLong("dt")*1000L);
        forecast.setDate(calTmp);
        //Humidity
        forecast.setHumidity((byte)json.getJSONObject("main").getInt("humidity"));
        //Icon code
        forecast.setIconCode(json.getJSONArray("weather").getJSONObject(0).getString("icon"));
        //Temperature
        forecast.setTemperature((float)json.getJSONObject("main").getDouble("temp"));
        //Max temp
        forecast.setMaxTemp((float)json.getJSONObject("main").getDouble("temp_max"));
        //Min temp
        forecast.setMinTemp((float)json.getJSONObject("main").getDouble("temp_min"));
        //Ground-level pressure
        forecast.setPressure((float)json.getJSONObject("main").getDouble("pressure"));
        //Sky main
        forecast.setSkyMain(json.getJSONArray("weather").getJSONObject(0).getString("main"));
        //Sky description
        forecast.setSkyDescription(json.getJSONArray("weather").getJSONObject(0).getString("description"));
        //Sunrise
        calTmp = Calendar.getInstance();
        calTmp.setTimeInMillis(json.getJSONObject("sys").getLong("sunrise")*1000L);
        forecast.setSunrise(calTmp);
        //Sunset
        calTmp = Calendar.getInstance();
        calTmp.setTimeInMillis(json.getJSONObject("sys").getLong("sunset")*1000L);
        forecast.setSunset(calTmp);
        //Wind speed
        forecast.setWindSpeed((float) json.getJSONObject("wind").getDouble("speed"));


        return forecast;
    }

    public static SimpleForecast[] parseJSONSimpleForecast(JSONObject json) throws JSONException{
        JSONArray forecastsJSONArray = json.getJSONArray("list");
        Calendar calTmp = Calendar.getInstance();

        Gson gson = new GsonBuilder().create();
        SimpleForecast[] forecasts = gson.fromJson(forecastsJSONArray.toString(), SimpleForecast[].class);

        return forecasts;
    }
}
