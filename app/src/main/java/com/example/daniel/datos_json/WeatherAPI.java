package com.example.daniel.datos_json;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by Daniel on 15/12/2016.
 */

public class WeatherAPI {
    public static final String APIKEY = "9a3532d2ea0c378075f8f0057c72cff3";
    public static final String CURRENT_WEATHER_PATH = "http://api.openweathermap.org/data/2.5/weather?";
    public static final String ICON_PREFIX_PATH = "http://openweathermap.org/img/w/";
    public static final String ID_MALAGA = "2514256";
    public static final String UNIT_METRIC = "metric";

    public static final String SEPARATOR_PRMT = "&";
    public static final String ID_PREFIX = "id=";
    public static final String APIKEY_PREFIX = "APPID=";
    public static final String UNIT_PREFIX = "units=";

    /**
     * Create a complete url path for calling Weather API
     * @param api_key Application Key
     * @param city_id City id within the API
     * @param units Metric system used
     * @return
     */
    public static String createCompleteApiCall(String api_key, String city_id, String units){
        return CURRENT_WEATHER_PATH+
                ID_PREFIX+
                city_id+
                SEPARATOR_PRMT+
                APIKEY_PREFIX+
                api_key+
                SEPARATOR_PRMT+
                UNIT_PREFIX+
                units;
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
        forecast.setPressureGround((float)json.getJSONObject("main").getDouble("grnd_level"));
        //Sea-level pressure
        forecast.setPressureSea((float)json.getJSONObject("main").getDouble("sea_level"));
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
}
